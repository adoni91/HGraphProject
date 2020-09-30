from pyspark import SparkContext, SparkConf, SparkFiles
from graph import Graph


NUM_PARTITION = 4
#NUM_CORE = 4

graph = Graph()
if __name__ == '__main__':
    sc = SparkContext(master="local[*]", appName="A*-Spark")
    sc.addFile("node.py")
    sc.addFile("nodeitem.py")
    sc.addFile("graph.py")
    SparkFiles.getRootDirectory()

    # read edges from edge.edgelist file
    transition_table_rdd = sc.textFile("C:\edge20.edgelist").map(lambda line: graph.createEdgeProperty(line), preservesPartitioning=True).partitionBy(NUM_PARTITION).cache()

    # create state list rdd
    state_list_rdd = transition_table_rdd.groupByKey(numPartitions=NUM_PARTITION).mapValues(graph.getSucessorProperty).sortByKey().map(lambda val: graph.lineToNode(val)).cache()

    #share the node boundaries IDs
    boundary_nodes=graph.getBoundaryNodesId(state_list_rdd, NUM_PARTITION)
    shared_nodes=sc.broadcast(boundary_nodes)
    target_node = state_list_rdd.collect()[len(state_list_rdd.collect()) - 1]
    share_target_node = sc.broadcast(target_node)


    # run A* mapper on each rdd partition
    def A_Star_Mapper(index, iterator):
        sc_w = SparkContext(master="local[1]", appName="A*_mapper_task")
        node_list = list(iterator.__iter__())
        init_node = node_list.__getitem__(0)
        goal_node = node_list.__getitem__(len(node_list) - 1)
        node_list_rdd = sc_w.parallelize(node_list)
        init_node_rdd = sc_w.parallelize([init_node], 1).cache()
        goal_node_rdd = sc_w.parallelize([goal_node], 1).cache()
        current_node_rdd = init_node_rdd
        open_list_rdd = sc_w.parallelize([], 1).cache()
        close_list_rdd = sc_w.parallelize([], 1).cache()
        while current_node_rdd.collect()[0].get_targetID() != goal_node_rdd.collect()[0].get_targetID():
            open_list_rdd = graph.searchNeighboardOfNode(current_node_rdd, share_target_node, open_list_rdd, sc_w)
            selected_item = open_list_rdd.min(lambda x: x == x.get_f())
            selected_item_rdd = sc_w.parallelize([selected_item], 1)
            open_list_rdd = open_list_rdd.filter(lambda x: x.get_targetID() != selected_item.get_targetID()).cache()
            close_list_rdd=close_list_rdd.filter(lambda x: x.get_idpreviousnode()!=selected_item.get_idpreviousnode()).union(selected_item_rdd).cache()
            current_node_rdd = node_list_rdd.filter(lambda x: x.get_targetID() == selected_item.get_targetID()).cache()
        path = graph.extractSequenceFromCloseList(close_list_rdd.collect())
        for value in shared_nodes.value:
            if path[len(path)-1][1]+1 == value:
                item = path[len(path)-1][1], value
                path.append(item)
        sc_w.stop()
        return path


    all_paths_rdd = state_list_rdd.mapPartitionsWithIndex(A_Star_Mapper)
    all_paths_rdd.saveAsTextFile("C:\output_all_parts")
    all_paths_rdd.repartition(1).saveAsTextFile("C:\output_final_part")

    sc.stop()

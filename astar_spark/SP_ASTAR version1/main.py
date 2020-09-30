from datetime import datetime
from graph import *
from pyspark import SparkContext
from node import *

NUM_PARTITION = 4
NUM_CORE = 4
# NUM_MAPPER=NUM_PARTITION

graph = Graph()
if __name__ == '__main__':
    target_node=int(input("enter the goal node targetID: "))
    target_node=Node(target_node-1,[])
    t1 = datetime.now().microsecond
    sc = SparkContext(master="local[*]", appName="A*-Spark")
    #sc.addFile("nodeitem.py")
    #sc.addFile("node.py")
    #sc.addFile("graph.py")

    print "loading graph data ..."
    # read edges from edge.edgelist file
    transition_table_rdd = sc.textFile("C:\edge50000.edgelist").map(lambda line: graph.createEdgeProperty(line),
                                                                 preservesPartitioning=True).partitionBy(
        NUM_PARTITION).cache()

    print "creating explored nodes and egdes ..."
    # create state list rdd
    state_list_rdd = transition_table_rdd.groupByKey(numPartitions=NUM_PARTITION).mapValues(
        graph.getSucessorProperty).sortByKey().map(lambda val: graph.lineToNode(val)).cache()

    print "sharing boundaries nodes ..."
    #target_node = state_list_rdd.collect()[len(state_list_rdd.collect()) - 1]
    share_target_node = sc.broadcast(target_node)
    print share_target_node.value

    print "compute all-paths ..."
    all_paths_rdd = state_list_rdd.mapPartitions(graph.A_Star_Mapper)
    t2 = datetime.now().microsecond
    print (t2-t1)
    all_paths_rdd.saveAsTextFile("C:\output_all_parts")
    all_paths_rdd.repartition(1).saveAsTextFile("C:\output_final_part")

    sc.stop()

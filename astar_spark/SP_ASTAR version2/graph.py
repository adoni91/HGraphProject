from node import Node
from nodeitem import Nodeitem
from math import sqrt, pow

class Graph(object):

    def __init__(self, node=[]):
        self.node=node

    def createNodeProperty(self, line):
        return [int(line.split()[0]), int(line.split()[1])]

    def createEdgeProperty(self, line):
        return [int(line.split()[0]), [int(line.split()[1]), int(line.split()[2])]]

    def getSucessorProperty(self,rdd):
        rdd2 = list()
        for values in rdd.__iter__():
            rdd2.append([values[0],values[1]])
        rdd2.sort()
        return rdd2

    #transform line into key-value pair
    def lineToNode(self, line):
        key=line[0]
        value=line[1]
        return Node(key, value)

    # return the transition fuction value of a state x. QxW->Q
    def getG(self, succesor):
        return succesor[1]

    # Euclidean distance between the state s1 and s2
    def getEuclideanDistance(self, target1, target2):
        return sqrt(pow(target2 - target1, 2)) #+ random.random() * 0.1

    #Return the neighboard of the current state
    def searchNeighboardOfNode(self, current_node_rdd, target_node, open_list_rdd, sc):
        items=[]
        current_node=current_node_rdd.collect()[0]
        for next_sucessor in current_node.get_succesors():
            G = self.getG(next_sucessor)
            H = self.getEuclideanDistance(target_node.value.get_targetID(), current_node.get_targetID())
            node_item = Nodeitem(next_sucessor[0], G, H, current_node.get_targetID())
            items.append(node_item)
        items_rdd=sc.parallelize(items, 1)
        return open_list_rdd.union(items_rdd)


    #Return the most promising state
    def getMinOpenListNode(self, open_list, goal_node):
        min_f = open_list[0].get_f()
        selected_item = open_list[0]
        for i in range(1, len(open_list)):
            if open_list[i].get_f() <= min_f:
                min_f = open_list[i].get_f()
                selected_item = open_list[i]
            if open_list[i].get_targetID() == goal_node.get_targetID():
                selected_item = open_list[i]
                break
        return selected_item

    # Return the new current node
    def searchNewCurrentNode(self, node_list, targetID):
        current_node = Node()
        for node in node_list:
            if node.get_targetID() == targetID:
                current_node = node
                break
        return current_node

    # Return sequence from the close list
    def extractSequenceFromCloseList(self, close_list):
        path = []
        for item in close_list:
            sequence = item.get_idpreviousnode(), item.get_targetID()
            path.append(sequence)
        return path

    # return the id of boundary nodes
    def getBoundaryNodesId(self, rdd, numberPartition):
        nodes_id = []
        all_rdd_parts=rdd.glom().collect()
        for i in range(1, numberPartition):
            nodes_id.append(all_rdd_parts[i][0].get_targetID())
        return nodes_id


from node import *
from nodeitem import *
from math import sqrt, pow
import time

class Graph:

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
    def searchNeighboardOfNode(self, current_node, goal_node, open_list):
        for next_sucessor in current_node.get_succesors():
            G = self.getG(next_sucessor)
            H = self.getEuclideanDistance(goal_node.get_targetID(), current_node.get_targetID())
            node_item = Nodeitem(next_sucessor[0], G, H, current_node.get_targetID())
            open_list.append(node_item)

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
        inter_close_list = []
        path = []
        size = len(close_list)
        for i in range(1, size):
            if close_list[i].get_idpreviousnode() == close_list[i - 1].get_idpreviousnode():
                inter_close_list.append(close_list[i - 1])
        for item in inter_close_list:
            close_list.remove(item)
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

    # compute intermediate path
    def A_Star_Mapper(self, rdd_part):
        intermediate_open_list=[]
        intermediate_close_list=[]
        node_list_rdd_part= list(rdd_part.__iter__())
        intermediate_init_node=node_list_rdd_part.__getitem__(0)
        intermediate_goal_node=node_list_rdd_part.__getitem__(len(node_list_rdd_part)-1)
        current_node = intermediate_init_node
        while current_node.get_targetID() != intermediate_goal_node.get_targetID():
            self.searchNeighboardOfNode(current_node, intermediate_goal_node, intermediate_open_list)
            selected_item=self.getMinOpenListNode(intermediate_open_list, intermediate_goal_node)
            intermediate_open_list.remove(selected_item)
            intermediate_close_list.append(selected_item)
            current_node = self.searchNewCurrentNode(node_list_rdd_part, selected_item.get_targetID())
            intermediate_path=self.extractSequenceFromCloseList(intermediate_close_list)
        return intermediate_path


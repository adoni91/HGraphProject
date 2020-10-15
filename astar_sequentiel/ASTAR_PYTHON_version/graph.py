from node import *
from nodeitem import *
from math import sqrt, pow
import random


class Graph:
    def __init__(self, states=[]):
        self.states = states

    # return the transition fuction value of a state x. QxW->Q
    def getG(self, current_state_id, next_state_id, transition_table):
        for i in range(len(transition_table)):
            if transition_table[i][0] == current_state_id and transition_table[i][1] == next_state_id:
                return transition_table[i][2]

    # Euclidean distance between the state s1 and s2
    def getEuclideanDistance(self, target1, target2):
        return sqrt(pow(target2 - target1, 2)) #+ random.random() * 0.1

    # Return the new current state
    def searchNewCurrentState(self, state_list, targetID):
        current_state = Node()
        for state in state_list:
            if state.get_targetID() == targetID:
                current_state = state
                break
        return current_state

    #Print all states in the state list
    def viewStateList(self, state_list):
        for state in state_list:
            print state

    #Return the neighboard of the current state
    def searchNeighboardOfState(self, current_state, goal_state, open_list, explored_list, transition_table):
        for next_id in current_state.get_succesorsid():
            G = self.getG(current_state.get_targetID(), next_id, transition_table)
            H = self.getEuclideanDistance(goal_state.get_targetID(), next_id)
            state_item = Nodeitem(next_id, G, H, current_state.get_targetID())
            open_list.append(state_item)
            explored_list.append(state_item)

    # Return sequence from the close list
    def extractSequenceFromCloseList(self, close_list):
        inter_close_list = []
        path = []
        size = len(close_list)
        for i in range(1, size):
            if close_list[i].get_idpreviousstate() != close_list[i].get_targetID() and close_list[
                i].get_idpreviousstate() == close_list[i - 1].get_idpreviousstate():
                inter_close_list.append(close_list[i - 1])
        for item in inter_close_list:
            close_list.remove(item)
        for item in close_list:
            sequence = item.get_idpreviousstate(), item.get_targetID()
            path.append(sequence)
        return path

    # Load states from the transition table
    def loadNodefromTransitionTable(self, property_table, transition_table):
        state_list = []
        for i in range(len(property_table)):
            succesorid = []
            for j in range(len(transition_table)):
                if property_table[i][0] == transition_table[j][0]:
                    succesorid.append(int(transition_table[j][1]))
            state = Node(int(property_table[i][0]), bool(property_table[i][1]), succesorid)
            state_list.append(state)
        return state_list

    #Return all end states of the automaton
    def getEndState(self, state_list):
        end_state_list = []
        for state in state_list:
            if state.get_boolType() == True:
                end_state_list.append(state)
        return end_state_list

    #Return the init state of the automaton
    def getInitState(self, state_list):
        return Node(state_list[0].get_targetID(), state_list[0].get_boolType(), state_list[0].get_succesorsid())

    #Return the most promising state
    def getMinOpenListState(self, open_list, goal_state):
        min_f = open_list[0].get_f()
        selected_item = open_list[0]
        for i in range(1, len(open_list)):
            if open_list[i].get_f() <= min_f:
                min_f = open_list[i].get_f()
                selected_item = open_list[i]
            if open_list[i].get_targetID() == goal_state.get_targetID():
                selected_item = open_list[i]
                break
        return selected_item

    #Return transition table of the automaton
    def stateTransitionTable(self, transition_path):
        file = open(transition_path)
        frame = file.readlines()
        file.close()
        cmax = len(frame[0].split(" "))
        lmax = len(frame)
        table = [[0] * cmax for i in range(lmax)]
        for l in range(lmax):
            split = frame[l].replace("\n", "").split(" ")
            for c in range(cmax):
                if c == cmax - 1:
                    table[l][c] = float(split[c])
                else:
                    table[l][c] = int(split[c])
        return table

    #Return state properties
    def statePropertyTable(self, state_path):
        file = open(state_path)
        frame = file.readlines()
        file.close()
        cmax = len(frame[0].split(" "))
        lmax = len(frame)
        table = [[0] * cmax for i in range(lmax)]
        for l in range(lmax):
            split = frame[l].replace("\n", "").split(" ")
            for c in range(cmax):
                table[l][c] = int(split[c])
        return table

    def get_states(self):
        return self.states

    def set_states(self, states):
        self.states = states

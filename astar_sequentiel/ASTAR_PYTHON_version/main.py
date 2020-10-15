#!/usr/bin/env python3
from nodeitem import *
from node import *
from graph import *
import time

current_state = Node()
state_list = []
open_list = []
close_list = []
explored_list = []
automaton = Graph()

if __name__ == '__main__':
    exit=0
    while(exit==0):
        automaton=Graph()
        path=input("print the automaton path file:")
        time_file = open(path+"/output.txt", "w")
        transition_table=automaton.stateTransitionTable(path+"/transition.edgelist")
        #print transition_table
        property_table=automaton.statePropertyTable(path+"/state.nodelist")
        print property_table
        state_list=automaton.loadNodefromTransitionTable(property_table, transition_table)
        print(state_list)

        init_state=automaton.getInitState(state_list)
        end_state_list=automaton.getEndState(state_list)

        start_time=time.time()
        selected_item = Nodeitem()
        current_state = init_state
        goal_state=Node(len(property_table),True,[])

        while current_state.get_targetID()!=goal_state.get_targetID():
            automaton.searchNeighboardOfState(current_state, goal_state, open_list, explored_list, transition_table)
            selected_item=automaton.getMinOpenListState(open_list, goal_state)
            open_list.remove(selected_item)
            close_list.append(selected_item)
            current_state=automaton.searchNewCurrentState(state_list, selected_item.get_targetID())
        path= automaton.extractSequenceFromCloseList(close_list)
        print "s({0}, {1}) = {2}".format(init_state.get_targetID(),goal_state.get_targetID(), path)
        time_file.write("start at: "+str(start_time)+"\nend at: "+str(time.time())+"\nruntime: "+str(time.time()-start_time)+"\n\n"+str(path))
        print time.time()-start_time
        time_file.close()
        exit=input("exit the program? type: 1 for yes/ 0 for no: ")
package com.limsad.astar;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.limsad.model.Graph;
import com.limsad.model.Listitem;
import com.limsad.model.Node;

public class Main {

	private static Node start_node;
	private static Node goal_node;
	private static Node current_node;
	private static ArrayList<Node> node_list;
	private static ArrayList<Listitem> open_list;
	private static ArrayList<Listitem> close_list;
	private static ArrayList<Listitem> explored_list;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws FileNotFoundException {
		Graph graph = new Graph();
		//graph.createMatrixFile(1,10);		
		
		// initialization
		String[][] matrix = graph.loadMatrixOfDistance("data/graph1.txt");

		open_list = new ArrayList<Listitem>();
		close_list = new ArrayList<Listitem>();
		explored_list=new ArrayList<Listitem>();
		
		node_list = new ArrayList<Node>();
		node_list = graph.loadNodefromMatrix(matrix);
		
		Scanner sc =new Scanner(System.in);
		System.out.println("Enter the start node id:");
		String stard_node_id=sc.nextLine();//11
		System.out.println("Enter the goal node id:");
		String end_node_id=sc.nextLine();//20
		
		start_node =  new Node(stard_node_id, node_list.get(0).getSuccesorsid());
		goal_node = new Node(end_node_id, node_list.get(Integer.parseInt(end_node_id)-Integer.parseInt(stard_node_id)-1).getSuccesorsid());
		
		System.out.println(start_node.toString());
		System.out.println(goal_node.toString());
		
		Listitem selected_item=new Listitem();
		
		long startTime=System.currentTimeMillis();
		
		current_node = start_node; //initialyze start node
		
		// search the new current node
		while(!current_node.getId().equals(goal_node.getId())){
			graph.searchNeighboardOfNode(current_node, open_list, explored_list, matrix);
			selected_item = graph.getMinOpenListNode(open_list,goal_node);
			open_list.remove(selected_item);
			close_list.add(selected_item);
			current_node=graph.searchNewCurrentNode(node_list, selected_item.getId());
		}
		
		long endTime=System.currentTimeMillis();
		System.out.println("*****NODE LIST*****");
		graph.viewNodeList(node_list);
		
		System.out.println("\n*****OPEN LIST*****");
		graph.viewListItem(explored_list);
		
		System.out.println("\n*****CLOSE LIST*****");
		graph.viewListItem(close_list);
		
		System.out.print("\n****FINAL PATH*****\n");
		graph.extractPathFromCloseList(close_list);
		
		System.out.println("\n\n*****% OF EXPLORED NODES*****");
		if(explored_list.size()*100.0f/node_list.size()>100)
			System.out.println("100 %");
		else
			System.out.println(explored_list.size()*100.0f/node_list.size()+" %");
		
		System.out.println("\n\n*****% OF SELECTED NODES*****");
		int number_of_selected_node=graph.number_of_select_node(close_list);
		System.out.println(number_of_selected_node*100.0f/node_list.size()+" %");
		
		System.out.println("\n\n*****EXECUTION TIME*****");
		System.out.println(endTime-startTime+" milliseconds");

	}

}

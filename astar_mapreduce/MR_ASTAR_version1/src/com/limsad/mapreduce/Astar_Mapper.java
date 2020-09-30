package com.limsad.mapreduce;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.limsad.model.Graph;
import com.limsad.model.Listitem;
import com.limsad.model.Node;

public class Astar_Mapper extends Mapper<LongWritable, Text, Text, Listitem> {
	private static Node start_node;
	private static Node goal_node;
	private static Node current_node;
	private static ArrayList<Node> node_list;
	private static ArrayList<Listitem> open_list;
	private static ArrayList<Listitem> close_list;
	private static ArrayList<Listitem> explored_list;
	
	
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		
		Graph graph = new Graph();
		String subgraph_id=null;
		String[][] matrix = graph.loadMatrix(value.toString());
		
		subgraph_id=matrix[0][0].toString();
		
		open_list = new ArrayList<Listitem>();
		close_list = new ArrayList<Listitem>();
		explored_list=new ArrayList<Listitem>();
		
		
		node_list = new ArrayList<Node>();
		
		/*Scanner sc =new Scanner(System.in);
		System.out.println("Enter the start node id:");
		String stard_node_id=sc.nextLine();
		System.out.println("Enter the goal node id:");
		String end_node_id=sc.nextLine();*/
		
		String stard_node_id="1";
		String end_node_id="25";
		
		node_list = graph.loadNodefromMatrix(matrix);
		
		start_node =  new Node(stard_node_id, node_list.get(Integer.parseInt(stard_node_id)-1).getSuccesorsid());
		goal_node = new Node(end_node_id, node_list.get(Integer.parseInt(end_node_id)-1).getSuccesorsid());
		
		Listitem selected_item=new Listitem();
		
		current_node = start_node; //initialyze start node
		
		// search the new current node
		while(!current_node.getId().equals(goal_node.getId())){
			graph.searchNeighboardOfNode(current_node, open_list, explored_list, matrix);
			selected_item = graph.getMinOpenListNode(open_list,goal_node);
			open_list.remove(selected_item);
			close_list.add(selected_item);
			current_node=graph.searchNewCurrentNode(node_list, selected_item.getId());
			Listitem item=new Listitem();
			item=selected_item;
			context.write(new Text(subgraph_id), item);
		}
		
	}

}

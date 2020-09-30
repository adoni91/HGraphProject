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
				
		String start_node_id=subgraph_id.split("_")[1];
		String end_node_id=subgraph_id.split("_")[2];
		
		node_list = graph.loadNodefromMatrix(matrix);
		int graph_size=Integer.parseInt(end_node_id)-Integer.parseInt(start_node_id)-1;
		start_node =  new Node(start_node_id, node_list.get(0).getSuccesorsid());
		goal_node = new Node(end_node_id, node_list.get(graph_size).getSuccesorsid());
		
		Listitem selected_item=new Listitem();
		
		current_node = start_node; //initialyze start node
		
		// search the new current node
		while(!current_node.getId().equals(goal_node.getId())){
			graph.searchNeighboardOfNode(current_node, open_list, explored_list, matrix);
			selected_item = graph.getMinOpenListNode(open_list,goal_node);
			open_list.remove(selected_item);
			close_list.add(selected_item);
			current_node=graph.searchNewCurrentNode(node_list, selected_item.getId().toString());
			context.write(new Text(subgraph_id), selected_item);
		}	
	}

}

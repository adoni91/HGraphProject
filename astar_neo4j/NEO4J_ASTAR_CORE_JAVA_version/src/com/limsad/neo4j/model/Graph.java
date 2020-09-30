package com.limsad.neo4j.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Result;

public class Graph {
	
	
	public Graph() {
		super();
	}
	
	public void viewListItem(ArrayList<Listitem> list_vertex_item) {
		for (Listitem vertex_item : list_vertex_item) {
			System.out.println(vertex_item.toString());
		}
	}

	public void viewNodeList(ArrayList<Vertex> vertex_list) {
		for (Vertex vertex : vertex_list) {
			System.out.println(vertex.toString());
		}
	}
	
	public void searchNeighboardOfVertex(Vertex current_vertex, ArrayList<Listitem> open_list, ArrayList<Listitem> explored_list,GraphDatabaseService graphDb){
		Node node=graphDb.findNode(Label.label("Vertex"), "id", current_vertex.getId());
		Iterable<Relationship> iterable_relationship =node.getRelationships(Direction.OUTGOING,RelationshipType.withName("EDGE"));
		Iterator<Relationship> relationships=iterable_relationship.iterator();
		while(relationships.hasNext()){
			Relationship r=relationships.next();		
        	String id=r.getEndNode().getProperty("id").toString();
        	double g=Double.parseDouble(r.getProperty("g").toString());
        	double h=Double.parseDouble(r.getEndNode().getProperty("h").toString());
        	String idpreviousnode = current_vertex.getId();
        	Listitem vertex_item = new Listitem(id, g, h, idpreviousnode);
        	open_list.add(vertex_item);
        	explored_list.add(vertex_item);
		}
	}
	
	public Listitem getMinOpenListVertex(ArrayList<Listitem> open_list, ArrayList<Listitem> close_list, Vertex goal_vertex) {
		double min_f = open_list.get(0).getF();
		Listitem selected_item = open_list.get(0);
		for (int i = 1; i < open_list.size(); i++) {
			if (open_list.get(i).getF() <= min_f && !close_list.contains(open_list.get(i))) {
				min_f = open_list.get(i).getF();
				selected_item = open_list.get(i);
			}
			if (open_list.get(i).getId() == goal_vertex.getId()) {
				selected_item = open_list.get(i);
				break;
			}
		}
		return selected_item;
	}

	public Vertex searchCurrentVertex(String id, GraphDatabaseService graphDb) {
	       Node node=graphDb.findNode(Label.label("Vertex"), "id", id);
	       double h=Double.parseDouble(node.getProperty("h").toString());
	       return new Vertex(id, h);
	}
	
	public void extractPathFromCloseList(ArrayList<Listitem> close_list, GraphDatabaseService graphDb) {
		ArrayList<Listitem> select_list=new ArrayList<Listitem>();
		Listitem currentVertex=close_list.get(close_list.size()-1);
		Listitem firstVertex=close_list.get(0);
		select_list.add(currentVertex);
		close_list.remove(currentVertex);
		while(!currentVertex.getIdpreviousnode().equals(firstVertex.getIdpreviousnode())){
		for(int i=close_list.size()-1; i>=0;i--){
			if(currentVertex.getIdpreviousnode().equals(close_list.get(i).getId())){
				currentVertex=close_list.get(i);
				select_list.add(currentVertex);
				close_list.remove(currentVertex);
			}
			}
		}
		Collections.reverse(select_list);
		String cypherQuery="MATCH path = (:Vertex{id:'"+select_list.get(0).getIdpreviousnode()+"'})-[:EDGE]-";
		System.out.print(select_list.get(0).getIdpreviousnode() + " -> ");
		double weight=0;
		for (Listitem item : select_list) {
			if (item.getId().equals(select_list.get(select_list.size() - 1).getId())){
				System.out.print(item.getId());
				cypherQuery+= "(:Vertex{id:'"+item.getId()+"'})";
				weight+=item.getG();
			}
			else{
				System.out.print(item.getId() + " -> ");
				cypherQuery+= "(:Vertex{id:'"+item.getId()+"'})-[:EDGE]-";
				weight+=item.getG();
			}
		}
		cypherQuery+=" RETURN path";
		Result result=graphDb.execute(cypherQuery);
		System.out.println("\n\n"+result.resultAsString()+ "weight: "+weight);

		}




}

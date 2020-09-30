package com.limsad.neo4j.java.main;

import java.io.File;
import java.util.ArrayList;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import com.limsad.neo4j.model.Graph;
import com.limsad.neo4j.model.Listitem;
import com.limsad.neo4j.model.Vertex;


/**
 * Hello world!
 *
 */
public class Main 
{
	private static Vertex start_vertex;
	private static Vertex goal_vertex;
	private static Vertex current_vertex;
	private static ArrayList<Listitem> open_list;
	private static ArrayList<Listitem> close_list;
	private static ArrayList<Listitem> explored_list;
	
    public static void main( String[] args )
    {
        System.out.println( "Shortest Path Computing..." );
		long startTime=System.currentTimeMillis();
		
		System.out.println();
		
        Graph graph=new Graph();
        //Create a factory:
        GraphDatabaseFactory graphDbFactory=new GraphDatabaseFactory();
        //Create an embedded database
        File storeDir=new File("C:/Users/Hamilton/Documents/Neo4j/graph20.graphdb");
        GraphDatabaseService graphDb = graphDbFactory.newEmbeddedDatabase(storeDir);
        //Start Transaction
        Transaction transaction = graphDb.beginTx();
        try{    
		open_list = new ArrayList<Listitem>();
		close_list = new ArrayList<Listitem>();
		explored_list=new ArrayList<Listitem>();
		
		new ArrayList<Vertex>();
		
	    start_vertex=new Vertex("1", 19);
        goal_vertex=new Vertex("20", 0);
		
       Listitem selected_item=new Listitem();
        
       current_vertex= start_vertex;
        
       Boolean pathFound=true;
       while(!current_vertex.getId().equals(goal_vertex.getId())){
        	graph.searchNeighboardOfVertex(current_vertex, open_list, explored_list, graphDb);
            selected_item=graph.getMinOpenListVertex(open_list, close_list, goal_vertex);
            open_list.remove(selected_item);
            close_list.add(selected_item);
            current_vertex=graph.searchCurrentVertex(selected_item.getId(), graphDb);
			if (open_list.isEmpty()) {
				pathFound=false;
				break;
			}
        }
       
       long endTime=System.currentTimeMillis();
       
       if(pathFound){
		
		System.out.print("\n****FINAL PATH*****\n");
		graph.extractPathFromCloseList(close_list,graphDb);
		
		System.out.println("\n\n*****EXECUTION TIME*****");
		System.out.println(endTime-startTime+" milliseconds");
       }
       else{
    	   System.out.println("path not found");
       }
       transaction.success();
       }finally{
    	   transaction.close();
       }
	   
       graphDb.shutdown();

    }
    
}

package com.limsad.neo4j.astar;

import java.io.File;
import java.io.FileNotFoundException;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
			
		//File storeDirGraphDb = new File("data");
        File storeDirGraphDb=new File("C:/Users/Hamilton/Documents/Neo4j/graph60000.graphdb");
        String storeDirMatrix="C:/Users/Hamilton/Documents/matrix60000.txt";
		GraphDatabaseFactory graphDbFactory = new GraphDatabaseFactory();
		// Create an embedded database
		GraphDatabaseService graphDb = graphDbFactory.newEmbeddedDatabase(storeDirGraphDb);
		Transaction transaction = graphDb.beginTx();
		try {
			Graph graph = new Graph();
			System.out.println("Creating Neo4j Graph");
			graph.createNode(storeDirMatrix, graphDb);
			graph.createRealtionShipType(storeDirMatrix, graphDb);
			transaction.success();

		} finally {
			transaction.close();
		}
		graphDb.shutdown();
	}

}

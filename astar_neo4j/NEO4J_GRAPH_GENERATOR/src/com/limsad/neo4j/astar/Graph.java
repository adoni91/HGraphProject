package com.limsad.neo4j.astar;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Random;

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
		
	public void createNode(String path, GraphDatabaseService graphDb) throws FileNotFoundException {

		BufferedReader in =null;			

		try {

			in = new BufferedReader(new FileReader(path));
			
			String str=null;
			int i=0;
			while ((str = in.readLine()) != null) {
					String node[]=str.split("\\s+");
					String id=node[0].split(",")[0];
					String h=node[0].split(",")[1];
					//String cypherQuery="CREATE (:Vertex{id:'"+id+"',h:'"+h+"'})";
					//graphDb.execute(cypherQuery);
					Node n=graphDb.createNode(Label.label("Vertex"));
					n.setProperty("id", id);
					n.setProperty("h", h);
					if(i%1000==0)
						System.out.println("Created "+(i)+" nodes ...");
					i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void createRealtionShipType(String path, GraphDatabaseService graphDb) {
		BufferedReader in = null;
		
		try {
			in = new BufferedReader(new FileReader(path));
			int i = 0; int n=0;
			String str=null;
			while ((str = in.readLine()) != null) {
				String[] line=str.toString().split("\\s+");
				for (int j = 1; j < line.length; j++) {
					if (!line[j].equals("X")) {
							//System.out.println((i+1) + "--[EDGE:"+ line[j] + "]-->" + j);						
							//String cypherQuery="MATCH (n1:Vertex),(n2:Vertex) WHERE n1.id='"+(i+1)+"' AND n2.id='"+j+"' CREATE (n1)-[r:EDGE{g:'"+line[j]+"'}]->(n2)";
							//graphDb.execute(cypherQuery);
							
						Node n1=graphDb.findNode(Label.label("Vertex"), "id", (i+1)+"");
						Node n2=graphDb.findNode(Label.label("Vertex"), "id", j+"");
						Relationship r =n1.createRelationshipTo(n2, RelationshipType.withName("EDGE"));
						r.setProperty("g", line[j]+"");
						if(n%1000==0)
							System.out.println("Created "+(n)+" relationships ...");
						n++;
					}
					
				}
				i++;
			}
			in.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public String getVertexIdFromGraph(String id, GraphDatabaseService graphDb){
		String cypherQuery="MATCH (n:Vertex) WHERE n.id = \'"+ id +"\' RETURN id(n) AS id_node";
		Result cypherQueryResult=graphDb.execute(cypherQuery);                
		Map<String, Object> row = cypherQueryResult.next();
		return row.get("id_node").toString();
    }
	
	public void createMatrixFile(long N) throws FileNotFoundException {
		System.out.println("Start creating graph...");
		N = N+1L;
		PrintWriter outputfile = new PrintWriter("data/matrix" +(N - 1)+".txt");
		for (long i = 1; i < N; i++) {
			if(i!=1)outputfile.print("\n");
			outputfile.print(i +","+(N-i-1)+" ");
			Random rd = new Random();
			for (long j = 1; j < N; j++) {
				if (i == j){
				outputfile.print("X ");
				}
				else {
					if (j == i + 1 || j == i + 2 || j == i + 3 || j == i + 4
							|| j == i + 5 || j == i + 6 || j == i + 7 || j == i+8
							|| j == i + 9 || j == i+10 || j == i+11 || j == i + 12) {
							outputfile.print(Math.abs(i - j) + rd.nextInt(6)+" ");
					} 
					else
						outputfile.print("X ");
				}
			}
		}
		outputfile.close();
		System.out.println("End creating graph...");
	}


}

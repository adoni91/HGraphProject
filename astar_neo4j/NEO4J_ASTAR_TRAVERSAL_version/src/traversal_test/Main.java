package traversal_test;

import java.io.File;

import org.neo4j.graphalgo.CommonEvaluators;
import org.neo4j.graphalgo.EstimateEvaluator;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.PathExpanders;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Main {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        System.out.println( "Shortest Path Computing..." );
		long startTime=System.currentTimeMillis();
		File storeDirGraphDb = new File("C:/Users/Hamilton/Documents/Neo4j/graph20.graphdb");
		GraphDatabaseFactory graphDbFactory = new GraphDatabaseFactory();
		GraphDatabaseService graphDb = graphDbFactory.newEmbeddedDatabase(storeDirGraphDb);
		Transaction transaction = graphDb.beginTx();
		try {
				
			Node nodeA = graphDb.getNodeById(0);		
			Node nodeB = graphDb.getNodeById(19);					
	
			EstimateEvaluator<Double> estimateEvaluator=new EstimateEvaluator<Double>() {
				
				public Double getCost( final Node node, final Node goal ) {
			        double d1 = (Double) Double.parseDouble(node.getProperty( "h" ).toString());
			        double d2 = (Double) Double.parseDouble(goal.getProperty( "h" ).toString());
			        double result = d1-d2;
			        return result;
			    }
			};
			PathFinder<WeightedPath> astar = GraphAlgoFactory.aStar(
			        PathExpanders.allTypesAndDirections(),
			        CommonEvaluators.doubleCostEvaluator( "g" ), estimateEvaluator );
			WeightedPath path = astar.findSinglePath( nodeA, nodeB );
			System.out.println(path.toString());
			transaction.success();
		}finally{
			transaction.close();
		}
	       long endTime=System.currentTimeMillis();
		   System.out.println("\n\n*****EXECUTION TIME*****");
		   System.out.println(endTime-startTime+" milliseconds");
		graphDb.shutdown();
	
	}


}

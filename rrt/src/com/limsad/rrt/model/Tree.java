package com.limsad.rrt.model;

/**
 * @author Hamilton
 *
 */
import java.util.ArrayList;

public class Tree{
	private ArrayList<Vertex> vertices;
	private ArrayList<Edge> edges;
	
	public Tree(){
		super();
	}
	public Tree(ArrayList<Vertex> nodes, ArrayList<Edge> edges) {
		super();
		this.vertices = nodes;
		this.edges = edges;
	}
	public ArrayList<Vertex> getNodes() {
		return vertices;
	}
	public void setNodes(ArrayList<Vertex> nodes) {
		this.vertices = nodes;
	}
	public ArrayList<Edge> getEdges() {
		return edges;
	}
	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}

	// RETURN A RANDOM VERTEX BETWEEN RANGEMIN AND RANGEMAX
	public Vertex random_state(double min, double max){
		double x_rand= (Math.random() * ((max - min) + 1)) + min;
		double y_rand= (Math.random() * ((max - min) + 1)) + min;
		return new Vertex(x_rand, y_rand);
	}
	
	// RETURN THE NEAREST NEIGHBOR
	public Vertex nearest_neighbor(Vertex q_rand){
		double d_min=Double.POSITIVE_INFINITY;
		Vertex q_near=new Vertex();
		for(Vertex q: this.vertices){
			double d_eucl=euclidean(q_rand, q);
			if(d_eucl<d_min){
				q_near=q;
				d_min=d_eucl;
			}
		}
		return q_near;
	}
	// RETURN A NEW CONFIGURATION q_new, BY MOVING FROM q_near AN INCREMENTAL DISTANCE delta_q
	public Vertex new_state(Vertex q_rand, Vertex q_near, double delta_q){
		return new Vertex(q_near.getX()+delta_q, q_near.getY()+delta_q);
	}
	
	// RETURN THE EUCLIDEAN DISTANCE BETWEEN TWO VERTICES
	public double euclidean(Vertex q_1, Vertex q_2) {
		double delta_x=Math.abs(q_2.getX()-q_1.getX());
		double delta_y=Math.abs(q_2.getY()-q_1.getY());
		return Math.sqrt(delta_x*delta_x + delta_y*delta_y);
	}
	
	// ADD NEW VERTEX IN THE TREE
	public void add_new_vertex(Vertex q_new){
		this.vertices.add(q_new);
	}
	
	//ADD NEW EDGE IN TH TREE
	public void add_new_edge(Vertex q_orig, Vertex q_dest){
		this.edges.add(new Edge(q_orig, q_dest));
	}
	
	// INITIALIZE THE ROOT OF THE TREE
	public void init(Vertex q_init){
		//this.vertices.set(0, q_init);
		this.vertices=new ArrayList<Vertex>();
		this.edges=new ArrayList<Edge>();
		this.vertices.add(q_init);
	}

	
	public void viewTree(){
		System.out.println("--------------- VERTICES ---------------");
		int i=0;
		for(Vertex q: this.vertices){
			System.out.println("q_"+i+ " "+q.toString());
			i++;
		}
		System.out.println("--------------- EDGES ---------------");
		for(Edge e: this.edges)
			System.out.println(e.toString());
	}
	
	
	

}

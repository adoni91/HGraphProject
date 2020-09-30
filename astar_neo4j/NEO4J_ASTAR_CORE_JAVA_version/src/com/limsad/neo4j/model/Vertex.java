package com.limsad.neo4j.model;



public class Vertex{
	private String id;// 
	private double h;
	
	
	public Vertex() {
		super();
	}
	public Vertex(String id, double h) {
		super();
		this.id = id;
		this.h = h;
	}
	@Override
	public String toString() {
		return "Node [id=" + id + ", h=" + h + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getH() {
		return h;
	}
	public void setH(double h) {
		this.h = h;
	}
	
	
}

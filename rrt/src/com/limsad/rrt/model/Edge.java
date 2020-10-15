package com.limsad.rrt.model;

public class Edge {
	private Vertex vertex1;
	private Vertex vertex2;
	
	public Edge() {
		super();
	}

	public Edge(Vertex vertex1, Vertex vertex2) {
		super();
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
	}

	public Vertex getVertex1() {
		return vertex1;
	}

	public void setVertex1(Vertex vertex1) {
		this.vertex1 = vertex1;
	}

	public Vertex getVertex2() {
		return vertex2;
	}

	public void setVertex2(Vertex vertex2) {
		this.vertex2 = vertex2;
	}

	@Override
	public String toString() {
		return "orig=" + vertex1 + ", dest=" + vertex2 + "]";
	}
	
	
	
}

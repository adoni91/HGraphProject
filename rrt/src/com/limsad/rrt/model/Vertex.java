package com.limsad.rrt.model;

public class Vertex {
	private double x;
	private double y;
	
	public Vertex(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	public Vertex() {
		super();
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public String toString() {
		return "[x=" + x + ", y=" + y + "]";
	}

}

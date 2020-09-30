package com.limsad.neo4j.model;

public class Listitem {
	private String id;
	private double g;
	private double h;
	private double f;
	private String idpreviousnode;
	
	public Listitem(String id, double g, double h, String idpreviousnode) {
		super();
		this.id = id;
		this.g = g;
		this.h = h;
		this.f = g+h;
		this.idpreviousnode = idpreviousnode;
	}
	
	public Listitem() {
		super();
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getG() {
		return g;
	}

	public void setG(double g) {
		this.g = g;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	public double getF() {
		return f;
	}

	public void setF(double f) {
		this.f = f;
	}

	public String getIdpreviousnode() {
		return idpreviousnode;
	}

	public void setIdpreviousnode(String idpreviousnode) {
		this.idpreviousnode = idpreviousnode;
	}

	@Override
	public String toString() {
		return "[node=" + id + ", g=" + g + ", h=" + h + ", f=" + f
				+ ", previousnode=" + idpreviousnode + "]";
	}	
		
	
}
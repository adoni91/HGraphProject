package com.limsad.model;

import java.util.Arrays;


public class Node{
	private String id;// 
	private String[] succesorsid;

	public Node() {
		super();
	}

	public Node(String id, String[] succesorsid) {
		super();
		this.id = id;
		this.succesorsid = succesorsid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getSuccesorsid() {
		return succesorsid;
	}

	public void setSuccesorsid(String[] succesorsid) {
		this.succesorsid = succesorsid;
	}

	public boolean indamissibleNode() {
		return this.succesorsid.length == 0;
	}

	@Override
	public String toString() {
		return "[node=" + id + ", succesors="+ Arrays.toString(succesorsid) + "]";
	}

}

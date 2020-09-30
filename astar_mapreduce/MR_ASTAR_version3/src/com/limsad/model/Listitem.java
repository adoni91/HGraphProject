package com.limsad.model;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

public class Listitem implements Writable{
	private Text id;
	private DoubleWritable g;
	private DoubleWritable h;
	private DoubleWritable f;
	private Text idpreviousnode;
	
	
	public Listitem() {
		this.id = new Text();
		this.g = new DoubleWritable();
		this.h = new DoubleWritable();
		this.f = new DoubleWritable();
		this.idpreviousnode = new Text();	
	}
	
	public Listitem(Text id, DoubleWritable g, DoubleWritable h, Text idpreviousnode) {
		this.id = id;
		this.g = g;
		this.h = h;
		double fct=Double.parseDouble(g.toString())+Double.parseDouble(h.toString());
		this.f = new DoubleWritable(fct);
		this.idpreviousnode = idpreviousnode;
	}
	public Text getId() {
		return id;
	}
	public void setId(Text id) {
		this.id = id;
	}
	public DoubleWritable getG() {
		return g;
	}
	public void setG(DoubleWritable g) {
		this.g = g;
	}
	public DoubleWritable getH() {
		return h;
	}
	public void setH(DoubleWritable h) {
		this.h = h;
	}
	public DoubleWritable getF() {
		return f;
	}
	public void setF(DoubleWritable f) {
		this.f = f;
	}
	public Text getIdpreviousnode() {
		return idpreviousnode;
	}
	public void setIdpreviousnode(Text idpreviousnode) {
		this.idpreviousnode = idpreviousnode;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		id.readFields(in);
		g.readFields(in);
		h.readFields(in);
		f.readFields(in);
		idpreviousnode.readFields(in);	
	}

	@Override
	public void write(DataOutput out) throws IOException {
		id.write(out);
		g.write(out);
		h.write(out);
		f.write(out);
		idpreviousnode.write(out);
		
	}

	@Override
	public String toString() {
		return "[id=" + id + ", g=" + g + ", h=" + h + ", f=" + f + ", idpreviousnode=" + idpreviousnode + "]";
	}
	
	
	
	
	
	
		
	
}
package com.limsad.rrt.controler;

import com.limsad.rrt.model.Vertex;

public interface TreeInterface {
	public double euclidean(Vertex state1, Vertex state2);
	public Vertex random_state(int rangeMin, int rangeMax);
	public Vertex nearest_neighbor(Vertex v_rand);

}

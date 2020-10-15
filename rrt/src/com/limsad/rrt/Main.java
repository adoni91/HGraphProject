package com.limsad.rrt;

/**
 * @author Hamilton
 *
 */
import java.awt.Color;

import javax.swing.JFrame;

import com.limsad.rrt.model.Space;
import com.limsad.rrt.model.Tree;
import com.limsad.rrt.model.Vertex;
import com.limsad.rrt.view.VertexPaint;

public class Main {
	
	static Vertex q_init=new Vertex(50,50);
	static Vertex q_goal;
	static Space X;
	static Space X_obs;
	static Space X_free;
	static final double DELTA_q=1;
	static final int K=20;
	static final int RANGE_MIN=0;
	static final int RANGE_MAX=250;


	public static void main(String[] args) {

		// TODO Auto-generated method stub
		/*Tree T = new Tree();
		T.init(q_init);
		for(int i=0; i<K;i++){
			Vertex q_rand=T.random_state(RANGE_MIN, RANGE_MAX);
			T.add_new_vertex(q_rand);
			Vertex q_near=T.nearest_neighbor(q_rand);
			Vertex q_new=T.new_state(q_rand, q_near, DELTA_q);
			T.add_new_vertex(q_new);
			T.add_new_edge(q_near, q_new);				
		}
		T.viewTree();*/
		
	    VertexPaint point = new VertexPaint(new Vertex(105,240),Color.red);
	    //point=new VertexPaint(q_goal, Color.red);
	    JFrame frame = new JFrame("Points");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.add(point);
	    frame.setSize(1000, 1000);
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	    
	
	    frame.add(new VertexPaint(new Vertex(110, 245), Color.BLUE));
		
		
	}


}

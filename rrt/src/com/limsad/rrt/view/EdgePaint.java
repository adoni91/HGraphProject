package com.limsad.rrt.view;
/**
 * @author Hamilton
 *
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.limsad.rrt.model.Edge;
import com.limsad.rrt.model.Vertex;

public class EdgePaint extends JPanel{
	
	private Edge e;
	private Color color;
	
	public EdgePaint(Edge e, Color color) {
		super();
		this.e = e;
		this.color = color;
	}


	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2d=(Graphics2D) g;
		g2d.setColor(this.color);
		
		Dimension size = getSize();
		int w=size.width;
		int h=size.height;
		
		Vertex q_1=this.e.getVertex1();
		Vertex q_2=this.e.getVertex2();
		
		int x_1=(int) q_1.getX() % w;
		int y_1= (int) q_1.getY() % h;
		int x_2=(int) q_2.getX() % w;
		int y_2= (int) q_2.getY() % h;
		g2d.drawLine(x_1, y_1, x_2, y_2);
		
	}
	
}

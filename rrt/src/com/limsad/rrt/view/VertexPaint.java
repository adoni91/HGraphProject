package com.limsad.rrt.view;
/**
 * @author Hamilton
 *
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.limsad.rrt.model.Vertex;

public class VertexPaint extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vertex q;
	private Color color;
	
	
	
	public VertexPaint() {
		super();
	}
	
	public VertexPaint(Vertex q, Color color) {
		super();
		this.q = q;
		this.color = color;
	}


	public Vertex getQ() {
		return q;
	}



	public void setQ(Vertex q) {
		this.q = q;
	}



	public Color getColor() {
		return color;
	}



	public void setColor(Color color) {
		this.color = color;
	}



	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2d=(Graphics2D) g;
		g2d.setStroke(new BasicStroke(4.0f));
		g2d.setColor(this.color);
		
		Dimension size = getSize();
		int w=size.width;
		int h=size.height;
		
		int x=(int) q.getX();
		int y= (int) q.getY();
		g2d.drawLine(x, y, x, y);
		
	}
	
	/*public void paint(Graphics g){
		Graphics2D g2d= (Graphics2D) g;
		   g2d.setStroke(new BasicStroke(3.0f)); 
		    Point2D P2D = new Point2D.Double(this.q.getX(), this.q.getY()); 
		   // Point2D P2D2 = new Point2D.Float(158.0f, 173.0f); 
		    Line2D L2D = new Line2D.Float(P2D, P2D); 
		    //Line2D L2D2 = new Line2D.Float(P2D, P2D2); 
		    g2d.draw(L2D); 
		    //g2d.draw(L2D2);

	}*/
	
}

package edu.virginia.engine.util;
/*
 * Leandra Irvine
 * lli5ba
 * Lab 2
 */

/*
 *Inherited Methods:
 * https://docs.oracle.com/javase/7/docs/api/java/awt/geom/Point2D.html
 */


public class Position extends java.awt.geom.Point2D{
	
	private double x;
	private double y;
		
	public Position(){
		 this.x = 0;
		 this.y = 0;
		}
	
	public Position(int x, int y){
		 this.x = x;
		 this.y = y;
		}
	
	public Position(double x, double y){
	 this.x = x;
	 this.y = y;
	}
	
	public double getxInt(){
		return (int)this.x;
	}
	
	
	public double getyInt(){
		return (int)this.y;
	}
	
	
	public double getX(){
		return this.x;
	}
	
	public void setX(double num){
		this.x = num;
	}
	
	public double getY(){
		return this.y;
	}
	
	public void setY(double num){
		this.y = num;
	}

	@Override //overriding toString() method that already exists for objects
	public String toString() {
		//DecimalFormat df = new DecimalFormat("0.0#"); 
		return "(" + x + ", " + y + ")";
	}
	
	@Override
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
		
	}
	
	
	


}

		


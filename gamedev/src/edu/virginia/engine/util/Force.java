package edu.virginia.engine.util;

public class Force {
	double xForce;
	double yForce;
	double timeRemaining;
	private String name;
	public double getxForce() {
		return xForce;
	}
	public void setxForce(double xForce) {
		this.xForce = xForce;
	}
	public double getyForce() {
		return yForce;
	}
	public void setyForce(double yForce) {
		this.yForce = yForce;
	}
	public double getTimeRemaining() {
		return timeRemaining;
	}
	public void setTimeRemaining(double timeRemaining) {
		this.timeRemaining = timeRemaining;
	}
	
	
	public Force(double xForce, double yForce, double timeRemaining, String name) {
		super();
		this.xForce = xForce;
		this.yForce = yForce;
		this.timeRemaining = timeRemaining;
		this.setName(name);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}

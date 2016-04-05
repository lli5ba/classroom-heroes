package edu.virginia.game.objects;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Sprite;

public class StudentHealthBar extends Sprite {

	private Sprite greenBar;
	
	public StudentHealthBar(String id) {
		super(id, "student/student-healthbar-red.png"); //red bar is in the back
		greenBar = new Sprite("student/student-healthbar-green.png");
		this.addChild(greenBar);
	}
	
	public void setHealthBar(double currHealth, double maxHealth) {
		
		this.greenBar.setScaleX(currHealth/maxHealth);
	}

}

package edu.virginia.game.objects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

<<<<<<< HEAD
public class StudentHealthBar extends Rectangle {
=======
import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Sprite;
>>>>>>> cd7e4de74449f626be652350717bb4479ac9aa2a

public class StudentHealthBar extends Sprite {

	private Sprite greenBar;
	
	public StudentHealthBar(String id) {
		super(id, "student/student-healthbar-red.png"); //red bar is in the back
		greenBar = new Sprite(id + "-green", "student/student-healthbar-green.png");
		this.addChild(greenBar);
	}
	
	public void setHealthBar(double currHealth, double maxHealth) {
		this.greenBar.setScaleX(currHealth/maxHealth);
	}
	
	@Override
	public void draw(Graphics g){
		super.draw(g); //draws children
		
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys){
		super.update(pressedKeys); //updates children
		
	}

}

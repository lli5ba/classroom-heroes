package edu.virginia.game.objects;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.util.Position;

public class Smokebomb extends AnimatedSprite{
	private Position finalPosGlobal;
	private boolean complete;
	private boolean exploding;
	
	public Smokebomb(String id, double xPos, double yPos) {
		super(id, "smokebomb/smokebomb-default.png", 
				"smokebomb/smokebomb-spritesheet.png", 
				"resources/smokebomb/smokebomb-spritesheet.txt");
		this.complete = false;
		this.exploding = false;
		finalPosGlobal= new Position(xPos, yPos);
		
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g); // draws children

	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys); // updates children
		//reached final position
		if (this.getyPosGlobal() == this.finalPosGlobal.getY()
				&& this.getxPosGlobal() == this.finalPosGlobal.getY()
				&& !this.exploding && !this.complete) {
			this.exploding = true;
			//choose random smoke pattern
			Random rand1 = new Random();
			 // between 1 and 3 inclusive
			int smokeVar = (int) (rand1.nextDouble() * 3) + 1;
			this.animateOnce("smoke" + smokeVar, 1);
		}
		//mark as complete
		if (this.exploding && !this.complete && !this.isPlaying()) {
			this.exploding = false;
			this.complete = true;
		}
	}

	public boolean isComplete() {
		return complete;
	}

	public boolean isExploding() {
		return exploding;
	}

	public static Position generatePosition(String dir, double range, double centerx, double centery) {
		
		double angleRad = Math.toRadians(0);
		
		double x = centerx + range * Math.cos(angleRad);
		double y = centery + range * Math.sin(angleRad);
		
		return new Position(x, y);
	}
	

}

package edu.virginia.engine.display;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import edu.virginia.engine.events.CollisionEvent;
import edu.virginia.engine.util.Position;

/**
 * Nothing in this class (yet) because there is nothing specific to a Sprite yet that a DisplayObject
 * doesn't already do. Leaving it here for convenience later. you will see!
 * */
public class Sprite extends DisplayObjectContainer {
	private Rectangle hitbox;
	
	
	public Sprite(String id) {
		super(id);
		Rectangle hitbox = new Rectangle();
		hitbox.setBounds((int)this.getxPos(), (int)this.getyPos(), this.getUnscaledWidth(), this.getUnscaledHeight());
		this.setHitbox(hitbox);
		
	}

	public Sprite(String id, String imageFileName) {
		super(id, imageFileName);
		Rectangle hitbox = new Rectangle();
		hitbox.setBounds((int)this.getxPos(), (int)this.getyPos(), this.getUnscaledWidth(), this.getUnscaledHeight());
		this.setHitbox(hitbox);
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
	}
	
	/*   Hitbox Methods */
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	
	
	private void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}
	
	private void moveHitbox() {
		Rectangle newHitbox = new Rectangle();
		newHitbox.setBounds((int)this.getxPos(), (int)this.getyPos(), 
				(int) (this.getWidth()), 
				(int) (this.getHeight()));
		this.setHitbox(newHitbox);
	}
	

	public boolean collidesWithGlobal(DisplayObjectContainer other) {
		if (this.getHitboxGlobal().intersects(other.getHitboxGlobal())) {
			this.dispatchEvent(new CollisionEvent(CollisionEvent.COLLISION, this));
			return true;
		}
		return false;	
	}
	
	public boolean inRangeGlobal(DisplayObjectContainer other, int range) {
		Rectangle original = this.getHitboxGlobal();
		//create rectangle that extends out "range" from original hitbox
		original.grow(range, range);
		if (original.intersects(other.getHitboxGlobal())) {
			return true;
		}
		return false;	
	}
	
	public void drawHitboxGlobal(Graphics g) {
		int x = this.getHitboxGlobal().x;
		int y = this.getHitboxGlobal().y;
		int width = this.getHitboxGlobal().width;
		int height = this.getHitboxGlobal().height;
		g.fillRect(x, y, width, height);
	}
	
	public void drawHitbox(Graphics g) {
		int x = this.getHitbox().x;
		int y = this.getHitbox().y;
		int width = this.getHitbox().width;
		int height = this.getHitbox().height;
		g.fillRect(x, y, width, height);
	}
	
	/*public void obstacleCollision(DisplayObject sprite){
		Rectangle obstacle = sprite.getHitbox();
		boolean insideRightBound;
		boolean insideLeftBound;
		boolean belowUpperBound;
		boolean aboveLowerBound;
		if(this.hitbox.intersects(obstacle)){ //If true, should move away from rectangle
			insideRightBound = (this.hitbox.getMinX() > obstacle.getMinX()); //true if inside Right bounc
			insideLeftBound = (this.hitbox.getMaxX() < obstacle.getMaxX());
			belowUpperBound = (this.hitbox.getMinY() > obstacle.getMinY());
			aboveLowerBound = (this.hitbox.getMaxY() < obstacle.getMaxY());
			
			if(!belowUpperBound){
				//Move Up
				//this.setyPos((float) (this.yCurrent - (1.25)*this.yChange));
				
				this.setyPos(obstacle.getMinY() - this.getHitbox().getHeight());
				this.setOnGround(true);
			} else {
				this.setOnGround(false);
			}
			if(!aboveLowerBound){
				//Move down
				this.setyPos(obstacle.getMaxY());
			}
			if(!insideRightBound){
				//Move right
				this.setxPos(obstacle.getMinX() - this.getHitbox().getWidth());
			}
			if(!insideLeftBound){
				//Move left
				this.setxPos(obstacle.getMaxX());
			}
			
			
		} 
			
	}*/
	
	




	
	
}
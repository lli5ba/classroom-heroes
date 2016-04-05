package edu.virginia.engine.display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import edu.virginia.engine.events.CollisionEvent;

public class DisplayObjectContainer extends DisplayObject{

	private ArrayList<DisplayObjectContainer> childObjects;
	public boolean drawChildren;
	public boolean updateChildren;
	private DisplayObjectContainer parentObject;
	
	public void setParentObject(DisplayObjectContainer parent) {
		this.parentObject = parent;
	}
	
	public DisplayObjectContainer getParentObject() {
		return this.parentObject;
	}
	
	public boolean hasParentObject() {
		return (this.parentObject != null);
	}
	
	public DisplayObjectContainer(String id) {
		super(id);
		this.drawChildren = true;
		this.updateChildren = true;
		this.childObjects = new ArrayList<DisplayObjectContainer>();
	}

	public DisplayObjectContainer(String id, String imageFileName) {
		super(id, imageFileName);
		this.drawChildren = true;
		this.updateChildren = true;
		this.childObjects = new ArrayList<DisplayObjectContainer>();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DisplayObjectContainer other = (DisplayObjectContainer) obj;
		if (childObjects == null) {
			if (other.childObjects != null)
				return false;
		} else if (!childObjects.equals(other.childObjects))
			return false;
		if (drawChildren != other.drawChildren)
			return false;
		return true;
	}

	public boolean isDrawChildren() {
		return drawChildren;
	}

	public void setDrawChildren(boolean drawChildren) {
		this.drawChildren = drawChildren;
	}

	public boolean addChild(DisplayObjectContainer child) {
		child.setParentObject(this);
		return this.childObjects.add(child);
	}
	
	public void addChildAtIndex(int index, DisplayObjectContainer child) {
		child.setParentObject(this);
		this.childObjects.add(index, child);
	}
	
	public boolean removeChild(DisplayObjectContainer child) {
		child.setParentObject(null);
		return this.childObjects.remove(child);
	}
	
	public DisplayObject removeByIndex(int index) {
		this.childObjects.get(index).setParentObject(null);
		return this.childObjects.remove(index);
	}
	
	public void removeAll() {
		for (DisplayObjectContainer child_i : this.childObjects) {
			child_i.setParentObject(null);
		}
		this.childObjects.clear();
		
	}
	
	public boolean contains(DisplayObject child) {
		return this.childObjects.contains(child);
	}
	
	public DisplayObject getChild(String stringid) {
		for (DisplayObject obj : this.childObjects) {
			if (obj.getId().equals(stringid)) {
				return obj; 
			}
		}
		return null;
	}
	
	public DisplayObject getChild(int index) {
		return this.childObjects.get(index);
	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		if (this.updateChildren) {
			for(DisplayObject obj : this.childObjects) {
				obj.update(pressedKeys);
			}
		}
	}
	
	@Override
	public void draw(Graphics g) {
			
			Graphics2D g2d = (Graphics2D) g;
			applyTransformations(g2d);
			
			if (this.getDisplayImage() != null && this.isVisible()) {
				/* Actually draw the image, perform the pivot point translation here */
				g2d.drawImage(this.getDisplayImage(), 0, 0,
						(int) (getUnscaledWidth()),
						(int) (getUnscaledHeight()), null);
			}
			
			if (this.drawChildren) {
				for(DisplayObject obj : this.childObjects) {
					obj.draw(g);
				}
			}
			
			reverseTransformations(g2d);
		}
		
	//fix hitbox methods to work globally
	public Rectangle getHitboxGlobal() {
		Rectangle globalHitbox = new Rectangle();
		globalHitbox.setBounds(
				(int) (this.getxPosGlobal() + this.getOriginalHitbox().getX()), 
				(int) (this.getyPosGlobal() + this.getOriginalHitbox().getY()), 
				(int) (this.getOriginalHitbox().getWidth()*this.getScaleXGlobal()), 
				(int) (this.getOriginalHitbox().getHeight()*this.getScaleYGlobal()));
		return globalHitbox;
	}
	
	public double getxPosGlobal() {
		if (!this.hasParentObject()) {
			return this.getxPos();
		} else {
			return this.getxPos()*this.getParentObject().getScaleX() 
					+ this.getParentObject().getxPosGlobal();
		}
	}
	public double getyPosGlobal() {
		if (!this.hasParentObject()) {
			return this.getyPos();
		} else {
			return this.getyPos()*this.getParentObject().getScaleY() 
					+ this.getParentObject().getyPosGlobal();
		}
	}
	
	public double getScaleXGlobal() {
		if (!this.hasParentObject()) {
			return this.getScaleX();
		} else {
			return this.getScaleX() * this.getParentObject().getScaleXGlobal();
		}
	}
	
	public double getScaleYGlobal() {
		if (!this.hasParentObject()) {
			return this.getScaleY();
		} else {
			return this.getScaleY() * this.getParentObject().getScaleYGlobal();
		}
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
	
}

package edu.virginia.engine.display;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import edu.virginia.engine.events.CollisionEvent;
import edu.virginia.engine.events.EventDispatcher;
import edu.virginia.engine.util.Position;

/**
 * A very basic display object for a java based gaming engine
 * 
 * */
public class DisplayObject extends EventDispatcher {

	private DisplayObject parent;
	/* All DisplayObject have a unique id */
	private String id;

	/* The image that is displayed by this object */
	private BufferedImage displayImage;
	
	private boolean visible;
	private Position position;
	private Position pivotPoint;
	private double scaleX; 	//1.0 is actual size
	private double scaleY;	//1.0 is actual size
	private double rotation; //in degrees
	private float alpha; //1.0f is solid
	private Rectangle originalHitbox; //position of hitbox assuming that the character is Unscaled and at position (0, 0)
	private Rectangle hitbox; //current hitbox moved and scaled with character
	private boolean onGround;
	
	/**
	 * Constructors: can pass in the id OR the id and image's file path and
	 * position OR the id and a buffered image and position
	 */
	public DisplayObject(String id) {
		this.setId(id);
		this.setPivotPoint(new Position(0, 0));
		this.setVisible(true);
		this.setAlpha(1.0f);
		this.position = (new Position(0, 0));
		this.setRotationDegrees(0);
		this.setScaleX(1.0);
		this.setScaleY(1.0);
		this.setHitbox(hitbox);
		this.setOriginalHitbox(new Rectangle((int)0, (int)0, this.getUnscaledWidth(), this.getUnscaledHeight()));
		this.setHitbox(new Rectangle((int)0, (int)0, this.getUnscaledWidth(), this.getUnscaledHeight()));
		this.setOnGround(false);
		
	}


	public DisplayObject(String id, String fileName) {
		this.setId(id);
		this.setPivotPoint(new Position(0, 0));
		this.setImage(fileName);
		this.setVisible(true);
		this.setAlpha(1.0f);
		this.position = (new Position(0, 0));
		this.setRotationDegrees(0);
		this.setScaleX(1.0);
		this.setScaleY(1.0);
		this.setOriginalHitbox(new Rectangle((int)0, (int)0, this.getUnscaledWidth(), this.getUnscaledHeight()));
		this.setHitbox(new Rectangle((int)0, (int)0, this.getUnscaledWidth(), this.getUnscaledHeight()));
		this.setOnGround(false);
	}
	
	public DisplayObject(String id, String fileName, 
			double xPos, double yPos) {
		this.setId(id);
		this.setPivotPoint(new Position(0, 0));
		this.setImage(fileName);
		this.setVisible(true);
		this.setAlpha(1.0f);
		this.position = (new Position(xPos, yPos));
		this.setRotationDegrees(0);
		this.setScaleX(1.0);
		this.setScaleY(1.0);
		this.setOriginalHitbox(new Rectangle((int)0, (int)0, this.getUnscaledWidth(), this.getUnscaledHeight()));
		this.setHitbox(new Rectangle((int)xPos, (int)yPos, this.getUnscaledWidth(), this.getUnscaledHeight()));
		this.setOnGround(false);
	}
	
	
	/*
	 * Getters and Setters
	 */

	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	public Rectangle getHitbox() {
		return hitbox;
	}
	
	
	
	private void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}
	
	private void moveHitbox() {
		Rectangle newHitbox = new Rectangle();
		newHitbox.setBounds((int)(this.getxPos() + this.getOriginalHitbox().getX()), 
				(int) (this.getyPos() + this.getOriginalHitbox().getY()), 
				(int) (this.getOriginalHitbox().getWidth()*this.scaleX), 
				(int) (this.getOriginalHitbox().getHeight()*this.scaleY));
		this.setHitbox(newHitbox);
	}
	
	
	public void obstacleCollision(DisplayObject sprite){
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
			
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Position getPosition() {
		return position;
	}
	
	public double getxPos() {
		return this.position.getX();
	}

	public void setxPos(double xPos) {
		this.position.setX(xPos);
		this.moveHitbox();
	}

	public double getyPos() {
		return this.position.getY();
	}

	public void setyPos(double yPos) {
		this.position.setY(yPos);
		this.moveHitbox();
	}

	public void setPosition(double x, double y) {
		this.position.setX(x);
		this.position.setY(y);
		this.moveHitbox();
	} 
	
	public void setPosition(Position position) {
		this.position = position;
		this.moveHitbox();
	}

	public Position getPivotPoint() {
		return pivotPoint;
	}

	public void setPivotPoint(Position pivotPoint) {
		this.pivotPoint = pivotPoint;
	}

	public double getWidth() {
		return (double)(this.getUnscaledWidth()*this.getScaleX());
	}

	public double getHeight() {
		return (double)(this.getUnscaledHeight()*this.getScaleY());
	}
	
	public void setHeight(double newHeight) {
		this.setScaleY(newHeight/(double)this.getUnscaledHeight());
	}
	
	public void setWidth(double newWidth) {
		this.setScaleX(newWidth/(double)this.getUnscaledWidth());
	}
	
	public double getScaleX() {
		return scaleX;
	}

	public void setScaleX(double scaleX) {
		this.scaleX = scaleX;
	}

	public double getScaleY() {
		return scaleY;
	}

	public void setScaleY(double scaleY) {
		this.scaleY = scaleY;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotationDegrees(double degrees) {
		this.rotation = Math.toRadians(degrees);
	}
	
	public void setRotationRadians(double rotation) {
		this.rotation = rotation;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
	public void setPivotPoint(double x, double y) {
		this.pivotPoint.setX(x);
		this.pivotPoint.setY(y);	
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}


	/**
	 * Returns the unscaled width and height of this display object
	 * */
	public int getUnscaledWidth() {
		if(displayImage == null) return 0;
		return displayImage.getWidth();
	}

	public int getUnscaledHeight() {
		if(displayImage == null) return 0;
		return displayImage.getHeight();
	}

	public BufferedImage getDisplayImage() {
		return this.displayImage;
	}

	protected void setImage(String imageName) {
		setVisible(true);
		if (imageName == null) {
			return;
		}
		displayImage = readImage(imageName);
		if (displayImage == null) {
			System.err.println("[DisplayObject.setImage] ERROR: " + imageName + " does not exist!");
		}
		this.setPivotPoint(new Position(this.getUnscaledWidth()/2,this.getUnscaledHeight()/2));
	}
	
	public Rectangle getOriginalHitbox() {
		return originalHitbox;
	}

	public void setOriginalHitbox(Rectangle originalHitbox) {
		this.originalHitbox = originalHitbox;
	}


	/**
	 * Helper function that simply reads an image from the given image name
	 * (looks in resources\\) and returns the bufferedimage for that filename
	 * */
	public BufferedImage readImage(String imageName) {
		BufferedImage image = null;
		try {
			String file = ("resources" + File.separator + imageName);
			image = ImageIO.read(new File(file));
		} catch (IOException e) {
			System.out.println("[Error in DisplayObject.java:readImage] Could not read image " + imageName);
			e.printStackTrace();
		}
		return image;
	}

	public void setImage(BufferedImage image) {
		if(image == null) return;
		displayImage = image;
	}


	/**
	 * Invoked on every frame before drawing. Used to update this display
	 * objects state before the draw occurs. Should be overridden if necessary
	 * to update objects appropriately.
	 * */
	protected void update(ArrayList<String> pressedKeys) {
		this.moveHitbox();
	}

	/**
	 * Draws this image. This should be overloaded if a display object should
	 * draw to the screen differently. This method is automatically invoked on
	 * every frame.
	 * */
	public void draw(Graphics g) {
		
		if (displayImage != null && this.visible) {
			
			/*
			 * Get the graphics and apply this objects transformations
			 * (rotation, etc.)
			 */
			Graphics2D g2d = (Graphics2D) g;
			applyTransformations(g2d);
			
			/* Actually draw the image, perform the pivot point translation here */
			g2d.drawImage(displayImage, 0, 0,
					(int) (getUnscaledWidth()),
					(int) (getUnscaledHeight()), null);
			
			/*
			 * undo the transformations so this doesn't affect other display
			 * objects
			 */
			reverseTransformations(g2d);
		}
	}

	/**
	 * Applies transformations for this display object to the given graphics
	 * object
	 * */
	protected void applyTransformations(Graphics2D g2d) {
		g2d.setComposite(makeComposite(this.alpha));
		g2d.translate(this.getxPos(), this.getyPos());
		g2d.rotate(this.getRotation(), this.pivotPoint.getX(), this.pivotPoint.getY());
		g2d.scale(this.getScaleX(), this.getScaleY());
		
	}

	/**
	 * Reverses transformations for this display object to the given graphics
	 * object
	 * */
	protected void reverseTransformations(Graphics2D g2d) {
		g2d.scale((1/this.getScaleX()), (1/this.getScaleY()));
		g2d.rotate(-this.getRotation(), this.pivotPoint.getX(), this.pivotPoint.getY());
		g2d.translate(-this.getxPos(), -this.getyPos());
		
		
		g2d.setComposite(makeComposite(1.0f));
		
		
	}
	
	private AlphaComposite makeComposite(float alpha) {
		  int type = AlphaComposite.SRC_OVER;
		  return(AlphaComposite.getInstance(type, alpha));
	}
	
	/**
	 * Compares two images pixel by pixel.
	 *
	 * @param imgA the first image.
	 * @param imgB the second image.
	 * @return whether the images are both the same or not.
	 */
	public static boolean compareImages(BufferedImage imgA, BufferedImage imgB) {
	  // The images must be the same size.
	  if (imgA.getWidth() == imgB.getWidth() && imgA.getHeight() == imgB.getHeight()) {
	    int width = imgA.getWidth();
	    int height = imgA.getHeight();

	    // Loop over every pixel.
	    for (int y = 0; y < height; y++) {
	      for (int x = 0; x < width; x++) {
	        // Compare the pixels for equality.
	        if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
	          return false;
	        }
	      }
	    }
	  } else {
	    return false;
	  }

	  return true;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DisplayObject other = (DisplayObject) obj;
		if (Float.floatToIntBits(alpha) != Float.floatToIntBits(other.alpha))
			return false;
		if (displayImage == null) {
			if (other.displayImage != null)
				return false;
		} else if (!compareImages(displayImage, other.displayImage))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (pivotPoint == null) {
			if (other.pivotPoint != null)
				return false;
		} else if (!pivotPoint.equals(other.pivotPoint))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (Double.doubleToLongBits(rotation) != Double.doubleToLongBits(other.rotation))
			return false;
		if (Double.doubleToLongBits(scaleX) != Double.doubleToLongBits(other.scaleX))
			return false;
		if (Double.doubleToLongBits(scaleY) != Double.doubleToLongBits(other.scaleY))
			return false;
		if (visible != other.visible)
			return false;
		return true;
	}

}
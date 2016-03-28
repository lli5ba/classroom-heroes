package edu.virginia.engine.display;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import edu.virginia.lab1test.PickedUpEvent;


public class Player extends AnimatedSprite {
	public static final String[] CARDINAL_DIRS = new String[] {"up", "down","left", "right"};
	private double speed;
	private int numPlayer;
	private int health;
	private int maxHealth;
	private int experience;			//total for the entire game 
	private int level;
	private Sprite net;
	private int swingSpeed;
	//TODO: key-mapping fields for when we have 2 players
	
	
	public Player(String id, String imageFileName, String thisSheetFileName, String specsFileName, int num)
	{
		super(id, imageFileName, thisSheetFileName, specsFileName);
		speed = 1;
		numPlayer = num;
		maxHealth = 5;
		health = maxHealth;
		experience = 0;
		level = 1;
		net = new Sprite("net", imageFileName);
		net.setScaleX(1.5);
		net.setScaleY(1.5);
		this.addChild(net);
		net.setAlpha(0);
		this.setScaleX(1.5);
		this.setScaleY(1.5);
		
	}
	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		if (this != null && this.net != null) {
			moveSpriteCartesianAnimate(pressedKeys);
			//if there are no keys being pressed, and Lily is walking, then stop the animation
			if (pressedKeys.isEmpty() && this.isPlaying() 
					&& Arrays.asList(CARDINAL_DIRS).contains(this.getCurrentAnimation())){
				this.stopAnimation();
			} 
			
			/* Example of collision checking
			 * 
			 * if (this.floor != null) {
				lily2.obstacleCollision(floor);
				
			}
			if (this.platform != null) {
				lily2.obstacleCollision(platform);
				
			} */
			
		}
	}

	public void moveSpriteCartesianAnimate(ArrayList<String> pressedKeys){
		/* Make sure this is not null. Sometimes Swing can auto cause an extra frame to go before everything is initialized */
		if(this != null && net != null) {
			/* update mario's position if a key is pressed, check bounds of canvas */
			if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_UP))) {
				if(this.getyPos() > 0) {
					this.setyPos(this.getyPos() - this.speed);
				}
				if(!this.isPlaying() || this.getCurrentAnimation() != "up") {
					this.animate("up");
				}
				this.setDirection("up");
				this.moveNet(this, net, "up");
				
			}
			if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_DOWN))) {
				if(this.getyPos() < gameHeight - this.getUnscaledHeight()*this.getScaleY()){
					this.setyPos(this.getyPos() + speed);
				}
				if(!this.isPlaying() || this.getCurrentAnimation() != "down") {
					this.animate("down");
				}
				this.setDirection("down");
				moveNet(this, net, "down");
			}
			if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_LEFT))) {
				if(this.getxPos() > 0) {
					this.setxPos(this.getxPos() - speed);
				}
				if(!this.isPlaying() || this.getCurrentAnimation() != "left") {
					this.animate("left");
				}
				this.setDirection("left");
				moveNet(this, net, "left");
			}
			if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_RIGHT))) {
				
				if(this.getxPos() < gameWidth - this.getUnscaledWidth()*this.getScaleX()){
					this.setxPos(this.getxPos() + speed);
				}
				if(!this.isPlaying() || this.getCurrentAnimation() != "right") {
					this.animate("right");
				}
				this.setDirection("right");
				moveNet(this, net, "right");
			}
			if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_SPACE))) {
				String currentDir = this.getDirection();
				//Until we have combined net and walking animation, net animation overrides walking animation
				
				if (this.isPlaying() && !this.getCurrentAnimation().contains("net")) {
					System.out.println("STOPPING\n");
					this.stopAnimation();
				}

				this.animateOnce("net" + currentDir, 10);
				for(PickedUpItem vp : vpList) {
					if (net.collidesWithGlobal(vp) && !vp.isPickedUp()) {
						vp.dispatchEvent(new PickedUpEvent(PickedUpEvent.KEY_PICKED_UP, vp));
						vp.setPickedUp(true);
					}
				}
			}
		}
	}
	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getSwingSpeed() {
		return swingSpeed;
	}

	public void setSwingSpeed(int swingSpeed) {
		this.swingSpeed = swingSpeed;
	}

	public int getNumPlayer() {
		return numPlayer;
	}

	public Sprite getNet() {
		return net;
	}
	
	
	

}

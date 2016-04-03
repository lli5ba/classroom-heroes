package edu.virginia.game.objects;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.PickedUpItem;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.util.Position;
import edu.virginia.game.main.PickedUpEvent;
import edu.virginia.game.managers.GameManager;
import edu.virginia.game.managers.LevelManager;
import edu.virginia.game.managers.PlayerManager;


public class Player extends AnimatedSprite {
	public static final String[] CARDINAL_DIRS = new String[] {"up", "down","left", "right"};
	private Sprite net;
	private PlayerManager playerManager = PlayerManager.getInstance();
	private LevelManager levelManager = LevelManager.getInstance();
	private GameManager gameManager = GameManager.getInstance();
	private int numPlayer;
	private boolean active; //whether movement works
	
	
	public Player(String id, String imageFileName, String thisSheetFileName, String specsFileName, 
			int numPlayer)
	{
		super(id, imageFileName, thisSheetFileName, specsFileName);
		this.numPlayer = numPlayer;
		this.active = true; 
		
		net = new Sprite("net", imageFileName);
		this.addChild(net);
		net.setAlpha(0); 
		this.setPivotPoint(new Position(this.getWidth()/2,this.getHeight()/2));
		
		
	}
	

	public int getNumPlayer() {
		return numPlayer;
	}
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

	public Sprite getNet() {
		return net;
	}
	
	public void moveNet(String position) {
		if(position.equals("up")) {
			//net.setRotationDegrees(-90);
			net.setyPos(-(this.getWidth()));
			net.setxPos(-(this.getWidth()/4.0));
			net.setWidth(this.getHeight());
			net.setHeight(this.getWidth());
		}
		else if(position.equals("down")) {
			//net.setRotationDegrees(90);
			net.setyPos(this.getHeight());
			net.setxPos(-(this.getWidth()/4.0));
			net.setWidth(this.getHeight());
			net.setHeight(this.getWidth());
		}
		else if(position.equals("right")) {
			//net.setRotationDegrees(0);
			net.setxPos((this.getUnscaledWidth()*this.getScaleX()));
			net.setyPos(0);
			net.setWidth(this.getWidth());
			net.setHeight(this.getHeight());
		}
		else if(position.equals("left")) {
			//net.setRotationDegrees(180);
			net.setxPos(-(this.getUnscaledWidth()*this.getScaleX()));
			net.setyPos(0);
			net.setWidth(this.getWidth());
			net.setHeight(this.getHeight());
		}
	}
	
	public void moveSpriteCartesianAnimate(ArrayList<String> pressedKeys){
		double speed = this.playerManager.getSpeed(this.numPlayer);
		/* Make sure this is not null. Sometimes Swing can auto cause an extra frame to go before everything is initialized */
		if(this != null && net != null) {
			/* update mario's position if a key is pressed, check bounds of canvas */
			if(pressedKeys.contains(this.playerManager.getUpKey(this.numPlayer))) {
				if(this.getyPos() > 0) {
					this.setyPos(this.getyPos() - speed);
				}
				if(!this.isPlaying() || this.getCurrentAnimation() != "up") {
					this.animate("up");
				}
				this.setDirection("up");
				this.moveNet( "up");
				
			}
			if (pressedKeys.contains(this.playerManager.getDownKey(this.numPlayer))) {
				if(this.getyPos() < this.gameManager.getGameHeight() - this.getHeight()){
					this.setyPos(this.getyPos() + speed);
				}
				if(!this.isPlaying() || this.getCurrentAnimation() != "down") {
					this.animate("down");
				}
				this.setDirection("down");
				moveNet("down");
			}
			if(pressedKeys.contains(this.playerManager.getLeftKey(this.numPlayer))) {
				if(this.getxPos() > 0) {
					this.setxPos(this.getxPos() - speed);
				}
				if(!this.isPlaying() || this.getCurrentAnimation() != "left") {
					this.animate("left");
				}
				this.setDirection("left");
				moveNet("left");
			}
			if (pressedKeys.contains(this.playerManager.getRightKey(this.numPlayer))) {
				
				if(this.getxPos() < this.gameManager.getGameWidth() - this.getWidth()){
					this.setxPos(this.getxPos() + speed);
				}
				if(!this.isPlaying() || this.getCurrentAnimation() != "right") {
					this.animate("right");
				}
				this.setDirection("right");
				moveNet("right");
			}
			if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_SPACE))) {
				String currentDir = this.getDirection();
				//Until we have combined net and walking animation, net animation overrides walking animation
				
				if (this.isPlaying() && !this.getCurrentAnimation().contains("net")) {
					System.out.println("STOPPING\n");
					this.stopAnimation();
				}

				this.animateOnce("net" + currentDir, 10);
				/* Move this to Classroom class
				for(PickedUpItem vp : vpList) {
					if (net.collidesWithGlobal(vp) && !vp.isPickedUp()) {
						vp.dispatchEvent(new PickedUpEvent(PickedUpEvent.KEY_PICKED_UP, vp));
						vp.setPickedUp(true);
					}
				}
				*/ 
			}
		}
	}

	

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		if (this != null && this.net != null) {
			if(this.isActive()) {
				moveSpriteCartesianAnimate(pressedKeys);
			}
			//if there are no keys being pressed, and sprite is walking, then stop the animation
			if (pressedKeys.isEmpty() && this.isPlaying() 
					&& Arrays.asList(CARDINAL_DIRS).contains(this.getCurrentAnimation())){
				this.stopAnimation();
			} 
			
			
		}
	}


}

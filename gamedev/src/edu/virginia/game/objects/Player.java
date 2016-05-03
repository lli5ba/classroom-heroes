package edu.virginia.game.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.FrameInfo;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.CollisionEvent;
import edu.virginia.engine.events.GameEvent;
import edu.virginia.engine.util.Position;
import edu.virginia.game.main.PickedUpEvent;
import edu.virginia.game.managers.GameManager;
import edu.virginia.game.managers.LevelManager;
import edu.virginia.game.managers.PlayerManager;
import edu.virginia.game.managers.SoundManager;

public class Player extends AnimatedSprite {
	public static final String[] CARDINAL_DIRS = new String[] { "up", "down", "left", "right" };
	private PlayerManager playerManager = PlayerManager.getInstance();
	private LevelManager levelManager = LevelManager.getInstance();
	private GameManager gameManager = GameManager.getInstance();
	private SoundManager soundManager;
	private int numPlayer;
	private boolean active; // whether movement works
	private int vpCount;
	private AnimatedSprite poisonBubbles;
	private Net net;
	private Sprite smokebomb;
	private String smokebombDir;
	private PlayerHealthBar healthBar;

	public Player(String id, String imageFileName, String thisSheetFileName, String specsFileName, int numPlayer) {
		super(id, imageFileName, thisSheetFileName, specsFileName);
		
		try {
			this.soundManager = SoundManager.getInstance();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		this.addEventListener(soundManager, EventTypes.SWING_NET.toString());
		this.addEventListener(soundManager, EventTypes.WALK.toString());
		this.numPlayer = numPlayer;
		this.active = true;
		
		/* add smokebomb */
		smokebomb = new Sprite(id + "-bomb", "smokebomb/smokebomb-default.png");
		this.addChild(smokebomb);
		this.smokebomb.setVisible(false);
		
		/* add net */
		net = new Net(id + "-net" , "resources/player/player-spritesheet-1-netFrameInfo.txt");
		this.addChild(net);
		
		/* add bubbles */
		poisonBubbles = new AnimatedSprite("bubbles", "bubbles/bubble-default.png", 
				"bubbles/bubble-spritesheet.png", "resources/bubbles/bubble-spritesheet.txt");
		this.addChild(poisonBubbles);
		this.poisonBubbles.setCenterPos(this.getWidth()*.75, -this.getHeight()*0.05);
		this.setPivotPoint(new Position(this.getWidth() / 2, this.getHeight() / 2));
		
		/* add healthbar */
		this.healthBar = new PlayerHealthBar(id + "-healthBar", numPlayer);
		this.addChild(healthBar);
		this.healthBar.setPosition(0, -10); // float over head
	}
	
	public void showHealthBar(double seconds) {
		this.healthBar.play(seconds);
	}
	/**global position returned */
	public Position getSmokebombPos() {
		return this.smokebomb.getCenterPosGlobal();
	}
	
	public void animateBubbles(){
		this.poisonBubbles.animateOnce("bubble");
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

	public int getVP() {
		return vpCount;
	}

/*
	public void moveNet(String position) {
		if (position.equals("up")) {
			// net.setRotationDegrees(-90);
			net.setyPos(-(this.getWidth()));
			net.setxPos(-(this.getWidth() / 4.0));
			net.setWidth(this.getHeight());
			net.setHeight(this.getWidth());

		} else if (position.equals("down")) {
			// net.setRotationDegrees(90);
			net.setyPos(this.getHeight());
			net.setxPos(-(this.getWidth() / 4.0));
			net.setWidth(this.getHeight());
			net.setHeight(this.getWidth());
		} else if (position.equals("right")) {
			// net.setRotationDegrees(0);
			net.setxPos((this.getUnscaledWidth() * this.getScaleX()));
			net.setyPos(0);
			net.setWidth(this.getWidth());
			net.setHeight(this.getHeight());
		} else if (position.equals("left")) {
			// net.setRotationDegrees(180);
			net.setxPos(-(this.getUnscaledWidth() * this.getScaleX()));
			net.setyPos(0);
			net.setWidth(this.getWidth());
			net.setHeight(this.getHeight());
		}
	}
*/
	public void moveSpriteCartesianAnimate(ArrayList<String> pressedKeys) {
		double speed = this.playerManager.getSpeed(this.numPlayer);
		/*
		 * Make sure this is not null. Sometimes Swing can auto cause an extra
		 * frame to go before everything is initialized
		 */
		if (this != null && this.net != null) {
			/*
			 * update mario's position if a key is pressed, check bounds of
			 * canvas
			 */
			if (pressedKeys.contains(this.playerManager.getUpKey(this.numPlayer))) {
				if (this.getyPos() > 0) {
					this.setyPos(this.getyPos() - speed);
				}
				if (!this.isPlaying() || this.getCurrentAnimation() != "up") {
					this.animate("up");
				}
				this.setDirection("up");
				this.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), this));
				//this.moveNet("up");

			}
			if (pressedKeys.contains(this.playerManager.getDownKey(this.numPlayer))) {
				if (this.getyPos() < this.gameManager.getGameHeight() - this.getHeight()) {
					this.setyPos(this.getyPos() + speed);
				}
				if (!this.isPlaying() || this.getCurrentAnimation() != "down") {
					this.animate("down");
				}
				this.setDirection("down");
				this.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), this));
				//moveNet("down");
			}
			if (pressedKeys.contains(this.playerManager.getLeftKey(this.numPlayer))) {
				if (this.getxPos() > 0) {
					this.setxPos(this.getxPos() - speed);
				}
				if (!this.isPlaying() || this.getCurrentAnimation() != "left") {
					this.animate("left");
				}
				this.setDirection("left");
				this.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), this));
				//moveNet("left");
			}
			if (pressedKeys.contains(this.playerManager.getRightKey(this.numPlayer))) {

				if (this.getxPos() < this.gameManager.getGameWidth() - this.getWidth()) {
					this.setxPos(this.getxPos() + speed);
				}
				if (!this.isPlaying() || this.getCurrentAnimation() != "right") {
					this.animate("right");
				}
				this.setDirection("right");
				this.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), this));
				//moveNet("right");
			}
			if (pressedKeys.contains(this.playerManager.getPrimaryKey(this.numPlayer))) {
				String currentDir = this.getDirection();
				// Until we have combined net and walking animation, net
				// animation overrides walking animation

				if (this.isPlaying() && !this.getCurrentAnimation().contains("net")) {
					// System.out.println("STOPPING\n");
					this.stopAnimation();
				}

				this.animateOnce("net" + currentDir, 5);
				this.dispatchEvent(new GameEvent(EventTypes.SWING_NET.toString(), this));
			}
		}
	}


	@Override
	public void updateAnimation(){
		if (this.isPlaying()) {
			// Stop if done looping
			// System.out.println("playing animation");
			// System.out.println("Current frame: " + currentFrame);
			if (!this.isLooping() && this.getTimesLooped() == 1) {
				this.setNetHitbox(new Rectangle (0,0,0,0));
				this.stopAnimation();
			}
			// Update sprite to next frame if enough time has passed
			if (this.getGameClockAnimation().getElapsedTime() > (AVE_DRAW / (this.getAnimationSpeed() * .1))) {
				if (getSpriteMap().containsKey(getCurrentAnimation())
						&& getSpriteMap().get(getCurrentAnimation()).size() >= (this.getCurrentFrame() + 1)) {

					this.setImage(getSpriteMap().get(getCurrentAnimation()).get(this.getCurrentFrame()).getImage());
					this.setOriginalHitbox(getSpriteMap().get(getCurrentAnimation()).get(this.getCurrentFrame()).getHitbox());
					//this.net.setOriginalHitbox(getSpriteMap().get(getCurrentAnimation()).get(this.getCurrentFrame()).getHitbox());
					if (this.net != null && this.net.getNetHitboxMap() != null
							&& this.net.getNetHitboxMap().containsKey(getCurrentAnimation())) {
						this.net.setNetHitbox(this.net.getNetHitboxMap()
								.get(getCurrentAnimation()).get(this.getCurrentFrame()).getHitbox());
					}
				}
				this.increaseFrame();
				this.getGameClockAnimation().resetGameClock();
			}
		}
	}

	
	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		/*
		if (this != null && this.net != null) {
			if (this.isActive()) {
				moveSpriteCartesianAnimate(pressedKeys);
			}
			// if there are no keys being pressed, and sprite is walking, then
			// stop the animation
			if (pressedKeys.isEmpty() && this.isPlaying()
					&& Arrays.asList(CARDINAL_DIRS).contains(this.getCurrentAnimation())) {
				this.stopAnimation();
			}

		}*/
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
	}
	
	

	public Rectangle getNetHitbox() {
		return this.net.getNetHitbox();
	}
	
	public Rectangle getNetHitboxGlobal() {
		Rectangle globalHitbox = new Rectangle();
		globalHitbox.setBounds((int) (this.getxPosGlobal() + 
				(-this.getOriginalHitbox().getX()  + this.getNetHitbox().x) * 
				this.getScaleXGlobal()),
				(int) (this.getyPosGlobal() + 
						(-this.getOriginalHitbox().getY() + this.getNetHitbox().y)* 
						this.getScaleYGlobal()),
				(int) (this.getNetHitbox().getWidth() * this.getScaleXGlobal()),
				(int) (this.getNetHitbox().getHeight() * this.getScaleYGlobal()));
		if(this.getNetHitbox().getWidth() != 0 && this.getNetHitbox().getHeight() != 0){
			globalHitbox.grow(5, 5);
		}
		
		return globalHitbox;
	}

	public void setNetHitbox(Rectangle netHitbox) {
		this.net.setNetHitbox(netHitbox);
	}
	
	public void drawNetHitboxGlobal(Graphics g) {
		int x = this.getNetHitboxGlobal().x;
		int y = this.getNetHitboxGlobal().y;
		int width = this.getNetHitboxGlobal().width;
		int height = this.getNetHitboxGlobal().height;
		g.fillRect(x, y, width, height);
	}

	public void moveSmokebomb(String dir) {
		switch (dir) {
		case "up":
			this.smokebomb.setCenterPos(this.getWidth()*.5, -this.getHeight()*.1);
			break;
		case "down":
			this.smokebomb.setCenterPos(this.getWidth()*.5, this.getHeight()*.75);
			break;
		case "left":
			this.smokebomb.setCenterPos(0, this.getHeight()*.5);
			break;
		case "right":
			this.smokebomb.setCenterPos(this.getWidth(), this.getHeight()*.5);
			break;
		case "upright":
			this.smokebomb.setCenterPos(this.getWidth(), 0);
			break;
		case "upleft":
			this.smokebomb.setCenterPos(0, 0);
			break;
		case "downright":
			this.smokebomb.setCenterPos(this.getWidth(), this.getHeight()*.75);
			break;
		case "downleft":
			this.smokebomb.setCenterPos(0, this.getHeight()*.75);
			break;
		}
		this.smokebomb.setVisible(true);
	}
	
	public void setSmokebombVisible(boolean visible) {
		this.smokebomb.setVisible(visible);
	}

	public String getSmokebombDir() {
		return smokebombDir;
	}

	public void setSmokebombDir(String smokeBombDir) {
		this.smokebombDir = smokeBombDir;
	}

}

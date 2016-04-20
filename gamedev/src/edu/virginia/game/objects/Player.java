package edu.virginia.game.objects;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import edu.virginia.engine.display.AnimatedSprite;
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
	private Sprite net;
	private PlayerManager playerManager = PlayerManager.getInstance();
	private LevelManager levelManager = LevelManager.getInstance();
	private GameManager gameManager = GameManager.getInstance();
	private SoundManager soundManager;
	private int numPlayer;
	private boolean active; // whether movement works
	private int vpCount;
	private AnimatedSprite poisonBubbles;

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

		poisonBubbles = new AnimatedSprite("bubbles", "bubbles/bubble-default.png", 
				"bubbles/bubble-spritesheet.png", "resources/bubbles/bubble-spritesheet.txt");
		this.addChild(poisonBubbles);
		this.poisonBubbles.setCenterPos(this.getWidth()*.75, -this.getHeight()*0.05);
		net = new AnimatedSprite("net", imageFileName, thisSheetFileName, "resources/player/player-spritesheet-1-netFrameInfo.txt");
		this.addChild(net);
		net.setAlpha(0);
		this.setPivotPoint(new Position(this.getWidth() / 2, this.getHeight() / 2));
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

	public Sprite getNet() {
		return net;
	}

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

	public void moveSpriteCartesianAnimate(ArrayList<String> pressedKeys) {
		double speed = this.playerManager.getSpeed(this.numPlayer);
		/*
		 * Make sure this is not null. Sometimes Swing can auto cause an extra
		 * frame to go before everything is initialized
		 */
		if (this != null && net != null) {
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
				this.moveNet("up");

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
				moveNet("down");
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
				moveNet("left");
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
				moveNet("right");
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
				this.stopAnimation();
			}
			// Update sprite to next frame if enough time has passed
			if (this.getGameClockAnimation().getElapsedTime() > (AVE_DRAW / (this.getAnimationSpeed() * .1))) {
				if (getSpriteMap().containsKey(getCurrentAnimation())
						&& getSpriteMap().get(getCurrentAnimation()).size() >= (this.getCurrentFrame() + 1)) {

					this.setImage(getSpriteMap().get(getCurrentAnimation()).get(this.getCurrentFrame()).getImage());
					this.setOriginalHitbox(getSpriteMap().get(getCurrentAnimation()).get(this.getCurrentFrame()).getHitbox());

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

}

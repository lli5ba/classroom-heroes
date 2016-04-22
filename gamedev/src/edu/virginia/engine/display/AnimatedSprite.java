package edu.virginia.engine.display;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import edu.virginia.engine.util.GameClock;
import edu.virginia.engine.util.Position;

/**
 * Average draw time is 17.5 (from gameClock)
 */
public class AnimatedSprite extends Sprite {

	public static final double AVE_DRAW = 17.5;
	private Map<String, ArrayList<FrameInfo>> spriteMap;
	private int currentFrame;
	private String currentAnimation;
	private boolean isPlaying;
	private double animationSpeed;
	private GameClock gameClockAnimation;
	private boolean loop;
	private int timesLooped;
	private String prevAnimation;
	private String direction;

	public AnimatedSprite(String id) {
		super(id);
		currentFrame = 0;
		spriteMap = new HashMap<String, ArrayList<FrameInfo>>();
		isPlaying = false;
		currentAnimation = null;
		setAnimationSpeed(1);
		setGameClockAnimation(new GameClock());
		loop = true;
		setTimesLooped(0);
		prevAnimation = null;
		direction = "down";

	}

	public AnimatedSprite(String id, String imageFileName) {
		super(id, imageFileName);
		currentFrame = 0;
		spriteMap = new HashMap<String, ArrayList<FrameInfo>>();
		isPlaying = false;
		currentAnimation = null;
		setAnimationSpeed(1);
		setGameClockAnimation(new GameClock());
		loop = true;
		setTimesLooped(0);
		prevAnimation = null;
		direction = "down";
	}

	public AnimatedSprite(String id, String imageFileName, String spriteSheetFileName, String specsFileName) {
		super(id, imageFileName);
		currentFrame = 0;
		spriteMap = new HashMap<String, ArrayList<FrameInfo>>();
		loadSprites(specsFileName, spriteSheetFileName);
		isPlaying = false;
		currentAnimation = "";
		setAnimationSpeed(1);
		setGameClockAnimation(new GameClock());
		loop = true;
		setTimesLooped(0);
		prevAnimation = "";
		direction = "down";
		if (spriteMap.containsKey("default")) {
			this.setDefault();
		}
	}
	
	public void setDefault(){
		if (this.spriteMap.containsKey("default")) {
			this.setOriginalHitbox(spriteMap.get("default").get(0).getHitbox());
		}
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void animate(String animationName) {
		
		if (spriteMap.containsKey(animationName)) {
			
			if (loop != false) { // Like a lock... animateOnce sequence takes
									// priority
				//if different animation, then reset currentFrame
				if (!currentAnimation.equals(animationName)) {
					
					currentFrame = 0;
					this.getGameClockAnimation().resetGameClock();
				}
				currentAnimation = animationName;
				isPlaying = true;
				loop = true;
				setTimesLooped(0);
				
			}
		}
	}
	
	public void animate(String animationName, double speed) {
		if (spriteMap.containsKey(animationName)) {
			if (loop != false) { // Like a lock... animateOnce sequence takes
									// priority
				//if different animation, then reset currentFrame
				if (!currentAnimation.equals(animationName)) {
					currentFrame = 0;
					this.getGameClockAnimation().resetGameClock();
				}
				currentAnimation = animationName;
				isPlaying = true;
				setAnimationSpeed(speed);
				loop = true;
				setTimesLooped(0);
			}
		}
	}

	public void stopAnimation() {
		/*
		 * set frame to display the previous default frame if animateOnce was
		 * last called Otherwise, set it to current default frame
		 * Also, if animateOnce was last called, don't reset the previous animation
		 */
		if (loop == false) {
			if (prevAnimation != null) {
				//if the animation sequence is still the same, don't reset animation to frame 0
				if (!currentAnimation.equals(prevAnimation)) {
					this.setDefaultImage(prevAnimation);
					loop = true;
				}
			}
		} else {
			//if the animation sequence is still the same, don't reset animation to frame 0
			if (!currentAnimation.equals(prevAnimation)) {
				this.setDefaultImage(currentAnimation);
			}
			prevAnimation = currentAnimation; 
		}
		isPlaying = false;
		//currentFrame = 0;
		setTimesLooped(0);
		setAnimationSpeed(1);		
	}


	public void animateOnce(String animationName) {
		if (spriteMap.containsKey(animationName)) {
			currentAnimation = animationName;
			isPlaying = true;
			currentFrame = 0;
			loop = false;
			setTimesLooped(0);
			this.getGameClockAnimation().resetGameClock();
		}
	}

	public void animateOnce(String animationName, double speed) {
		if (spriteMap.containsKey(animationName)) {
			currentAnimation = animationName;
			isPlaying = true;
			currentFrame = 0;
			setAnimationSpeed(speed);
			loop = false;
			setTimesLooped(0);
			this.getGameClockAnimation().resetGameClock();
		}
	}
	
	public void animateOnceLock(String animationName, int speed) {
		if (spriteMap.containsKey(animationName)) {
			if(loop == true) {
				currentAnimation = animationName;
				isPlaying = true;
				currentFrame = 0;
				setAnimationSpeed(speed);
				loop = false;
				setTimesLooped(0);
				this.getGameClockAnimation().resetGameClock();
			}
		}
	}

	public boolean isLooping() {
		return loop;
	}

	public String getCurrentAnimation() {
		return currentAnimation;
	}
	
	public void setCurrentAnimation(String currAnim) {
		this.currentAnimation = currAnim;
	}

	public GameClock getGameClockAnimation() {
		return gameClockAnimation;
	}

	public void setGameClockAnimation(GameClock gameClockAnimation) {
		this.gameClockAnimation = gameClockAnimation;
	}

	public double getAnimationSpeed() {
		return animationSpeed;
	}

	public int getTimesLooped() {
		return timesLooped;
	}

	public void setTimesLooped(int timesLooped) {
		this.timesLooped = timesLooped;
	}

	public Set<String> getAnimations() {
		return this.spriteMap.keySet();
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setAnimationSpeed(double speed) {
		this.animationSpeed = speed;
	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		
		super.update(pressedKeys);
		updateAnimation();
	}
	
	public void updateAnimation() {
		if (this.isPlaying) {
			// Stop if done looping
			if (!this.isLooping() && this.getTimesLooped() == 1) {
				this.stopAnimation();
			}
			// Update sprite to next frame if enough time has passed
			if (this.getGameClockAnimation().getElapsedTime() > (AVE_DRAW / (this.getAnimationSpeed() * .1))) {
				if (spriteMap.containsKey(currentAnimation)
						&& spriteMap.get(currentAnimation).size() >= (this.currentFrame + 1)) {

					this.setImage(spriteMap.get(currentAnimation).get(this.currentFrame).getImage());
					this.setOriginalHitbox(spriteMap.get(currentAnimation).get(this.currentFrame).getHitbox());

				}
				this.increaseFrame();
				this.getGameClockAnimation().resetGameClock();
			}
		}
	}

	public void setDefaultImage(String animationName) {
		if (spriteMap.containsKey(animationName)) {
			BufferedImage current = spriteMap.get(animationName).get(0).getImage();
			this.setImage(current);
		}
	}

	public Map<String, ArrayList<FrameInfo>> getSpriteMap() {
		return spriteMap;
	}

	public void increaseFrame() {
		if (spriteMap.containsKey(this.currentAnimation)) {
			if (this.currentFrame + 1 == getTotalFrames(this.currentAnimation)) { // looping
																					// back
																					// to
																					// first
																					// frame
				this.setTimesLooped(this.getTimesLooped() + 1);
			}
			this.currentFrame = (this.currentFrame + 1) % getTotalFrames(this.currentAnimation);
		}
	}

	public int getTotalFrames(String animationName) {
		if (spriteMap.containsKey(animationName)) {
			return spriteMap.get(animationName).size();
		}
		return 0;
	}

	public void loadSprites(String txt_filename, String image_filename) {
		BufferedImage spriteSheet = null;
		spriteSheet = this.readImage(image_filename);
		if (spriteSheet == null) {
			System.err.println("[DisplayObject.setImage] ERROR: " + image_filename + " does not exist!");
		}
		/*
		 * try { spriteSheet = ImageIO.read(new File(image_filename)); } catch
		 * (Exception e) { System.err.println("Cannot load sprite sheet " +
		 * image_filename + "!"); }
		 */

		try {
			FileInputStream fstream = new FileInputStream(txt_filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {

				String[] tokens = strLine.split(" ");
				String name = tokens[0];
				int num_in_seq = Integer.parseInt(tokens[1]);
				int xPos = Integer.parseInt(tokens[3]);
				int yPos = Integer.parseInt(tokens[4]);
				int xWidth = Integer.parseInt(tokens[5]);
				int yHeight = Integer.parseInt(tokens[6]);
				int xPosHitbox = 0;
				int yPosHitbox = 0;
				int xWidthHitbox = xWidth;
				int yHeightHitbox = yHeight;
				if (tokens.length == 11) {
					xPosHitbox = Integer.parseInt(tokens[7]);
					yPosHitbox = Integer.parseInt(tokens[8]);
					xWidthHitbox = Integer.parseInt(tokens[9]);
					yHeightHitbox = Integer.parseInt(tokens[10]);
				}
				// System.out.println("Adding image at " + xPos + "," + yPos +
				// "," + xWidth + "," + yHeight);
				if (spriteMap.containsKey(name)) {
					ArrayList<FrameInfo> spriteArray = spriteMap.get(name);
					spriteArray.add(new FrameInfo(spriteSheet.getSubimage(xPos, yPos, xWidth, yHeight),
							new Rectangle(xPosHitbox, yPosHitbox, xWidthHitbox, yHeightHitbox)));
					spriteMap.put(name, spriteArray);
				} else {
					ArrayList<FrameInfo> spriteArray = new ArrayList<FrameInfo>();
					spriteArray.add(new FrameInfo(spriteSheet.getSubimage(xPos, yPos, xWidth, yHeight),
							new Rectangle(xPosHitbox, yPosHitbox, xWidthHitbox, yHeightHitbox)));
					spriteMap.put(name, spriteArray);

				}

			}
			in.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

	}

}

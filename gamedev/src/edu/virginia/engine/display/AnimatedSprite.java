package edu.virginia.engine.display;

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
	private Map<String, ArrayList<BufferedImage>> spriteMap;
	private int currentFrame;
	private String currentAnimation;
	private boolean isPlaying;
	private int animationSpeed;
	private GameClock gameClockAnimation;
	private boolean loop;
	private int timesLooped;
	
	public AnimatedSprite(String id) {
		super(id);
		currentFrame = 0;
		spriteMap = new HashMap<String, ArrayList<BufferedImage>>();
		isPlaying = false;
		currentAnimation = null;
		animationSpeed = 1;
		gameClockAnimation = new GameClock();
		loop = true;
		timesLooped = 0;
		
	}

	public AnimatedSprite(String id, String imageFileName) {
		super(id, imageFileName);
		currentFrame = 0;
		spriteMap = new HashMap<String, ArrayList<BufferedImage>>();
		isPlaying = false;
		currentAnimation = null;
		animationSpeed = 1;
		gameClockAnimation = new GameClock();
		loop = true;
		timesLooped = 0;
	}
	
	public AnimatedSprite(String id, String imageFileName, String spriteSheetFileName, String specsFileName) {
		super(id, imageFileName);
		currentFrame = 0;
		spriteMap = new HashMap<String, ArrayList<BufferedImage>>();
		loadSprites(specsFileName, spriteSheetFileName);
		isPlaying = false;
		currentAnimation = null;
		animationSpeed = 1;
		gameClockAnimation = new GameClock();
		loop = true;
		timesLooped = 0;
	}
	
	public void animate(String animationName) {
		System.out.println("called animate");
		if(spriteMap.containsKey(animationName)) {
			currentAnimation = animationName;
			isPlaying = true;
			currentFrame = 0;
			loop = true;
			timesLooped = 0;
		}
	}
	
	public void stopAnimation(){
		isPlaying = false;
		currentFrame = 0;
		loop = true;
		timesLooped = 0;
		animationSpeed = 1;
	}
	
	public void animate(String animationName, int speed) {
		if(spriteMap.containsKey(animationName)) {
			currentAnimation = animationName;
			isPlaying = true;
			currentFrame = 0;
			animationSpeed = speed;
			loop = true;
			timesLooped = 0;
		} 
	}
	
	public void animateOnce(String animationName) {
		if(spriteMap.containsKey(animationName)) {
			currentAnimation = animationName;
			isPlaying = true;
			currentFrame = 0;
			loop = false;
			timesLooped = 0;
		}
	}
	
	public void animateOnce(String animationName, int speed) {
		if(spriteMap.containsKey(animationName)) {
			currentAnimation = animationName;
			isPlaying = true;
			currentFrame = 0;
			animationSpeed = speed;
			loop = false;
			timesLooped = 0;
		}
	}
	
	public boolean isLooping() {
		return loop;
	}
	
	public String getCurrentAnimation() {
		return currentAnimation;
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

	public void setAnimationSpeed(int animationSpeed) {
		this.animationSpeed = animationSpeed;
	}

	
	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		if (this.isPlaying) {
			//Stop if done looping
			System.out.println("looped " + this.timesLooped);
			System.out.println("curr frame " + this.currentFrame);
			if(!this.isLooping() && this.timesLooped == 1) {
				this.stopAnimation();
			}
			//Update sprite to next frame if enough time has passed
			if (this.gameClockAnimation.getElapsedTime() > (AVE_DRAW/(this.animationSpeed*.1))){
				if (spriteMap.containsKey(currentAnimation) 
						&& spriteMap.get(currentAnimation).size() >= (this.currentFrame + 1)) {
					
					BufferedImage current = spriteMap.get(currentAnimation).get(this.currentFrame);
					this.setImage(current);
				}
				this.increaseFrame();
				this.gameClockAnimation.resetGameClock();
			}
		} else {
			if (spriteMap.containsKey(currentAnimation) 
					&& spriteMap.get(currentAnimation).size() >= (this.currentFrame + 1)) {
				
				BufferedImage current = spriteMap.get(currentAnimation).get(this.currentFrame);
				this.setImage(current);
			}
		}
	}

	public Map<String, ArrayList<BufferedImage>> getSpriteMap() {
		return spriteMap;
	}

	public void increaseFrame() {
		if(spriteMap.containsKey(this.currentAnimation)) {
			if (this.currentFrame + 1 == getTotalFrames(this.currentAnimation)) { //looping back to first frame
				this.timesLooped += 1;
			}
			this.currentFrame = (this.currentFrame + 1) % getTotalFrames(this.currentAnimation);
		}
	}
	public int getTotalFrames(String animationName) {
		if(spriteMap.containsKey(animationName)) {
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
		/*try {
			spriteSheet = ImageIO.read(new File(image_filename));
		} catch (Exception e) {
			System.err.println("Cannot load sprite sheet " + image_filename + "!");
		}*/

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
				//System.out.println("Adding image at " + xPos + "," + yPos + "," + xWidth + "," + yHeight);
				if (spriteMap.containsKey(name)) {
					ArrayList<BufferedImage> spriteArray = spriteMap.get(name);
					spriteArray.add(spriteSheet.getSubimage(xPos, yPos, xWidth, yHeight));
					spriteMap.put(name, spriteArray);
				} else {
					ArrayList<BufferedImage> spriteArray = new ArrayList<BufferedImage>();
					spriteArray.add(spriteSheet.getSubimage(xPos, yPos, xWidth, yHeight));
					spriteMap.put(name, spriteArray);

				}

			}
			in.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

	}

	

}

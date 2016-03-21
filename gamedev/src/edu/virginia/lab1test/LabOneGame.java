package edu.virginia.lab1test;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.plaf.synth.SynthSeparatorUI;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.PhysicsSprite;
import edu.virginia.engine.display.PickedUpItem;
import edu.virginia.engine.display.SoundManager;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.CollisionEvent;
import edu.virginia.engine.tween.Tween;
import edu.virginia.engine.tween.TweenEvent;
import edu.virginia.engine.tween.TweenJuggler;
import edu.virginia.engine.tween.TweenTransitions;
import edu.virginia.engine.tween.TweenableParam;
import edu.virginia.engine.util.Position;

/**
 * Modified by: Leandra Irvine (lli5ba)
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */
public class LabOneGame extends Game {
	
	public static int gameHeight = 300;
	public static int gameWidth = 500;
	public static final String[] CARDINAL_DIRS = new String[] {"up", "down","left", "right"};
	/* Create a sprite object for our game. We'll use mario */
	//Sprite mario = new Sprite("Mario", "Mario.png");
	PickedUpItem key = new PickedUpItem("Key", "Small_Key.png");
	//AnimatedSprite lily = new AnimatedSprite("Lily", "Lily.png", "LilySheet.png", "LilySpecs.txt");
	AnimatedSprite lily2 = new AnimatedSprite("Lily2", "Lily.png", "LilySheet.png", "LilySpecs.txt");
	QuestManager myQuestManager = new QuestManager();
	Sprite floor = new Sprite("Floor", "floor.png");
	Sprite platform = new Sprite("Platform", "floor.png");
	SoundManager mySoundManager;
	TweenJuggler myTweenJuggler = TweenJuggler.getInstance();
	Sprite net = new Sprite("Net", "Lily.png");
	
	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * @throws UnsupportedAudioFileException 
	 * @throws IOException 
	 * */
	public LabOneGame() throws LineUnavailableException, IOException, UnsupportedAudioFileException{
		super("Lab One Test Game", gameWidth, gameHeight);
		net.setAlpha(0);
		
		
		net.setScaleX(1.5);
		net.setScaleY(1.5);
		lily2.addChild(net);
		
		
		
		mySoundManager = new SoundManager();
		mySoundManager.LoadMusic("thebestsong", "whatisthis.wav");
		//mySoundManager.PlayMusic("thebestsong");
		//lily.setPosition(100, 100);
		lily2.setPosition(0, 0);
		//lily.animate("down");
		key.setPosition(275, 50);
		
		floor.setPosition(0, 300 - floor.getUnscaledHeight() - 10);
		floor.setScaleX(20);
		platform.setPosition(200, 150);
		key.addEventListener(myQuestManager, PickedUpEvent.KEY_PICKED_UP);
		key.addEventListener(myQuestManager, CollisionEvent.COLLISION);
		key.addEventListener(myQuestManager, TweenEvent.TWEEN_COMPLETE_EVENT);
		lily2.setPivotPoint(new Position(lily2.getUnscaledWidth()/2,lily2.getUnscaledHeight()/2));
		Tween tween0 = new Tween(lily2, TweenTransitions.EASE_IN_OUT);
		myTweenJuggler.add(tween0);
		tween0.animate(TweenableParam.SCALE_X, 0, 1.5, 1000);
		tween0.animate(TweenableParam.SCALE_Y, 0, 1.5, 1000);
		tween0.animate(TweenableParam.ALPHA, 0, 1, 1000);
		tween0.animate(TweenableParam.POS_X, 0, 100, 1000);
		tween0.animate(TweenableParam.POS_Y, 0, 100, 1000);
		
		
	}
	
	public void moveNet(Sprite character, Sprite net, String position) {
		if(position.equals("up")) {
			//net.setRotationDegrees(-90);
			net.setyPos(-(character.getWidth()));
			net.setxPos(-(character.getWidth()/4.0));
			net.setWidth(character.getHeight());
			net.setHeight(character.getWidth());
		}
		else if(position.equals("down")) {
			//net.setRotationDegrees(90);
			net.setyPos(character.getHeight());
			net.setxPos(-(character.getWidth()/4.0));
			net.setWidth(character.getHeight());
			net.setHeight(character.getWidth());
		}
		else if(position.equals("right")) {
			//net.setRotationDegrees(0);
			net.setxPos((character.getUnscaledWidth()*character.getScaleX()));
			net.setyPos(0);
			net.setWidth(character.getWidth());
			net.setHeight(character.getHeight());
		}
		else if(position.equals("left")) {
			//net.setRotationDegrees(180);
			net.setxPos(-(character.getUnscaledWidth()*character.getScaleX()));
			net.setyPos(0);
			net.setWidth(character.getWidth());
			net.setHeight(character.getHeight());
		}
	}
	
	
	public void rotateSprite(Sprite sprite, ArrayList<String> pressedKeys, String button) {
		if(sprite != null) {
			sprite.update(pressedKeys);
			/* update mario's position if a key is pressed, check bounds of canvas */
			if(pressedKeys.contains(button)) {
				sprite.setRotationRadians(sprite.getRotation() + .04);
				}
		}
	}
	
	public void moveSpriteCartesianAnimate(Sprite net, AnimatedSprite sprite, ArrayList<String> pressedKeys){
		/* Make sure sprite is not null. Sometimes Swing can auto cause an extra frame to go before everything is initialized */
		if(sprite != null && net != null) {
			sprite.update(pressedKeys);
			/* update mario's position if a key is pressed, check bounds of canvas */
			if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_UP))) {
				if(sprite.getyPos() > 0) {
					sprite.setyPos(sprite.getyPos() - 1);
				}
				if(!sprite.isPlaying() || sprite.getCurrentAnimation() != "up") {
					sprite.animate("up");
				}
				moveNet(sprite, net, "up");
				
			}
			if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_DOWN))) {
				if(sprite.getyPos() < gameHeight - sprite.getUnscaledHeight()*sprite.getScaleY()){
					sprite.setyPos(sprite.getyPos() + 1);
				}
				if(!sprite.isPlaying() || sprite.getCurrentAnimation() != "down") {
					sprite.animate("down");
				}
				moveNet(sprite, net, "down");
			}
			if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_LEFT))) {
				if(sprite.getxPos() > 0) {
					sprite.setxPos(sprite.getxPos() - 1);
				}
				if(!sprite.isPlaying() || sprite.getCurrentAnimation() != "left") {
					sprite.animate("left");
				}
				moveNet(sprite, net, "left");
			}
			if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_RIGHT))) {
				
				if(sprite.getxPos() < gameWidth - sprite.getUnscaledWidth()*sprite.getScaleX()){
					sprite.setxPos(sprite.getxPos() + 1);
				}
				if(!sprite.isPlaying() || sprite.getCurrentAnimation() != "right") {
					sprite.animate("right");
				}
				moveNet(sprite, net, "right");
			}
			if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_SPACE))) {
				String currentDir = sprite.getCurrentAnimation();
				if (currentDir == null) {
					currentDir = "down";
				}
				sprite.animateOnce("right");
				if (net.collidesWithGlobal(key) && !key.isPickedUp()) {
					key.dispatchEvent(new PickedUpEvent(PickedUpEvent.KEY_PICKED_UP, key));
					key.setPickedUp(true);
				}
			}
		}
	}
	
	/**
	 * Engine will automatically call this update method once per frame and pass to us
	 * the set of keys (as strings) that are currently being pressed down
	 * */
	@Override
	public void update(ArrayList<String> pressedKeys){
		super.update(pressedKeys);
		/*moveSpriteCartesian(mario, pressedKeys);
		moveSpriteCartesian(mario2,pressedKeys);
		if(this.lily2 != null) {
			this.lily2.update(pressedKeys);
		}*/
		if (this.lily2 != null && this.net != null) {
			moveSpriteCartesianAnimate(net, lily2, pressedKeys);
			//if there are no keys being pressed, and Lily is walking, then stop the animation
			if (pressedKeys.isEmpty() && Arrays.asList(CARDINAL_DIRS).contains(lily2.getCurrentAnimation())){
				lily2.stopAnimation();
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
		if (this.floor != null) {
			floor.update(pressedKeys);
		}
		if (this.platform != null) {
			platform.update(pressedKeys);
		}
		if (this.net != null) {
			net.update(pressedKeys);
		}
		
		if (myTweenJuggler != null) {
			myTweenJuggler.nextFrame();
		}
		
	}
	
	/**
	 * Engine automatically invokes draw() every frame as well. If we want to make sure mario gets drawn to
	 * the screen, we need to make sure to override this method and call mario's draw method.
	 * */
	@Override
	public void draw(Graphics g){
		super.draw(g);
		
		if(lily2 != null) {
			lily2.draw(g);
			net.drawHitboxGlobal(g);
		}
		/*
		if(floor != null) {
			floor.draw(g);
		}
		if(platform != null) {
			platform.draw(g);
		} */
		if(key != null) {
			key.draw(g);	
		}
	}

	/**
	 * Quick main class that simply creates an instance of our game and starts the timer
	 * that calls update() and draw() every frame
	 * @throws UnsupportedAudioFileException 
	 * @throws IOException 
	 * @throws LineUnavailableException 
	 * */
	public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		LabOneGame game = new LabOneGame();
		game.start();
		
	}
}

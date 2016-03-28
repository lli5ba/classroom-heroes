package edu.virginia.lab1test;

import java.awt.Font;
import java.awt.FontMetrics;
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
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.plaf.synth.SynthSeparatorUI;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.display.DisplayObjectContainer;
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
import edu.virginia.engine.util.GameClock;
import edu.virginia.engine.util.Position;

/**
 * Modified by: Leandra Irvine (lli5ba)
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */
public class LabOneGame extends Game {
	
	public static int gameHeight = 500;
	public static int gameWidth = 800;
	public static final String[] CARDINAL_DIRS = new String[] {"up", "down","left", "right"};
	/* Create a sprite object for our game. We'll use mario */
	//Sprite mario = new Sprite("Mario", "Mario.png");
	PickedUpItem key = new PickedUpItem("Key", "Small_Key.png");
	//AnimatedSprite lily = new AnimatedSprite("Lily", "Lily.png", "LilySheet.png", "LilySpecs.txt");
	AnimatedSprite lily2 = new AnimatedSprite("Lily2", "Lily.png", "LilySheet.png", "LilySpecs.txt");
	AnimatedSprite player1 = new AnimatedSprite("Player1", "player1.png", "player1sheet.png", "player1sheetspecs.txt");
	QuestManager myQuestManager = new QuestManager();
	Sprite floor = new Sprite("Floor", "floor.png");
	Sprite platform = new Sprite("Platform", "floor.png");
	Sprite vpNum = new Sprite("vp", "vpbox.png"); //Box for VP; size of 512X512
	SoundManager mySoundManager;
	TweenJuggler myTweenJuggler = TweenJuggler.getInstance();
	Sprite net = new Sprite("Net", "Lily.png");
	private GameClock gameClock;

	public static final double SPAWN_INTERVAL = 1500;
	public static int p1speed = 8;
	public static int vpcounter = 0;
	
	//Change this boss sprite later!!
	Sprite boss = new Sprite("boss", "Mario.png");
	
	
	ArrayList<PickedUpItem> vpList = new ArrayList<PickedUpItem>();
	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * @throws UnsupportedAudioFileException 
	 * @throws IOException 
	 * */
	public LabOneGame() throws LineUnavailableException, IOException, UnsupportedAudioFileException{
		super("Lab One Test Game", gameWidth, gameHeight);
		net.setAlpha(0);
		gameClock = new GameClock();
		
		net.setScaleX(1.5);
		net.setScaleY(1.5);
		player1.addChild(net);
		
		mySoundManager = new SoundManager();
		mySoundManager.LoadMusic("thebestsong", "whatisthis.wav");
		//mySoundManager.PlayMusic("thebestsong");
		//lily.setPosition(100, 100);
		player1.setPosition(100, 100);
		boss.setPosition(400 - (boss.getWidth()/2),0);
		//lily.animate("down");
		key.setPosition(300, 50);
		
		vpNum.setPosition(690,430);
		vpNum.setScaleX(0.2);
		vpNum.setScaleY(0.1);
		
		floor.setPosition(0, 300 - floor.getUnscaledHeight() - 10);
		floor.setScaleX(20);
		platform.setPosition(200, 150);
		key.addEventListener(myQuestManager, PickedUpEvent.KEY_PICKED_UP);
		key.addEventListener(myQuestManager, CollisionEvent.COLLISION);
		key.addEventListener(myQuestManager, TweenEvent.TWEEN_COMPLETE_EVENT);
		player1.setPivotPoint(new Position(player1.getUnscaledWidth()/2,player1.getUnscaledHeight()/2));
		Tween tween0 = new Tween(player1, TweenTransitions.EASE_IN_OUT);
		Tween tween1 = new Tween(key, TweenTransitions.LINEAR);
		myTweenJuggler.add(tween0);
		tween0.animate(TweenableParam.SCALE_X, 0, 1.5, 1000);
		tween0.animate(TweenableParam.SCALE_Y, 0, 1.5, 1000);
		tween0.animate(TweenableParam.ALPHA, 0, 1, 1000);
		tween0.animate(TweenableParam.POS_X, 0, 100, 1000);
		tween0.animate(TweenableParam.POS_Y, 0, 100, 1000);
		
		myTweenJuggler.add(tween1);
		tween1.animate(TweenableParam.POS_X, 0, 200, 6000);
		tween1.animate(TweenableParam.POS_Y, 0, 200, 6000);
		
	
	}
	
	public Position generatePosition(double centerx, double centery, double radius) {
		//Creates semi-circle range, 0 to PI
		double ang_min = (0);
		double ang_max = (Math.PI);
		Random rand1 = new Random();
		double d = ang_min + rand1.nextDouble() * (ang_max - ang_min);
		//System.out.println("d: " + d);
		
		double x = centerx + radius*Math.cos(d);
		double y = centery + radius*Math.sin(d);
		//System.out.println("X:" + x);
		return new Position(x, y);
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
					sprite.setyPos(sprite.getyPos() - p1speed);
				}
				if(!sprite.isPlaying() || sprite.getCurrentAnimation() != "up") {
					sprite.animate("up");
				}
				sprite.setDirection("up");
				moveNet(sprite, net, "up");
				
			}
			if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_DOWN))) {
				if(sprite.getyPos() < gameHeight - sprite.getUnscaledHeight()*sprite.getScaleY()){
					sprite.setyPos(sprite.getyPos() + p1speed);
				}
				if(!sprite.isPlaying() || sprite.getCurrentAnimation() != "down") {
					sprite.animate("down");
				}
				sprite.setDirection("down");
				moveNet(sprite, net, "down");
			}
			if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_LEFT))) {
				if(sprite.getxPos() > 0) {
					sprite.setxPos(sprite.getxPos() - p1speed);
				}
				if(!sprite.isPlaying() || sprite.getCurrentAnimation() != "left") {
					sprite.animate("left");
				}
				sprite.setDirection("left");
				moveNet(sprite, net, "left");
			}
			if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_RIGHT))) {
				
				if(sprite.getxPos() < gameWidth - sprite.getUnscaledWidth()*sprite.getScaleX()){
					sprite.setxPos(sprite.getxPos() + p1speed);
				}
				if(!sprite.isPlaying() || sprite.getCurrentAnimation() != "right") {
					sprite.animate("right");
				}
				sprite.setDirection("right");
				moveNet(sprite, net, "right");
			}
			if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_SPACE))) {
				String currentDir = sprite.getDirection();
				//Until we have combined net and walking animation, net animation overrides walking animation
				
				if (sprite.isPlaying() && !sprite.getCurrentAnimation().contains("net")) {
					System.out.println("STOPPING\n");
					sprite.stopAnimation();
				}

				sprite.animateOnce("net" + currentDir, 10);
				for(PickedUpItem vp : vpList) {
					if (net.collidesWithGlobal(vp) && !vp.isPickedUp()) {
						vp.dispatchEvent(new PickedUpEvent(PickedUpEvent.KEY_PICKED_UP, vp));
						vp.setPickedUp(true);
						vpcounter++;
					}
					System.out.println("Current vp is: " + vpcounter);
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
		
		if(this.gameClock != null) {
			
		if(this.gameClock.getElapsedTime() > (SPAWN_INTERVAL)) {
			spawnVP();
			this.gameClock.resetGameClock();
		}
		}
		
		if (vpList != null) {
			for(PickedUpItem vp : vpList) {
				if(vp != null) {
					vp.update(pressedKeys);
					//vp.drawHitboxGlobal(g);
				}
			}
		}
		
		if (this.player1 != null && this.net != null) {
			moveSpriteCartesianAnimate(net, player1, pressedKeys);
			//if there are no keys being pressed, and Lily is walking, then stop the animation
			if (pressedKeys.isEmpty() && player1.isPlaying() 
					&& Arrays.asList(CARDINAL_DIRS).contains(player1.getCurrentAnimation())){
				player1.stopAnimation();
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
	
	public void spawnVP() {
		if(myTweenJuggler != null) {
			PickedUpItem vp = new PickedUpItem("VP", "vp0.png", "vpsheet.png", "vpsheetspecs.txt");
			Random rand1 = new Random();
			int colorVar = (int)(rand1.nextDouble() * 3);
			String color = null;
			switch (colorVar) {
				case 0: color = "red";
						break;
				case 1: color = "yellow";
						break;
				case 2: color = "blue";
						break;
				default: color = "red";
			}
			vp.animate(color);
			vp.setScaleX(1);
			vp.setScaleY(1);
			vp.addEventListener(myQuestManager, PickedUpEvent.KEY_PICKED_UP);
			vp.addEventListener(myQuestManager, CollisionEvent.COLLISION);
			Tween tween2 = new Tween(vp, TweenTransitions.LINEAR);
			myTweenJuggler.add(tween2);
			Position pos =  generatePosition(400, 20, 1000);
			tween2.animate(TweenableParam.POS_X, 400, pos.getX(), 10000);
			tween2.animate(TweenableParam.POS_Y, 20, pos.getY(), 10000);
			this.vpList.add(vp);
		}
	}
	
	/**
	 * Engine automatically invokes draw() every frame as well. If we want to make sure mario gets drawn to
	 * the screen, we need to make sure to override this method and call mario's draw method.
	 * */
	@Override
	public void draw(Graphics g){
		super.draw(g);
		
		if(boss != null) {
			boss.draw(g);
		}
		
		if(vpNum != null) {
			 g.drawString("Num of VP: " + vpcounter, (int) vpNum.getxPos()+15, (int) vpNum.getyPos()+30);
			vpNum.draw(g);
		}
		
//		if(key != null) {
//			key.draw(g);	
//		}
		if(player1 != null) {
			player1.draw(g);
			
		
		
		for(PickedUpItem vp : vpList) {
			if(vp != null) {
				vp.draw(g);
				//vp.drawHitboxGlobal(g);
			}
		}
			
		}
		if(net != null) {
			//net.drawHitboxGlobal(g);
		}
		/*
		if(floor != null) {
			floor.draw(g);
		}
		if(platform != null) {
			platform.draw(g);
		} */
		
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

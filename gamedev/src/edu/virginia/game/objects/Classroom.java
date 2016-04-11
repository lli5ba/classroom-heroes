package edu.virginia.game.objects;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.CollisionEvent;
import edu.virginia.engine.events.GameEvent;
import edu.virginia.engine.tween.Tween;
import edu.virginia.engine.tween.TweenJuggler;
import edu.virginia.engine.tween.TweenTransitions;
import edu.virginia.engine.tween.TweenableParam;
import edu.virginia.engine.util.GameClock;
import edu.virginia.engine.util.Position;
import edu.virginia.game.main.PickedUpEvent;
import edu.virginia.game.managers.GameManager;
import edu.virginia.game.managers.LevelManager;
import edu.virginia.game.managers.PlayerManager;
import edu.virginia.game.managers.ProjectileManager;
import edu.virginia.game.managers.SoundManager;
import edu.virginia.game.managers.StudentManager;

//This class represents a game screen object to be used for levels and hallway scenes.
//This way you can instantiate a GameScreen and add game elements as children.
public class Classroom extends DisplayObjectContainer {
	public static final String[] CARDINAL_DIRS = new String[] { "up", "down", "left", "right" };
	private PlayerManager playerManager = PlayerManager.getInstance();
	private LevelManager levelManager = LevelManager.getInstance();
	private static GameManager gameManager = GameManager.getInstance();
	private StudentManager studentManager = StudentManager.getInstance();
	private ProjectileManager projectileManager = ProjectileManager.getInstance();
	private SoundManager soundManager;
	private TweenJuggler myTweenJuggler = TweenJuggler.getInstance();
	private Player player1;
	private Player player2;
	private Boss boss;
	private PlayerStatBox stat;
	private EndLevelScreen endLevelScreen;
	private GameClock gameClock;
	private GameClock poisonClock;
	private GameClock vpClock;
	private boolean inPlay;
	public static final double VP_SPAWN_INTERVAL = 1500;
	public static final double POISON_SPAWN_INTERVAL = 1750;
	public static final double GAME_TIME = 60000;
	private static boolean hit = false;
	public ArrayList<PickedUpItem> vpList = new ArrayList<PickedUpItem>();
	ArrayList<PickedUpItem> poisonList = new ArrayList<PickedUpItem>();
	ArrayList<Student> studentList = new ArrayList<Student>();
	private DisplayObjectContainer playArea;

	public Classroom(String id) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		super(id, "classroom/classroom-background-" + gameManager.getNumLevel() + ".png");

		try {
			this.soundManager = SoundManager.getInstance();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

		/* GameClocks */
		this.gameClock = new GameClock();
		this.poisonClock = new GameClock();
		this.vpClock = new GameClock();

		/* Game Event Listener */
		this.addEventListener(soundManager, EventTypes.PICKUP_VP.toString());
		this.addEventListener(soundManager, EventTypes.PICKUP_POISON.toString());
		this.addEventListener(levelManager, EventTypes.WIN_LEVEL.toString());
		this.addEventListener(levelManager, EventTypes.LOSE_LEVEL.toString());
		this.addEventListener(soundManager, EventTypes.CURE_STUDENT.toString());
		this.addEventListener(soundManager,  EventTypes.POISON_STUDENT.toString());
		
		/* Constructing players and their event listeners */
		player1 = new Player("Player1", "player/player1.png", "player/player-spritesheet-1.png",
				"resources/player/player-spritesheet-1-frameInfo.txt", 1);
		player2 = new Player("Player2", "player/player1.png", "player/player-spritesheet-1.png",
				"resources/player/player-spritesheet-1-frameInfo.txt", 2);

		this.player1.addEventListener(playerManager, EventTypes.POISON_PLAYER.toString());
		this.player1.addEventListener(levelManager, EventTypes.PICKUP_VP.toString());
		this.player1.addEventListener(playerManager, EventTypes.CURE_STUDENT.toString());
		this.player1.addEventListener(levelManager, EventTypes.CURE_STUDENT.toString());

		this.player2.addEventListener(playerManager, EventTypes.POISON_PLAYER.toString());
		this.player2.addEventListener(levelManager, EventTypes.PICKUP_VP.toString());
		this.player2.addEventListener(playerManager, EventTypes.CURE_STUDENT.toString());
		this.player2.addEventListener(levelManager, EventTypes.CURE_STUDENT.toString());

		if (this.gameManager.getNumPlayers() == 1) {
			// set player2 inactive and invisible
			player2.setActive(false);
			player2.setVisible(false);
		}

		this.addChild(player1);
		this.addChild(player2);

		this.player1.setPosition(this.getWidth() * .08, this.getHeight() * .742);
		this.player2.setPosition(this.getWidth() * .814, this.getHeight() * .742);

		/* Boss constructor */
		boss = new Boss("Boss", "Mario.png");
		this.addChild(boss);
		this.boss.setPosition(this.getWidth() * .43, this.getHeight() * .1);
		this.boss.setScaleX(.6);
		this.boss.setScaleY(.6);

		/* Generate Students */
		spawnStudent("Student0", "down", this.getWidth() * .5, this.getHeight() * .742);
		spawnStudent("Student1", "left", this.getWidth() * .7, this.getHeight() * .65);
		spawnStudent("Student2", "right", this.getWidth() * .2, this.getHeight() * .65);

		
		/* set play area bounds */
		this.playArea = new DisplayObjectContainer("playArea", "Mario.png"); //random png file
		this.playArea.setVisible(false);
		this.playArea.setWidth(this.getWidth());
		this.playArea.setHeight(this.getHeight() * .8);
		this.addChild(playArea);
		this.playArea.setPosition(0, this.getHeight() * .2);
		
		/* Printing out stats */
		stat = new PlayerStatBox("stats");
		this.addChild(stat);
		
		/*End Level Screen */
		endLevelScreen = new EndLevelScreen("endLevel");
		this.endLevelScreen.setVisible(false);
		this.addChild(endLevelScreen);
		this.endLevelScreen.setPosition(this.getWidth()*0, this.getHeight()*.05);
		//FIXME: Only works for one player right now,
		// not built well at the moment because just needed to finish!
		
		/* the game is in session (not on end screen) */
		this.inPlay = true;
		
		/* music */
		soundManager.stopAll();
		if (!soundManager.isPlayingMusic()) {
			soundManager.LoadMusic("bg", "theme.wav");
			soundManager.PlayMusic("bg");
		}
		
		/* setting height and width of background 
		 * DO THIS LAST! */
		this.setHeight(gameManager.getGameHeight());
		this.setWidth(gameManager.getGameWidth());
	}

	/** Generates random position on semi-circle for spawning poison/VP **/
	public Position generatePosition(double centerx, double centery, double radius) {
		double ang_min = (0);
		double ang_max = (Math.PI);
		Random rand1 = new Random();
		double d = ang_min + rand1.nextDouble() * (ang_max - ang_min);
		// System.out.println("d: " + d);

		double x = centerx + radius * Math.cos(d);
		double y = centery + radius * Math.sin(d);
		// System.out.println("X:" + x);
		return new Position(x, y);
	}

	public void spawnStudent(String id, String animDir, double xPos, double yPos) {
		Student student1 = new Student(id, "0", animDir);
		student1.addEventListener(studentManager, EventTypes.POISON_STUDENT.toString());
		student1.addEventListener(studentManager, EventTypes.CURE_STUDENT.toString());
		this.addChild(student1);
		student1.setPosition(xPos, yPos);
		this.studentList.add(student1);
	}
	public void spawnVP() {
		if (myTweenJuggler != null) {
			VP vp = new VP("VP");
			vp.setCenterPos(this.boss.getCenterPos());
			vp.addEventListener(projectileManager, EventTypes.PICKUP_VP.toString());
			vp.addEventListener(playerManager, EventTypes.PICKUP_VP.toString());
			Tween tween2 = new Tween(vp, TweenTransitions.LINEAR);
			myTweenJuggler.add(tween2);
			Position pos = generatePosition(vp.getxPos(), vp.getyPos(), 1000);
			tween2.animate(TweenableParam.POS_X, vp.getxPos(), pos.getX(), 20000);
			tween2.animate(TweenableParam.POS_Y, vp.getyPos(), pos.getY(), 20000);
			this.vpList.add(vp);
			this.addChild(vp);
			this.hit = false;
		}
	}

	public void spawnPoison() {
		if (myTweenJuggler != null) {
			// FIXME: sprite sheet not implemented
			Poison poison = new Poison("Poison");
			poison.setCenterPos(this.boss.getCenterPos());
			poison.addEventListener(projectileManager, EventTypes.PICKUP_POISON.toString());
			poison.addEventListener(playerManager, EventTypes.PICKUP_POISON.toString());
			Tween tween2 = new Tween(poison, TweenTransitions.LINEAR);
			myTweenJuggler.add(tween2);
			Position pos = generatePosition(poison.getxPos(), poison.getyPos(), 1000);
			tween2.animate(TweenableParam.POS_X, poison.getxPos(), pos.getX(), 25000);
			tween2.animate(TweenableParam.POS_Y, poison.getyPos(), pos.getY(), 25000);
			this.poisonList.add(poison);
			this.addChild(poison);
		}
	}

	private void checkVPCollisions(ArrayList<String> pressedKeys) {
		for (PickedUpItem vp : vpList) {
			if (player1.getNet().collidesWithGlobal(vp) && !vp.isPickedUp()
					&& pressedKeys.contains(this.playerManager.getPrimaryKey(1))) {
				this.dispatchEvent(new GameEvent(EventTypes.PICKUP_VP.toString(), this));
				vp.dispatchEvent(new GameEvent(EventTypes.PICKUP_VP.toString(), vp));
				this.player1.dispatchEvent(new GameEvent(EventTypes.PICKUP_VP.toString(), this.player1));
				System.out.println("Player 1's Number of VP: " + this.levelManager.getVPCollected(1));
				System.out.println("Player 2's Number of VP: " + this.levelManager.getVPCollected(2));
				System.out.println("Total number of VP: " + this.playerManager.getVpCount());
			}

			if (player2.getNet().collidesWithGlobal(vp) && !vp.isPickedUp()
					&& pressedKeys.contains(this.playerManager.getPrimaryKey(2))) {
				this.dispatchEvent(new GameEvent(EventTypes.PICKUP_VP.toString(), this));
				vp.dispatchEvent(new GameEvent(EventTypes.PICKUP_VP.toString(), vp));
				this.player2.dispatchEvent(new GameEvent(EventTypes.PICKUP_VP.toString(), this.player2));
				System.out.println("Player 1's Number of VP: " + this.levelManager.getVPCollected(1));
				System.out.println("Player 2's Number of VP: " + this.levelManager.getVPCollected(2));
				System.out.println("Total number of VP: " + this.playerManager.getVpCount());
			}
		}
	}

	private void checkPoisonCollisions(ArrayList<String> pressedKeys) {
		for (PickedUpItem poison : poisonList) {
			if (player1.collidesWithGlobal(poison) && !poison.isPickedUp()) {
				this.dispatchEvent(new GameEvent(EventTypes.PICKUP_POISON.toString(), this));
				poison.dispatchEvent(new GameEvent(EventTypes.PICKUP_POISON.toString(), poison));
				this.player1.dispatchEvent(new GameEvent(EventTypes.POISON_PLAYER.toString(), this.player1));
				System.out.println("Player 1's Health: " + this.playerManager.getHealth(1));
				System.out.println("Player 2's Health: " + this.playerManager.getHealth(2));

			}

			if (player2.collidesWithGlobal(poison) && !poison.isPickedUp()) {
				this.dispatchEvent(new GameEvent(EventTypes.PICKUP_POISON.toString(), this));
				poison.dispatchEvent(new GameEvent(EventTypes.PICKUP_POISON.toString(), poison));
				this.player2.dispatchEvent(new GameEvent(EventTypes.POISON_PLAYER.toString(), this.player2));
				System.out.println("Player 1's Health: " + this.playerManager.getHealth(1));
				System.out.println("Player 2's Health: " + this.playerManager.getHealth(2));

			}
			// Check all poison collisions with each student
			for (Student student : studentList) {
				if (student.collidesWithGlobal(poison) && !poison.isPickedUp()) {
					//Note: right now poison does not stack
					if (!student.isPoisoned() && !student.isDead()) {
						student.dispatchEvent(new GameEvent(EventTypes.POISON_STUDENT.toString(), student));
					
						this.dispatchEvent(new GameEvent(EventTypes.PICKUP_POISON.toString(), this));
						poison.dispatchEvent(new GameEvent(EventTypes.PICKUP_POISON.toString(), poison));
						System.out.println("Student's Health: " + student.getCurrentHealth());
					}
				}
			}
			// Check all poison collisions with each player's net
			if (player1.getNet().collidesWithGlobal(poison) && !poison.isPickedUp()
					&& pressedKeys.contains(this.playerManager.getPrimaryKey(1))) {
				this.dispatchEvent(new GameEvent(EventTypes.PICKUP_POISON.toString(), this));
				poison.dispatchEvent(new GameEvent(EventTypes.PICKUP_POISON.toString(), poison));
				this.player1.dispatchEvent(new GameEvent(EventTypes.PICKUP_POISON.toString(), this.player1));
				System.out.println("Player 1's Number of Poison Collected: " + this.levelManager.getPoisonCollected(1));
				System.out.println("Player 2's Number of Poison Collected: " + this.levelManager.getPoisonCollected(2));
			}

			if (player2.getNet().collidesWithGlobal(poison) && !poison.isPickedUp()
					&& pressedKeys.contains(this.playerManager.getPrimaryKey(2))) {
				this.dispatchEvent(new GameEvent(EventTypes.PICKUP_POISON.toString(), this));
				poison.dispatchEvent(new GameEvent(EventTypes.PICKUP_POISON.toString(), poison));
				this.player2.dispatchEvent(new GameEvent(EventTypes.PICKUP_POISON.toString(), this.player2));
				System.out.println("Player 1's Number of Poison Collected: " + this.levelManager.getPoisonCollected(1));
				System.out.println("Player 2's Number of Poison Collected: " + this.levelManager.getPoisonCollected(2));
			}
			if (this.playerManager.getHealth(1) == 0 || this.playerManager.getHealth(2) == 0) {
				/* OUT OF HEALTH LOGIC */
				this.stopLevel();
				this.endLevelScreen.setDialog(endLevelScreen.LOSE_NO_HEALTH);
				this.endLevelScreen.setExperience((int) this.calcExp(1));
				this.endLevelScreen.setNumPlayer(1);
				this.endLevelScreen.setVisible(true);
				/* Reset stats for next level */
				this.dispatchEvent(new GameEvent(EventTypes.LOSE_LEVEL.toString(), this));
				this.playerManager.setHealth(this.playerManager.getMaxHealth(1), 1);
			}

		}
	}

	private void checkStudentCollisions(ArrayList<String> pressedKeys) {
		int distToCure = 30; //how close you need to be to the student
		boolean atLeastOneStudentAlive = false;
		for (Student student : studentList) {
			if (player1.inRangeGlobal(student, distToCure) && student.isPoisoned() && this.playerManager.getNumGingerAle() > 0
					&& !student.isDead() && pressedKeys.contains(this.playerManager.getSecondaryKey(1))) {
				// this.dispatchEvent(new
				// GameEvent(EventTypes.CURE_STUDENT.toString(), this));
				student.dispatchEvent(new GameEvent(EventTypes.CURE_STUDENT.toString(), student));
				this.player1.dispatchEvent(new GameEvent(EventTypes.CURE_STUDENT.toString(), this.player1));
				this.dispatchEvent(new GameEvent(EventTypes.CURE_STUDENT.toString(), this.player2));	
				System.out.println("Player 1's Number of Students Cured: " + this.levelManager.getStudentsCured(1));
				System.out.println("Player 2's Number of Students Cured: " + this.levelManager.getStudentsCured(2));
			} else if (player2.inRangeGlobal(student, distToCure)&& student.isPoisoned() && this.playerManager.getNumGingerAle() > 0
					&& !student.isDead() && pressedKeys.contains(this.playerManager.getSecondaryKey(2))) {
				// this.dispatchEvent(new
				// GameEvent(EventTypes.CURE_STUDENT.toString(), this));
				student.dispatchEvent(new GameEvent(EventTypes.CURE_STUDENT.toString(), student));
				this.dispatchEvent(new GameEvent(EventTypes.CURE_STUDENT.toString(), this));
				this.player2.dispatchEvent(new GameEvent(EventTypes.CURE_STUDENT.toString(), this.player2));
				System.out.println("Player 1's Number of Students Cured: " + this.levelManager.getStudentsCured(1));
				System.out.println("Player 2's Number of Students Cured: " + this.levelManager.getStudentsCured(2));
			}
			if(!student.isDead()) {
				atLeastOneStudentAlive = true;
			}
		} 
		if (!atLeastOneStudentAlive) {
			/* ALL STUDENTS DIED logic */
			this.stopLevel();
			this.endLevelScreen.setDialog(endLevelScreen.LOSE_STUDENTS);
			this.endLevelScreen.setExperience((int) this.calcExp(1));
			this.endLevelScreen.setNumPlayer(1);
			this.endLevelScreen.setVisible(true);
			/* reset stats */
			this.dispatchEvent(new GameEvent(EventTypes.LOSE_LEVEL.toString(), this));
			this.playerManager.setHealth(this.playerManager.getMaxHealth(1), 1);
		}

	}	
	private void spawnProjectiles() {
		if (this.vpClock != null) {
			if (this.vpClock.getElapsedTime() > (VP_SPAWN_INTERVAL)) {
				spawnVP();
				this.vpClock.resetGameClock();
			}
		}
		if (this.poisonClock != null) {
			if (this.poisonClock.getElapsedTime() > (POISON_SPAWN_INTERVAL)) {
				spawnPoison();
				this.poisonClock.resetGameClock();
			}
		}
	}

	public void openDoor() {
		// TODO: Leandra
	}

	public void keepTime() {
		if (this.gameClock != null) {
			if (this.gameClock.getElapsedTime() > GAME_TIME) {
				/* WIN LEVEL logic */
				this.stopLevel();
				this.endLevelScreen.setDialog(endLevelScreen.WIN);
				this.endLevelScreen.setExperience((int) this.calcExp(1));
				this.endLevelScreen.setNumPlayer(1);
				this.endLevelScreen.setVisible(true);
				/* Reset stats */
				this.dispatchEvent(new GameEvent(EventTypes.WIN_LEVEL.toString(), this));
				this.playerManager.setHealth(this.playerManager.getMaxHealth(1), 1);
			}
		}
	}

	public double calcExp(int numPlayer) {
		double exp = 0;
		for (Student student: studentList)
		{
			exp += student.getCurrentHealth()/student.getMaxHealth() * 100;  //Health should be a max of 25000
		}
		exp += this.levelManager.getPoisonCollected(numPlayer) * 10;
		exp += this.levelManager.getStudentsCured(numPlayer) * 50;
		exp += this.levelManager.getVPCollected(numPlayer) * 10;
		exp += this.playerManager.getHealth(numPlayer)/this.playerManager.getMaxHealth(numPlayer) * 100;
		this.playerManager.setExperience(this.playerManager.getExperience(numPlayer) + (int)exp, numPlayer);
		// FIXME: Attributes should be based on how much experience was earned?
		this.playerManager.setAttrPoints(this.playerManager.getAttrPoints(numPlayer) + (int)2, numPlayer);
		return exp;
		
	}
	public void stopLevel() {
		this.inPlay = false;
		this.stat.setVisible(false);
		this.soundManager.stopAll();
	}

	private void garbageVPCollect() {
		for (Iterator<PickedUpItem> it = vpList.iterator(); it.hasNext();) {
			PickedUpItem garbage = it.next();
			if (garbage.isPickedUp()) {
				it.remove();
			} else if (!garbage.collidesWithGlobal(this)) {
				it.remove();
			}
		}
	}

	private void garbagePoisonCollect() {
		for (Iterator<PickedUpItem> it = poisonList.iterator(); it.hasNext();) {
			PickedUpItem garbage = it.next();
			if (garbage.isPickedUp()) {
				it.remove();
			} else if (!garbage.collidesWithGlobal(this)) {
				it.remove();
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g); // draws children
		if (this.inPlay) {
			Font f = new Font("Dialog", Font.PLAIN, 20);
			g.setFont(f);
			int timeLeft = (int) (GAME_TIME - this.gameClock.getElapsedTime())/1000;
			if (timeLeft < 0) {
				timeLeft = 0;
			}
			g.drawString("Time Left: " + timeLeft, 0, 20);
		}
		/* if(this.playArea != null){
			this.playArea.drawHitboxGlobal(g);
		} debugging */
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys); // updates children
		if (this.inPlay) {
			this.keepTime();
			this.spawnProjectiles();
			this.checkVPCollisions(pressedKeys);
			this.garbagePoisonCollect();
			this.garbageVPCollect();
			this.checkPoisonCollisions(pressedKeys);
			this.checkStudentCollisions(pressedKeys);
			this.updatePlayer(pressedKeys, this.player1);
			if(this.gameManager.getNumPlayers() == 2) {
				this.updatePlayer(pressedKeys, this.player2);
			}
		}
		if (myTweenJuggler != null) {
			myTweenJuggler.nextFrame();
		}

	}
	
	/* collision detection and movement for players */
	
	public void updatePlayer(ArrayList<String> pressedKeys, Player player){
		if (player != null && player.getNet() != null) {
			if (player.isActive()) {
				this.moveSpriteCartesianAnimate(pressedKeys, player);
			}
			// if there are no keys being pressed, and sprite is walking, then
			// stop the animation
			if (pressedKeys.isEmpty() && player.isPlaying()
					&& Arrays.asList(CARDINAL_DIRS).contains(player.getCurrentAnimation())) {
				player.stopAnimation();
			}

		}
	}
	
	public void moveSpriteCartesianAnimate(ArrayList<String> pressedKeys, Player player) {
		double speed = this.playerManager.getSpeed(player.getNumPlayer());
		/*
		 * Make sure this is not null. Sometimes Swing can auto cause an extra
		 * frame to go before everything is initialized
		 */
		Position originalPos = new Position(player.getxPos(), player.getyPos());
		if (player != null && player.getNet() != null) {
			/*
			 * update mario's position if a key is pressed, check bounds of
			 * canvas
			 */
			
			
			if (pressedKeys.contains(this.playerManager.getUpKey(player.getNumPlayer()))) {
				
				player.setyPos(player.getyPos() - speed);
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), this));
				
				if (!player.isPlaying() || player.getCurrentAnimation() != "up") {
					player.animate("up");
				}
				player.setDirection("up");
				player.moveNet("up");

			}
			if (pressedKeys.contains(this.playerManager.getDownKey(player.getNumPlayer()))) {
				
				player.setyPos(player.getyPos() + speed);
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), this));
				if (!player.isPlaying() || player.getCurrentAnimation() != "down") {
					player.animate("down");
				}
				player.setDirection("down");
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), player));
				player.moveNet("down");
			}
			if (pressedKeys.contains(this.playerManager.getLeftKey(player.getNumPlayer()))) {
				player.setxPos(player.getxPos() - speed);
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), this));
				
				if (!player.isPlaying() || player.getCurrentAnimation() != "left") {
					player.animate("left");
				}
				player.setDirection("left");
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), player));
				player.moveNet("left");
			}
			if (pressedKeys.contains(this.playerManager.getRightKey(player.getNumPlayer()))) {

				player.setxPos(player.getxPos() + speed);
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), this));
			
					
				if (!player.isPlaying() || player.getCurrentAnimation() != "right") {
					player.animate("right");
				}
				player.setDirection("right");
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), player));
				player.moveNet("right");
			}
			if (pressedKeys.contains(this.playerManager.getPrimaryKey(player.getNumPlayer()))) {
				String currentDir = player.getDirection();
				// Until we have combined net and walking animation, net
				// animation overrides walking animation

				if (player.isPlaying() && !player.getCurrentAnimation().contains("net")) {
					// System.out.println("STOPPING\n");
					player.stopAnimation();
				}

				player.animateOnce("net" + currentDir, this.playerManager.getSwingSpeed(player.getNumPlayer()));
				player.dispatchEvent(new GameEvent(EventTypes.SWING_NET.toString(), player));
			}
			
		}
		if (playerCollision(player.getHitboxGlobal(), player.getNumPlayer())) {
			player.setPosition(originalPos);//move the player back
		} else {
			//don't move the player
		}
	}
	// Rectangle r is the players global hitbox
	private boolean playerCollision(Rectangle r, int numPlayer) {
		/* Check collisions with students */
		for(Student student: studentList) {
			if(r.intersects(student.getHitboxGlobal())) {
				return true;
			}
		}
		/* Check collisions with the other player */
		switch (numPlayer) {
			case 1:
				if(r.intersects(this.player2.getHitboxGlobal())) {
					return true;
				}
				break;
			case 2:
				if(r.intersects(this.player1.getHitboxGlobal())) {
					return true;
				}
				break;
			default:
				break;
		}
		/* Check whether player is not inside of the play area */
		if(!(this.playArea.getHitboxGlobal().contains(r))) {
			return true;
		}
		/* Check whether player is too close to the boss */
		Rectangle bossHitboxRange = this.boss.getHitboxGlobal();
		bossHitboxRange.grow(20, 20);
		if(r.intersects(bossHitboxRange)) {
			return true;
		} 
		return false;
	}
}



	

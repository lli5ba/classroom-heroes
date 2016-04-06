package edu.virginia.game.objects;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
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
	private GameClock gameClock;
	private GameClock poisonClock;
	private GameClock vpClock;
	public static final double VP_SPAWN_INTERVAL = 1500;
	public static final double POISON_SPAWN_INTERVAL = 1000;
	public static final double GAME_TIME = 60000; 
	private static boolean hit = false;
	public static int vp1 = 0;
	public static int vp2 = 0;
	public static int vpCount;
	public static int health1 = 5;
	public static int health2 = 5;
	public ArrayList<PickedUpItem> vpList = new ArrayList<PickedUpItem>();
	ArrayList<PickedUpItem> poisonList = new ArrayList<PickedUpItem>();
	ArrayList<Student> studentList = new ArrayList<Student>();

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
		
		/* Constructing players and their event listeners */
		player1 = new Player("Player1", "player/player1.png", 
				"player/player-spritesheet-1.png", "resources/player/player-spritesheet-1-frameInfo.txt", 1);
		player2 = new Player("Player2", "player/player1.png", 
				"player/player-spritesheet-1.png", "resources/player/player-spritesheet-1-frameInfo.txt", 2);
		
		this.player1.addEventListener(playerManager, EventTypes.POISON_PLAYER.toString());
		this.player1.addEventListener(levelManager, EventTypes.PICKUP_VP.toString());
		this.player1.addEventListener(playerManager, EventTypes.CURE_STUDENT.toString());
		this.player1.addEventListener(levelManager, EventTypes.CURE_STUDENT.toString());
		
		this.player2.addEventListener(playerManager, EventTypes.POISON_PLAYER.toString());
		this.player2.addEventListener(levelManager, EventTypes.PICKUP_VP.toString());
		this.player2.addEventListener(playerManager, EventTypes.CURE_STUDENT.toString());
		this.player2.addEventListener(levelManager, EventTypes.CURE_STUDENT.toString());

		if(this.gameManager.getNumPlayers() == 1) {
			//set player2 inactive and invisible
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
		this.boss.setPosition(this.getWidth() * .400, this.getHeight() * .003);
		this.boss.setScaleX(.75);
		this.boss.setScaleY(.75);

		/* Generate Students */
		Student student0 = new Student("Student0", "0", "back");
		student0.addEventListener(studentManager, EventTypes.POISON_STUDENT.toString());
		student0.addEventListener(studentManager, EventTypes.CURE_STUDENT.toString());
		this.addChild(student0);
		student0.setPosition(this.getWidth() * .5, this.getHeight() * .742);
		this.studentList.add(student0);
		

		/* setting height and width of background 
		 * must do this after setting position of all children items! */
		this.setHeight(gameManager.getGameHeight());
		this.setWidth(gameManager.getGameWidth());
		
		soundManager.LoadMusic("bg", "theme.wav");
		soundManager.PlayMusic("bg");
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

	public void spawnVP() {
		if (myTweenJuggler != null) {
			VP vp = new VP("VP", "projectiles/vp0.png", "projectiles/vpsheet.png",
					"resources/projectiles/vpsheetspecs.txt");
			vp.setCenterPos(this.boss.getCenterPos());
			vp.addEventListener(projectileManager, EventTypes.PICKUP_VP.toString());
			vp.addEventListener(playerManager, EventTypes.PICKUP_VP.toString());
			Tween tween2 = new Tween(vp, TweenTransitions.LINEAR);
			myTweenJuggler.add(tween2);
			Position pos = generatePosition(vp.getxPos(), vp.getyPos(), 1000);
			tween2.animate(TweenableParam.POS_X, vp.getxPos(), pos.getX(), 30000);
			tween2.animate(TweenableParam.POS_Y, vp.getyPos(), pos.getY(), 30000);
			this.vpList.add(vp);
			this.addChild(vp);
			this.hit = false;
			// FIXME: hit sometimes gives 2 vp; i think due to hitbox??
		}
	}

	public void spawnPoison() {
		if (myTweenJuggler != null) {
			// FIXME: sprite sheet not implemented
			Poison poison = new Poison("Poison", "projectiles/poison.png");
			poison.setCenterPos(this.boss.getCenterPos());
			poison.addEventListener(projectileManager, EventTypes.PICKUP_POISON.toString());
			poison.addEventListener(playerManager, EventTypes.PICKUP_POISON.toString());
			Tween tween2 = new Tween(poison, TweenTransitions.LINEAR);
			myTweenJuggler.add(tween2);
			Position pos = generatePosition(poison.getxPos(), poison.getyPos(), 1000);
			tween2.animate(TweenableParam.POS_X, poison.getxPos(), pos.getX(), 30000);
			tween2.animate(TweenableParam.POS_Y, poison.getyPos(), pos.getY(), 30000);
			this.poisonList.add(poison);
			this.addChild(poison);
		}
	}

	private void checkVPCollisions(ArrayList<String> pressedKeys) {
		for (PickedUpItem vp : vpList) {
			if (player1.getNet().collidesWithGlobal(vp) && !vp.isPickedUp() 
					&& pressedKeys.contains(this.playerManager.getPrimaryKey(1))) {
		//FIXME: Leandra, we talked about how using primary key won't work
				//because there are other times primary key is used
				//But the other times primary key is used (during store),
				//vp will never collide since vp will not be spawning
				//This works rn, but let me know what you think
				//Oh Yay! it works :D This sounds good to me unless we run into problems!
				this.dispatchEvent(new GameEvent(EventTypes.PICKUP_VP.toString(), this));
				vp.dispatchEvent(new GameEvent(EventTypes.PICKUP_VP.toString(), vp));
				this.player1.dispatchEvent(new GameEvent(EventTypes.PICKUP_VP.toString(), this.player1));

				//FIXME: sound
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
				
				//FIXME: sound
				System.out.println("Player 1's Health: " + this.playerManager.getHealth(1));
				System.out.println("Player 2's Health: " + this.playerManager.getHealth(2));
				
			}
			//Check all poison collisions with each student
			for (Student student : studentList) {
				if (student.collidesWithGlobal(poison) && !poison.isPickedUp()) {
						if (!student.isPoisoned()) {
							student.dispatchEvent(new GameEvent(EventTypes.POISON_STUDENT.toString(), student));
						}
						this.dispatchEvent(new GameEvent(EventTypes.PICKUP_POISON.toString(), this));
						poison.dispatchEvent(new GameEvent(EventTypes.PICKUP_POISON.toString(), poison));
						//FIXME: sound
						System.out.println("Student's Health: " + student.getCurrentHealth());
				}
			}
			//Check all poison collisions with each player's net
			if (player1.getNet().collidesWithGlobal(poison) && !poison.isPickedUp() 
					&& pressedKeys.contains(this.playerManager.getPrimaryKey(1))) {
				this.dispatchEvent(new GameEvent(EventTypes.PICKUP_POISON.toString(), this));
				poison.dispatchEvent(new GameEvent(EventTypes.PICKUP_POISON.toString(), poison));
				this.player1.dispatchEvent(new GameEvent(EventTypes.PICKUP_POISON.toString(), this.player1));

				//FIXME: sound
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
			if(this.playerManager.getHealth(1) == 0 || this.playerManager.getHealth(2) == 0) {
				//FIXME: exit screen
				System.out.println("DEAD!");
				this.dispatchEvent(new GameEvent(EventTypes.LOSE_LEVEL.toString(), this));
				System.exit(0);
			}

		}
	}
	
	private void checkStudentCollisions(ArrayList<String> pressedKeys) {
		for (Student student : studentList) {
			//Check whether players are in range of student
			if (player1.inRangeGlobal(student, 200) && student.isPoisoned() && this.playerManager.getNumGingerAle() > 0
					&& pressedKeys.contains(this.playerManager.getSecondaryKey(1))) {
				//this.dispatchEvent(new GameEvent(EventTypes.CURE_STUDENT.toString(), this));
				student.dispatchEvent(new GameEvent(EventTypes.CURE_STUDENT.toString(), student));
				this.player1.dispatchEvent(new GameEvent(EventTypes.CURE_STUDENT.toString(), this.player1));

				//FIXME: sound
				System.out.println("Player 1's Number of Students Cured: " + this.levelManager.getStudentsCured(1));
				System.out.println("Player 2's Number of Students Cured: " + this.levelManager.getStudentsCured(2));
			} else if (player2.inRangeGlobal(student, 10) && student.isDead() && this.playerManager.getNumGingerAle() > 0
					&& pressedKeys.contains(this.playerManager.getSecondaryKey(2))) {
				//this.dispatchEvent(new GameEvent(EventTypes.CURE_STUDENT.toString(), this));
				student.dispatchEvent(new GameEvent(EventTypes.CURE_STUDENT.toString(), student));
				this.player2.dispatchEvent(new GameEvent(EventTypes.CURE_STUDENT.toString(), this.player2));

				//FIXME: sound
				System.out.println("Player 1's Number of Students Cured: " + this.levelManager.getStudentsCured(1));
				System.out.println("Player 2's Number of Students Cured: " + this.levelManager.getStudentsCured(2));
			}
			

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
		//TODO: Leandra
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g); // draws children
		spawnProjectiles();
	}
	
	public void keepTime() {
		if (this.gameClock != null) {
			if (this.gameClock.getElapsedTime() > GAME_TIME) {
				this.dispatchEvent(new GameEvent(EventTypes.WIN_LEVEL.toString(), this));
			}
		}
	}
	
	private void garbageVPCollect()
	{
		for(Iterator<PickedUpItem> it = vpList.iterator(); it.hasNext();)
		{
			PickedUpItem garbage = it.next();
			if(garbage.isPickedUp())
			{
				it.remove();
			}
			else if(!garbage.collidesWithGlobal(this))
			{
				it.remove();
			}
		}
	}
	
	private void garbagePoisonCollect()
	{
		for(Iterator<PickedUpItem> it = poisonList.iterator(); it.hasNext();)
		{
			PickedUpItem garbage = it.next();
			if(garbage.isPickedUp())
			{
				it.remove();
			}
			else if(!garbage.collidesWithGlobal(this))
			{
				it.remove();
			}
		}
	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys); // updates children
		this.checkVPCollisions(pressedKeys);
		this.garbagePoisonCollect();
		this.garbageVPCollect();
		this.checkPoisonCollisions(pressedKeys);
		this.checkStudentCollisions(pressedKeys);
		if (myTweenJuggler != null) {
			myTweenJuggler.nextFrame();
		}

	}

}

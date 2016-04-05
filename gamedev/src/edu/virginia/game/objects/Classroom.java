package edu.virginia.game.objects;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.PickedUpItem;
import edu.virginia.engine.display.SoundManager;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.CollisionEvent;
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
import edu.virginia.game.managers.StudentManager;

//This class represents a game screen object to be used for levels and hallway scenes.
//This way you can instantiate a GameScreen and add game elements as children.
public class Classroom extends DisplayObjectContainer {

	private PlayerManager playerManager = PlayerManager.getInstance();
	private LevelManager levelManager = LevelManager.getInstance();
	private static GameManager gameManager = GameManager.getInstance();
	private StudentManager studentManager = StudentManager.getInstance();
	private ProjectileManager projectileManager = ProjectileManager.getInstance();
	private SoundManager mySoundManager;
	private TweenJuggler myTweenJuggler = TweenJuggler.getInstance();
	private Player player1;
	private Player player2;
	private Boss boss;
	private GameClock gameClock;
	private GameClock poisonClock;
	private GameClock vpClock;
	public static final double VP_SPAWN_INTERVAL = 1500;
	public static final double POISON_SPAWN_INTERVAL = 3000;
	private static boolean hit = false;
	public static int vp1 = 0;
	public static int vp2 = 0;
	public static int poisoncount = 5;
	ArrayList<PickedUpItem> vpList = new ArrayList<PickedUpItem>();
	ArrayList<PickedUpItem> poisonList = new ArrayList<PickedUpItem>();
	ArrayList<Student> studentList = new ArrayList<Student>();

	public Classroom(String id) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		super(id, "classroom/classroom-background-" + gameManager.getNumLevel() + ".png");

		/* GameClocks */
		this.gameClock = new GameClock();
		this.poisonClock = new GameClock();
		this.vpClock = new GameClock();
		
		player1 = new Player("Player1", "player/player1.png", 
				"player/player-spritesheet-1.png", "resources/player/player-spritesheet-1-frameInfo.txt", 1);
		player2 = new Player("Player2", "player/player1.png", 
				"player/player-spritesheet-1.png", "resources/player/player-spritesheet-1-frameInfo.txt", 2);
		if(this.gameManager.getNumPlayers() == 1) {
			//set player2 inactive and invisible
			player2.setActive(false);
			player2.setVisible(false);
		}

		boss = new Boss("Boss", "Mario.png");
		
		this.addChild(player1);
		this.addChild(player2);
		this.addChild(boss);
		/* Generate Students */
		Student student0 = new Student("Student0", "0", "back");
		this.addChild(student0);
		student0.setPosition(this.getWidth() * .5, this.getHeight() * .742);
		this.studentList.add(student0);

		this.player1.setPosition(this.getWidth() * .08, this.getHeight() * .742);

		this.player2.setPosition(this.getWidth() * .814, this.getHeight() * .742);

		this.boss.setPosition(this.getWidth() * .400, this.getHeight() * .003);
		this.boss.setScaleX(.75);
		this.boss.setScaleY(.75);

		this.setHeight(gameManager.getGameHeight());
		this.setWidth(gameManager.getGameWidth());

		mySoundManager = new SoundManager();
<<<<<<< HEAD
		//mySoundManager.LoadMusic("bg", "theme.wav");
		//mySoundManager.PlayMusic("bg");
=======
		mySoundManager.LoadMusic("bg", "theme.wav");
		mySoundManager.PlayMusic("bg");
>>>>>>> db8190e0a697c9eb1af5717e94e2d3ec54bebce9
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
		if(myTweenJuggler != null) {
			VP vp = new VP("VP", "projectiles/vp0.png", 
					"projectiles/vpsheet.png", "resources/projectiles/vpsheetspecs.txt");
			vp.setCenterPos(this.boss.getCenterPos());
			vp.addEventListener(playerManager, PickedUpEvent.KEY_PICKED_UP);
			vp.addEventListener(playerManager, CollisionEvent.COLLISION);
			Tween tween2 = new Tween(vp, TweenTransitions.LINEAR);
			myTweenJuggler.add(tween2);
			Position pos = generatePosition(vp.getxPos(), vp.getyPos(), 1000);
			tween2.animate(TweenableParam.POS_X, vp.getxPos(), pos.getX(), 10000);
			tween2.animate(TweenableParam.POS_Y, vp.getyPos(), pos.getY(), 10000);
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
			poison.addEventListener(playerManager, PickedUpEvent.KEY_PICKED_UP);
			poison.addEventListener(playerManager, CollisionEvent.COLLISION);
			Tween tween2 = new Tween(poison, TweenTransitions.LINEAR);
			myTweenJuggler.add(tween2);
			Position pos = generatePosition(poison.getxPos(), poison.getyPos(), 1000);
			tween2.animate(TweenableParam.POS_X, poison.getxPos(), pos.getX(), 10000);
			tween2.animate(TweenableParam.POS_Y, poison.getyPos(), pos.getY(), 10000);
			this.poisonList.add(poison);
			this.addChild(poison);
		}
	}

	private void checkVPCollisions(ArrayList<String> pressedKeys) {
		for (PickedUpItem vp : vpList) {
			if (player1.getNet().collidesWithGlobal(vp) && !vp.isPickedUp()) {
				vp.dispatchEvent(new CollisionEvent(CollisionEvent.COLLISION, vp));
				vp.setPickedUp(true);
				this.vp1++;
				System.out.println("Player 1's Number of VP: " + vp1);
			}
			
			if (player2.getNet().collidesWithGlobal(vp) && !vp.isPickedUp()) {
				vp.dispatchEvent(new CollisionEvent(CollisionEvent.COLLISION, vp));
				vp.setPickedUp(true);
				this.vp2++;
				System.out.println("Player 2's Number of VP: " + vp2);
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

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys); // updates children
		this.checkVPCollisions(pressedKeys);

		if (myTweenJuggler != null) {
			myTweenJuggler.nextFrame();
		}

	}

}

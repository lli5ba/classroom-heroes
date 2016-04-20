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
	public static final String[] FLORYAN_DIR = new String[] { "tossdown", "tossdownleft", "tossdownright", "tossleft",
			"tossright", "tossup", "tossupleft", "tossupright" };
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
	private Sprite table1;
	private Sprite table2;
	private Sprite table3;
	private PlayerStatBox stat;
	private PlayerStat pstat;
	private EndLevelScreen endLevelScreen;
	private GameClock gameClock;
	private GameClock poisonClock;
	private GameClock vpClock;
	private boolean inPlay;
	public static final double VP_SPAWN_INTERVAL = 1500;
	public static final double POISON_SPAWN_INTERVAL = 1750;
	public static final double GAME_TIME = 60000;
	public ArrayList<PickedUpItem> vpList = new ArrayList<PickedUpItem>();
	ArrayList<PickedUpItem> poisonList = new ArrayList<PickedUpItem>();
	ArrayList<Student> studentList = new ArrayList<Student>();
	ArrayList<Sprite> furnitureList = new ArrayList<Sprite>();
	private DisplayObjectContainer playArea;
	private ArrayList<String> prevPressedKeys;

	public Classroom(String id) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		super(id, "classroom/classroom-background-" + gameManager.getNumLevel() + ".png");

		try {
			this.soundManager = SoundManager.getInstance();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

		/* PrevPressedKeys to find ReleasedKeys */
		prevPressedKeys = new ArrayList<String>();

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
		this.addEventListener(soundManager, EventTypes.POISON_STUDENT.toString());

		
		/* Constructing furniture */
		
		spawnTable("table1", "blue", this.getWidth() * .446, this.getHeight() * .592);
		spawnTable("table2", "blue", this.getWidth() * .746, this.getHeight() * .5);
		spawnTable("table3", "blue", this.getWidth() * .146, this.getHeight() * .5);
		
		
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
<<<<<<< HEAD
		boss = new Boss("Boss", "floryan/floryan-default.png", "floryan/floryan-spritesheet.png",
				"resources/floryan/floryan-spritesheet.txt");
=======
		boss = new Boss("floryan", "floryan/floryan-default.png", 
				"floryan/floryan-spritesheet.png", "resources/floryan/floryan-spritesheet.txt");
>>>>>>> a2964e22496bcbbe1a48c7d80f0af5428f394df9
		this.addChild(boss);
		this.boss.setPosition(this.getWidth() * .46, this.getHeight() * .18);
		this.boss.setScaleX(.7);
		this.boss.setScaleY(.7);

		
		
		/* Generate Students */
		spawnStudent("Student0", "down", this.getWidth() * .5, this.getHeight() * .742);
		spawnStudent("Student1", "left", this.getWidth() * .8, this.getHeight() * .65);
		spawnStudent("Student2", "right", this.getWidth() * .2, this.getHeight() * .65);
		
		

		/* set play area bounds */
		this.playArea = new DisplayObjectContainer("playArea", "Mario.png"); // random
																				// png
																				// file
		this.playArea.setVisible(false);
		this.playArea.setWidth(this.getWidth());
		this.playArea.setHeight(this.getHeight() * .8);
		this.addChild(playArea);
		this.playArea.setPosition(0, this.getHeight() * .2);

		/* Printing out stats */
		stat = new PlayerStatBox("stats");
		this.addChild(stat);

		/* Printing out Player stats */
		pstat = new PlayerStat("pstats");
		this.addChild(pstat);

		/* End Level Screen */
		endLevelScreen = new EndLevelScreen("endLevel");
		this.endLevelScreen.setVisible(false);
		this.addChild(endLevelScreen);
		this.endLevelScreen.setPosition(this.getWidth() * 0, this.getHeight() * .05);
		// FIXME: Only works for one player right now,
		// not built well at the moment because just needed to finish!

		/* the game is in session (not on end screen) */
		this.inPlay = true;

		/* music */
		soundManager.stopAll();
		if (!soundManager.isPlayingMusic()) {
			soundManager.LoadMusic("bg", "theme.wav");
			soundManager.PlayMusic("bg");
		}

		/*
		 * setting height and width of background DO THIS LAST!
		 */
		this.setHeight(gameManager.getGameHeight());
		this.setWidth(gameManager.getGameWidth());
	}

	public void spawnTable(String id, String style, double xPos, double yPos) {
		if (style.equals("blue")) {
			table1 = new Sprite(id, "table/Table.png");
			table1.setScaleX(.4);
			table1.setScaleY(.6);
			this.addChild(table1);
			table1.setOriginalHitbox(new Rectangle((int) table1.getOriginalHitbox().getX(),
					(int) table1.getOriginalHitbox().getY() - 2,
					(int) table1.getOriginalHitbox().getWidth(),
					(int) (table1.getOriginalHitbox().getHeight()*.8)));
			this.table1.setPosition(xPos, yPos);
			this.furnitureList.add(table1);
		} else if (style.equals("wood")) {
			
		}
	}
<<<<<<< HEAD
	/** Generates random position on semi-circle for spawning poison/VP **/
	public Position generatePosition(String vpOrPoison, double centerx, double centery, double radius) {
		double ang_min = (0);
		double ang_max = (Math.PI);
		Random rand1 = new Random();
		double d = ang_min + rand1.nextDouble() * (ang_max - ang_min);
		// System.out.println("d: " + d);
		// if vp is thrown, give boss right direction to turn
		// FIXME
		if (vpOrPoison.equals("vp") && boss != null) {
			this.boss.setLastThrownDegrees(Math.toDegrees(d));
			//System.out.println("Degrees: " + this.boss.getLastThrownDegrees());
			if (this.boss.getLastThrownDegrees() > 0 && this.boss.getLastThrownDegrees() < 60) {
				boss.animate("tossdownright");
				//System.out.println("tossdownright");
			} else if (this.boss.getLastThrownDegrees() > 60 && this.boss.getLastThrownDegrees() < 120) {
				boss.animate("tossdown");
				//System.out.println("tossdown");
			} else if (this.boss.getLastThrownDegrees() > 120 && this.boss.getLastThrownDegrees() < 180) {

				boss.animate("tossdownleft");
				//System.out.println("tossdownleft");
			}
		}
		double x = centerx + radius * Math.cos(d);
		double y = centery + radius * Math.sin(d);
		return new Position(x, y);
	}

=======
	/* Note: floryan logic moved to boss class!*/
	
>>>>>>> a2964e22496bcbbe1a48c7d80f0af5428f394df9
	public void spawnStudent(String id, String animDir, double xPos, double yPos) {
		Student student1 = new Student(id, "0", animDir);
		student1.addEventListener(studentManager, EventTypes.POISON_STUDENT.toString());
		student1.addEventListener(studentManager, EventTypes.CURE_STUDENT.toString());
		this.addChild(student1);
		student1.setPosition(xPos, yPos);
		this.studentList.add(student1);
	}

	public void spawnVP() {
		VP vp = this.boss.spawnVP();
		if (vp != null) {
			this.vpList.add(vp);
			this.addChild(vp);
		}
	}

	public void spawnPoison() {
<<<<<<< HEAD
		if (myTweenJuggler != null) {
			Poison poison = new Poison("Poison");
			poison.setCenterPos(this.boss.getCenterPos());
			poison.addEventListener(projectileManager, EventTypes.PICKUP_POISON.toString());
			poison.addEventListener(playerManager, EventTypes.PICKUP_POISON.toString());
			Tween tween2 = new Tween(poison, TweenTransitions.LINEAR);
			myTweenJuggler.add(tween2);
			Position pos = generatePosition("posion", poison.getxPos(), poison.getyPos(), 1000);
			tween2.animate(TweenableParam.POS_X, poison.getxPos(), pos.getX(), 25000);
			tween2.animate(TweenableParam.POS_Y, poison.getyPos(), pos.getY(), 25000);
=======
		Poison poison = this.boss.spawnPoison();
		if (poison != null) {
>>>>>>> a2964e22496bcbbe1a48c7d80f0af5428f394df9
			this.poisonList.add(poison);
			this.addChild(poison);
		}
	}

	private void checkVPCollisions(ArrayList<String> pressedKeys) {
		for (PickedUpItem vp : vpList) {
			if (player1.getNetHitboxGlobal().intersects(vp.getHitboxGlobal()) //|| player1.collidesWithGlobal(vp)) 
					&& !vp.isPickedUp()) {
				this.dispatchEvent(new GameEvent(EventTypes.PICKUP_VP.toString(), this));
				vp.dispatchEvent(new GameEvent(EventTypes.PICKUP_VP.toString(), vp));
				this.player1.dispatchEvent(new GameEvent(EventTypes.PICKUP_VP.toString(), this.player1));
				System.out.println("Player 1's Number of VP: " + this.levelManager.getVPCollected(1));
				System.out.println("Player 2's Number of VP: " + this.levelManager.getVPCollected(2));
				System.out.println("Total number of VP: " + this.playerManager.getVpCount());
			}

			if (player2.getNetHitboxGlobal().intersects(vp.getHitboxGlobal()) //|| player2.collidesWithGlobal(vp)) 
					&& !vp.isPickedUp()) {
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
					// Note: right now poison does not stack
					if (!student.isPoisoned() && !student.isDead()) {
						student.dispatchEvent(new GameEvent(EventTypes.POISON_STUDENT.toString(), student));

						this.dispatchEvent(new GameEvent(EventTypes.PICKUP_POISON.toString(), this));
						poison.dispatchEvent(new GameEvent(EventTypes.PICKUP_POISON.toString(), poison));
						System.out.println("Student's Health: " + student.getCurrentHealth());
					}
				}
			}
			// Check all poison collisions with each player's net
			if (player1.getNetHitboxGlobal().intersects(poison.getHitboxGlobal()) && !poison.isPickedUp()) {
				this.dispatchEvent(new GameEvent(EventTypes.PICKUP_POISON.toString(), this));
				poison.dispatchEvent(new GameEvent(EventTypes.PICKUP_POISON.toString(), poison));
				this.player1.dispatchEvent(new GameEvent(EventTypes.PICKUP_POISON.toString(), this.player1));
				System.out.println("Player 1's Number of Poison Collected: " + this.levelManager.getPoisonCollected(1));
				System.out.println("Player 2's Number of Poison Collected: " + this.levelManager.getPoisonCollected(2));
			}

			if (player2.getNetHitboxGlobal().intersects(poison.getHitboxGlobal()) && !poison.isPickedUp()) {
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
		int distToCure = 30; // how close you need to be to the student
		boolean atLeastOneStudentAlive = false;
		for (Student student : studentList) {
			if (player1.inRangeGlobal(student, distToCure) && student.isPoisoned()
					&& this.playerManager.getNumGingerAle() > 0 && !student.isDead()
					&& pressedKeys.contains(this.playerManager.getSecondaryKey(1))) {
				// this.dispatchEvent(new
				// GameEvent(EventTypes.CURE_STUDENT.toString(), this));
				student.dispatchEvent(new GameEvent(EventTypes.CURE_STUDENT.toString(), student));
				this.player1.dispatchEvent(new GameEvent(EventTypes.CURE_STUDENT.toString(), this.player1));
				this.dispatchEvent(new GameEvent(EventTypes.CURE_STUDENT.toString(), this.player2));
				System.out.println("Player 1's Number of Students Cured: " + this.levelManager.getStudentsCured(1));
				System.out.println("Player 2's Number of Students Cured: " + this.levelManager.getStudentsCured(2));
			} else if (player2.inRangeGlobal(student, distToCure) && student.isPoisoned()
					&& this.playerManager.getNumGingerAle() > 0 && !student.isDead()
					&& pressedKeys.contains(this.playerManager.getSecondaryKey(2))) {
				// this.dispatchEvent(new
				// GameEvent(EventTypes.CURE_STUDENT.toString(), this));
				student.dispatchEvent(new GameEvent(EventTypes.CURE_STUDENT.toString(), student));
				this.dispatchEvent(new GameEvent(EventTypes.CURE_STUDENT.toString(), this));
				this.player2.dispatchEvent(new GameEvent(EventTypes.CURE_STUDENT.toString(), this.player2));
				System.out.println("Player 1's Number of Students Cured: " + this.levelManager.getStudentsCured(1));
				System.out.println("Player 2's Number of Students Cured: " + this.levelManager.getStudentsCured(2));
			}
			if (!student.isDead()) {
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
		// FIXME: want to display these stats on the endLevelScreen
		double exp = 0;
		for (Student student : studentList) {
			exp += student.getCurrentHealth() / student.getMaxHealth() * 100;
			// Health should be a max of 25000
		}
		exp += this.levelManager.getPoisonCollected(numPlayer) * 10;
		exp += this.levelManager.getStudentsCured(numPlayer) * 50;
		exp += this.levelManager.getVPCollected(numPlayer) * 10;
		exp += this.playerManager.getHealth(numPlayer) / this.playerManager.getMaxHealth(numPlayer) * 100;
		this.playerManager.setExperience(this.playerManager.getExperience(numPlayer) + (int) exp, numPlayer);
		// FIXME: Attributes should be based on how much experience was earned?
		this.playerManager.setAttrPoints(this.playerManager.getAttrPoints(numPlayer) + (int) 2, numPlayer);
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

	public void drawTimeLeft(Graphics g) {
		if (this.inPlay) {
			Font f = new Font("Dialog", Font.PLAIN, 20);
			g.setFont(f);
			int timeLeft = (int) (GAME_TIME - this.gameClock.getElapsedTime()) / 1000;
			if (timeLeft < 0) {
				timeLeft = 0;
			}
			g.drawString("Time Left: " + timeLeft, 0, 20);
		}
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g); // draws children
		this.drawTimeLeft(g);
		/*if (this.player1 != null) {
			this.player1.drawNetHitboxGlobal(g);
		}*/

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
<<<<<<< HEAD
=======
			this.aimThrowSmokeBomb(pressedKeys, this.player1);
		//	this.floryan(boss.getAnimation());
>>>>>>> a2964e22496bcbbe1a48c7d80f0af5428f394df9
			if (this.gameManager.getNumPlayers() == 2) {
				this.updatePlayer(pressedKeys, this.player2);
				this.aimThrowSmokeBomb(pressedKeys, this.player2);
			}
			
		}
		if (myTweenJuggler != null) {
			myTweenJuggler.nextFrame();
		}

		/* to calculate releasedKeys for use in moving player */
		this.prevPressedKeys.clear();
		this.prevPressedKeys.addAll(pressedKeys);

	}

	/* collision detection and movement for players */

	public void updatePlayer(ArrayList<String> pressedKeys, Player player) {
		if (player != null && player.getNetHitbox() != null) {
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

	public void aimThrowSmokeBomb(ArrayList<String> pressedKeys, Player player) {
		ArrayList<String> releasedKeys = new ArrayList<String>(this.prevPressedKeys);
		releasedKeys.removeAll(pressedKeys);
		/*
		 * Make sure this is not null. Sometimes Swing can auto cause an extra
		 * frame to go before everything is initialized
		 */
		if (!pressedKeys.contains(this.playerManager.getSecondaryKey(player.getNumPlayer()))) {
			player.setSmokebombVisible(false);
		}
		if (player != null && player.getNetHitbox() != null 
				&& pressedKeys.contains(this.playerManager.getSecondaryKey(player.getNumPlayer()))) {
			/*
			 * update player's position depending on key pressed
			 */
			player.moveSmokebomb(player.getDirection());
			String smokebombDir = player.getDirection();
			System.out.println(pressedKeys.toString());
			if (pressedKeys.contains(this.playerManager.getUpKey(player.getNumPlayer()))
					&& pressedKeys.contains(this.playerManager.getRightKey(player.getNumPlayer()))) {
				String dir = "up"; //right
				player.setDirection(dir);
				player.setDefaultImage(dir);
				player.moveSmokebomb(dir + "right");
				smokebombDir = dir + "right";
				
			} else if (pressedKeys.contains(this.playerManager.getUpKey(player.getNumPlayer()))
					&& pressedKeys.contains(this.playerManager.getLeftKey(player.getNumPlayer()))) {
				String dir = "up"; //left
				player.setDirection(dir);
				player.setDefaultImage(dir);
				player.moveSmokebomb(dir + "left");
				smokebombDir = dir + "left";
			} else if (pressedKeys.contains(this.playerManager.getDownKey(player.getNumPlayer()))
					&& pressedKeys.contains(this.playerManager.getRightKey(player.getNumPlayer()))) {
				String dir = "down"; //right
				player.setDirection(dir);
				player.setDefaultImage(dir);
				player.moveSmokebomb(dir + "right");
				smokebombDir = dir + "right";
			} else if (pressedKeys.contains(this.playerManager.getDownKey(player.getNumPlayer()))
					&& pressedKeys.contains(this.playerManager.getLeftKey(player.getNumPlayer()))) {
				String dir = "down"; //left
				player.setDirection(dir);
				player.setDefaultImage(dir);
				player.moveSmokebomb(dir + "left");
				smokebombDir = dir + "left";
			} else if (pressedKeys.contains(this.playerManager.getUpKey(player.getNumPlayer()))) {
				String dir = "up";
				player.setDirection(dir);
				player.setDefaultImage(dir);
				player.moveSmokebomb(dir);
				smokebombDir = dir;
			} else if (pressedKeys.contains(this.playerManager.getDownKey(player.getNumPlayer()))) {
				String dir = "down";
				player.setDirection(dir);
				player.setDefaultImage(dir);
				player.moveSmokebomb(dir);
				smokebombDir = dir;
			} else if (pressedKeys.contains(this.playerManager.getLeftKey(player.getNumPlayer()))) {
				String dir = "left";
				player.setDirection(dir);
				player.setDefaultImage(dir);
				player.moveSmokebomb(dir);
				smokebombDir = dir;
			} else if (pressedKeys.contains(this.playerManager.getRightKey(player.getNumPlayer()))) {
				String dir = "right";
				player.setDirection(dir);
				player.setDefaultImage(dir);
				player.moveSmokebomb(dir);
				smokebombDir = dir;
			}
			if (releasedKeys.contains(this.playerManager.getPrimaryKey(player.getNumPlayer()))) {
				/* throw puffbag */
				//FIXME: Leandra
			}
		}
	}
	

	public void moveSpriteCartesianAnimate(ArrayList<String> pressedKeys, Player player) {
		ArrayList<String> releasedKeys = new ArrayList<String>(this.prevPressedKeys);
		releasedKeys.removeAll(pressedKeys);
		double speed = this.playerManager.getSpeed(player.getNumPlayer());
		/*
		 * Make sure this is not null. Sometimes Swing can auto cause an extra
		 * frame to go before everything is initialized
		 */
		Position originalPos = new Position(player.getxPos(), player.getyPos());

		if (player != null && player.getNetHitbox() != null 
				&& !pressedKeys.contains(this.playerManager.getSecondaryKey(player.getNumPlayer()))) {
			/*
			 * update player's position depending on key pressed
			 */

			if (pressedKeys.contains(this.playerManager.getUpKey(player.getNumPlayer()))) {

				player.setyPos(player.getyPos() - speed);
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), this));

				if (!player.isPlaying() || player.getCurrentAnimation() != "up") {
					player.animate("up");
				}
				player.setDirection("up");
				

			}
			if (pressedKeys.contains(this.playerManager.getDownKey(player.getNumPlayer()))) {

				player.setyPos(player.getyPos() + speed);
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), this));
				if (!player.isPlaying() || player.getCurrentAnimation() != "down") {
					player.animate("down");
				}
				player.setDirection("down");
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), player));
				
			}
			if (pressedKeys.contains(this.playerManager.getLeftKey(player.getNumPlayer()))) {
				player.setxPos(player.getxPos() - speed);
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), this));

				if (!player.isPlaying() || player.getCurrentAnimation() != "left") {
					player.animate("left");
				}
				player.setDirection("left");
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), player));
				
			}
			if (pressedKeys.contains(this.playerManager.getRightKey(player.getNumPlayer()))) {

				player.setxPos(player.getxPos() + speed);
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), this));

				if (!player.isPlaying() || player.getCurrentAnimation() != "right") {
					player.animate("right");
				}
				player.setDirection("right");
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), player));
				
			}
			if (releasedKeys.contains(this.playerManager.getPrimaryKey(player.getNumPlayer()))) {
				String currentDir = player.getDirection();
				// Until we have combined net and walking animation, net
				// animation overrides walking animation
				if (player.isPlaying() && !player.getCurrentAnimation().contains("net")) {
					// System.out.println("STOPPING\n");
					player.stopAnimation();
				}

				player.animateOnceLock("net" + currentDir, this.playerManager.getSwingSpeed(player.getNumPlayer()));
				player.dispatchEvent(new GameEvent(EventTypes.SWING_NET.toString(), player));
			}

			// this revises the net animation mid-swing
			// if direction changes and animateOnce net sequence is playing
			if (player.isPlaying() && player.getCurrentAnimation().contains("net")
					&& !player.getCurrentAnimation().contains(player.getDirection())) {
				player.setCurrentAnimation("net" + player.getDirection());
			}
			// System.out.println("position: " + player.getHitboxGlobal().getX()
			// + ", " + player.getHitboxGlobal().getY());

			// FIXME: check for collisions
			if (playerCollision(player.getHitboxGlobal(), player.getNumPlayer())) {
				player.setPosition(originalPos);// move the player back
			} else {
				// don't move the player
			}
		}
	}

	// Rectangle r is the players global hitbox
	private boolean playerCollision(Rectangle r, int numPlayer) {
		/* Check collisions with students */
		for (Student student : studentList) {
			if (r.intersects(student.getHitboxGlobal())) {
				return true;
			}
		}
		
		for (Sprite furniture : furnitureList) {
			if (r.intersects(furniture.getHitboxGlobal())) {
				return true;
			}
		}
		/* Check collisions with the other player */
		switch (numPlayer) {
		case 1:
			if (r.intersects(this.player2.getHitboxGlobal())) {
				return true;
			}
			break;
		case 2:
			if (r.intersects(this.player1.getHitboxGlobal())) {
				return true;
			}
			break;
		default:
			break;
		}
		/* Check whether player is not inside of the play area */
		if (!(this.playArea.getHitboxGlobal().contains(r))) {
			return true;
		}
		/* Check whether player is too close to the boss */
		Rectangle bossHitboxRange = this.boss.getHitboxGlobal();
		bossHitboxRange.grow(20, 20);
		if (r.intersects(bossHitboxRange)) {
			return true;
		}
		return false;
	}
}

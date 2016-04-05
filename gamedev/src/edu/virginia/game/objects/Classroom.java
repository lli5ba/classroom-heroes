package edu.virginia.game.objects;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.PickedUpItem;
import edu.virginia.engine.display.SoundManager;
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
import edu.virginia.game.managers.StudentManager;

//This class represents a game screen object to be used for levels and hallway scenes.
//This way you can instantiate a GameScreen and add game elements as children.
public class Classroom extends DisplayObjectContainer{
	
	private PlayerManager playerManager = PlayerManager.getInstance();
	private LevelManager levelManager = LevelManager.getInstance();
	private static GameManager gameManager = GameManager.getInstance();
	private StudentManager studentManager = StudentManager.getInstance();
	private TweenJuggler myTweenJuggler = TweenJuggler.getInstance();
	private Player player1;
	private Player player2;
	private Boss boss;
	private GameClock gameClock;
	public static final double SPAWN_INTERVAL = 1500;
	private static boolean hit = false;
	public static int vpcount = 0;
	public static int poisoncount = 5;
	ArrayList<PickedUpItem> vpList = new ArrayList<PickedUpItem>();
	ArrayList<PickedUpItem> poisonList = new ArrayList<PickedUpItem>();
	ArrayList<Student> studentList = new ArrayList<Student>();
	
	public Classroom(String id) {
		super(id, "classroom/classroom-background-" + gameManager.getNumLevel() + ".png");
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
		student0.setPosition(
				this.getWidth()*.5, this.getHeight()*.742);
		this.studentList.add(student0);
		
		this.player1.setPosition(
				this.getWidth()*.08, this.getHeight()*.742);
		
		this.player2.setPosition(
				this.getWidth()*.814, this.getHeight()*.742);
		
		this.boss.setPosition(this.getWidth()*.400, this.getHeight()*.003);
		this.boss.setScaleX(.75);
		this.boss.setScaleY(.75);
		
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
	
	public void spawnVP() {
		if(myTweenJuggler != null) {
			VP vp = new VP("VP", "projectiles/vp0.png", 
					"projectiles/vpsheet.png", "resources/projectiles/vpsheetspecs.txt");

			vp.addEventListener(playerManager, PickedUpEvent.KEY_PICKED_UP);
			vp.addEventListener(playerManager, CollisionEvent.COLLISION);
			Tween tween2 = new Tween(vp, TweenTransitions.LINEAR);
			myTweenJuggler.add(tween2);
			Position pos = generatePosition(400, 20, 1000);
			tween2.animate(TweenableParam.POS_X, 400, pos.getX(), 10000);
			tween2.animate(TweenableParam.POS_Y, 20, pos.getY(), 10000);
			this.vpList.add(vp);
			this.hit = false;
			// FIXME: hit sometimes gives 2 vp; i think due to hitbox??
		}
	}
	
	public void spawnPoison() {
		if(myTweenJuggler != null) {
			//FIXME: sprite sheet not implemented
			Poison poison = new Poison("Poison", "projectiles/poison.png");
			poison.addEventListener(playerManager, PickedUpEvent.KEY_PICKED_UP);
			poison.addEventListener(playerManager, CollisionEvent.COLLISION);
			Tween tween2 = new Tween(poison, TweenTransitions.LINEAR);
			myTweenJuggler.add(tween2);
			Position pos = generatePosition(400, 20, 1000);
			tween2.animate(TweenableParam.POS_X, 400, pos.getX(), 10000);
			tween2.animate(TweenableParam.POS_Y, 20, pos.getY(), 10000);
			this.poisonList.add(poison);
		}
	}

	private void checkVPCollisions(ArrayList<String> pressedKeys) {

		
	}
	
	private void spawnProjectile() {
		if (this.gameClock != null) {
			if (this.gameClock.getElapsedTime() > (SPAWN_INTERVAL)) {
				spawnVP();
				spawnPoison();
				// FIXME: may need to spawn poison at different intervals
				this.gameClock.resetGameClock();
			}
		}
	}
	
	public void openDoor() {
		//TODO: Leandra
	}
	
	@Override
	public void draw(Graphics g){
		super.draw(g); //draws children
		spawnProjectile();
		
		if (poisonList != null) {	
			for(PickedUpItem poison : poisonList) {
				if(poison != null) {
					poison.draw(g);
				}
			}
		}
		
		if(vpList != null) {
			for (PickedUpItem vp : vpList) {
				if (vp != null) {
					vp.draw(g);
				}
			}
		}
		
		
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys){
		super.update(pressedKeys); //updates children
		this.checkVPCollisions(pressedKeys);
		
	}

}

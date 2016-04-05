package edu.virginia.game.objects;

import java.awt.Graphics;
import java.util.ArrayList;

import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.PickedUpItem;
import edu.virginia.engine.display.SoundManager;
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
	private Player player1;
	private Player player2;
	private Boss boss;
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
		Student student0 = new Student("Student0", "0");
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
	
	public void openDoor() {
		//TODO: Leandra
	}
	
	@Override
	public void draw(Graphics g){
		super.draw(g); //draws children
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys){
		super.update(pressedKeys); //updates children
		this.checkVPCollisions(pressedKeys);
		
	}

	private void checkVPCollisions(ArrayList<String> pressedKeys) {
		// TODO Auto-generated method stub
		
	}
	

}

package edu.virginia.game.objects;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.util.Position;
import edu.virginia.game.managers.GameManager;
import edu.virginia.game.managers.LevelManager;
import edu.virginia.game.managers.PlayerManager;

/* this is a student! It is a sprite that is sitting in a chair that can be poisoned and unpoisoned */
public class Student extends AnimatedSprite{
	private static PlayerManager playerManager = PlayerManager.getInstance();
	private static LevelManager levelManager = LevelManager.getInstance();
	private static GameManager gameManager = GameManager.getInstance();
	
	private int maxHealth;
	private int currentHealth;
	private boolean poisoned;
	
	public Student(String id, String styleCode)
	{
		super(id, "student/student-default-" + styleCode + ".png", 
				"student/student-spritesheet-" + styleCode + ".png", 
				"recources/student/student-spritesheet-specs-" + styleCode + ".txt");
		this.numPlayer = numPlayer;
		this.active = true; 
		
		net = new Sprite("net", imageFileName);
		this.addChild(net);
		net.setAlpha(0); 
		this.setPivotPoint(new Position(this.getWidth()/2,this.getHeight()/2));
		
		
	}
}

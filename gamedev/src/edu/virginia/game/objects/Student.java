
package edu.virginia.game.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.util.GameClock;
import edu.virginia.engine.util.Position;
import edu.virginia.game.managers.GameManager;
import edu.virginia.game.managers.LevelManager;
import edu.virginia.game.managers.PlayerManager;

/* this is a student! It is a sprite that is sitting in a chair that can be poisoned and unpoisoned */
public class Student extends AnimatedSprite{
	private static PlayerManager playerManager = PlayerManager.getInstance();
	private static LevelManager levelManager = LevelManager.getInstance();
	private static GameManager gameManager = GameManager.getInstance();
	public static final double DRAIN_INTERVAL = 1500; //FIXME: should depend on what level we are on
	private GameClock healthDrainClock;
	private double maxHealth;
	private double currentHealth;
	private boolean poisoned;
	private boolean dead;
	private StudentHealthBar healthBar;
	
	public Student(String id, String styleCode)
	{
		 /* super(id, "student/student-default-" + styleCode + ".png", 
				"student/student-spritesheet-" + styleCode + ".png", 
				"recources/student/student-spritesheet-specs-" + styleCode + ".txt"); */
		super(id, "student/student-default-" + styleCode + ".png");
		this.maxHealth = 20; //FIXME: should depend on what level we are on
		this.currentHealth = maxHealth;
		this.healthBar = new StudentHealthBar(id + "-healthBar");
		this.addChild(healthBar);
		this.healthBar.setPosition(10, -5); //float over head
		this.dead = false;
		this.poisoned = false;
		healthDrainClock = new GameClock();
		
	}
	
	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
	public double getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(double maxHealth) {
		this.maxHealth = maxHealth;
	}

	public double getCurrentHealth() {
		return currentHealth;
	}
	public void setCurrentHealth(double currHealth) {
		if (currHealth < 0) {
			currHealth = 0; //cannot be less than 0
		}
		this.currentHealth = currHealth;
	}
	public boolean isPoisoned() {
		return poisoned;
	}
	public void setPoisoned(boolean poisoned) {
		if (poisoned == true) { //clock has been running, so need to reset it!
			this.healthDrainClock.resetGameClock(); 
		}
		this.poisoned = poisoned;
	}

	private void drainHealthIfPoisoned(ArrayList<String> pressedKeys) {
		double percentToDrain = 0.2; //FIXME: should depend on what level we are on
		if (this.healthDrainClock != null) {
			if (this.healthDrainClock.getElapsedTime() > (DRAIN_INTERVAL) && 
					this.isPoisoned() && !this.isDead()) {
				double newHealth = this.currentHealth - percentToDrain * this.maxHealth;
				if(newHealth < 0) {
					this.currentHealth = 0;
					this.dead = true;
				} else {
					this.currentHealth = newHealth;
				}
				this.healthBar.setHealthBar(this.currentHealth, this.maxHealth);
				this.healthDrainClock.resetGameClock();
			}
		}
	}
	
	@Override
	public void draw(Graphics g){
		super.draw(g); //draws children
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys){
		super.update(pressedKeys); //updates children
		this.drainHealthIfPoisoned(pressedKeys);
	}

	
}

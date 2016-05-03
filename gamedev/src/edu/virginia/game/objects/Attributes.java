package edu.virginia.game.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.game.managers.PlayerManager;

public class Attributes extends DisplayObjectContainer{

	private PlayerManager playerManager = PlayerManager.getInstance();
	public AttributeModule health;
	public AttributeModule speed;
	public AttributeModule swingSpeed;
	public int numPlayer;
	private ArrayList<String> prevPressedKeys = new ArrayList<String>();
	public String selected;
	
	public Attributes(String id, int numPlayer) {
		super(id);
		numPlayer = this.numPlayer;
		health = new AttributeModule(AttributeModule.HEALTH, numPlayer);
		this.addChild(health);
		this.health.setPosition(0, 0);
		health.setSelected(true);
		selected = AttributeModule.HEALTH;
		
		speed = new AttributeModule(AttributeModule.MOVEMENT_SPEED, numPlayer);
		this.addChild(speed);
		this.speed.setPosition(this.health.getWidth()*1, 0);
		
		swingSpeed = new AttributeModule(AttributeModule.SWING_SPEED, numPlayer);
		this.addChild(swingSpeed);
		this.swingSpeed.setPosition(this.health.getWidth()*2, 0);
	}
	
	public int getNumPlayer() {
		return numPlayer;
	}

	public void setNumPlayer(int numPlayer) {
		this.numPlayer = numPlayer;
		this.speed.setNumPlayer(numPlayer);
		this.health.setNumPlayer(numPlayer);
		this.swingSpeed.setNumPlayer(numPlayer);
	}

	private void navigateAttributes(ArrayList<String> pressedKeys) {
		ArrayList<String> releasedKeys = new ArrayList<String>(this.prevPressedKeys);
		releasedKeys.removeAll(pressedKeys);
		
		if (releasedKeys.contains(this.playerManager.getLeftKey(this.numPlayer))) {
			switch (this.selected) {
			case AttributeModule.HEALTH: //on health, can't go more left
				break;
			case AttributeModule.MOVEMENT_SPEED: //go to health
				this.selectHealth();
				break;
			case AttributeModule.SWING_SPEED: //go to movement
				this.selectSpeed();
				break;
			default:
				break;
			}
		} else if (releasedKeys.contains(this.playerManager.getRightKey(this.numPlayer))) {
			//select 2 players if not currently selected(visible)
			switch (this.selected) {
			case AttributeModule.HEALTH: //on health go to movement
				this.selectSpeed();
				break;
			case AttributeModule.MOVEMENT_SPEED: //go to swing_speed
				this.selectSwingSpeed();
				break;
			case AttributeModule.SWING_SPEED: //can't go right
				break;
			default:
				break;
			}
		} 
		
		this.prevPressedKeys.clear();
		this.prevPressedKeys.addAll(pressedKeys);
		
	}

	public void selectHealth() {
		this.health.setSelected(true);
		this.speed.setSelected(false);
		this.swingSpeed.setSelected(false);
		selected = AttributeModule.HEALTH;
	}
	
	public void selectSpeed() {
		this.health.setSelected(false);
		this.speed.setSelected(true);
		this.swingSpeed.setSelected(false);
		selected = AttributeModule.MOVEMENT_SPEED;
	}
	
	public void selectSwingSpeed() {
		this.health.setSelected(false);
		this.speed.setSelected(false);
		this.swingSpeed.setSelected(true);
		selected = AttributeModule.SWING_SPEED;
	}
	
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);

	}
	
	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		
		//navigate
		if (this.isVisible()) {
			navigateAttributes(pressedKeys);
		}
		
	}

}

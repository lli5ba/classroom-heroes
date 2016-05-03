package edu.virginia.game.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.display.ToastSprite;
import edu.virginia.game.managers.PlayerManager;

public class AttributeModule extends Sprite{
	private PlayerManager playerManager = PlayerManager.getInstance();
	public static final String SWING_SPEED = "swing speed";
	public static final String HEALTH = "health";
	public static final String MOVEMENT_SPEED = "movement speed";
	private ToastSprite upArrow;
	private ToastSprite downArrow;
	private boolean selected;
	private Player player;
	private PlayerHealthBar healthbar;
	private int attrAdditions;
	private int numPlayer;
	private ArrayList<String> prevPressedKeys = new ArrayList<String>();
	
	
	public AttributeModule(String id, int playerNum) {
		super(id, "end-level/attribute-unselected.png");
		upArrow = new ToastSprite("up-arrow", "end-level/up.png");
		this.addChild(upArrow);
		downArrow = new ToastSprite("up-arrow", "end-level/down.png");
		this.addChild(downArrow);
		if(id.equals(HEALTH)) {
			healthbar = new PlayerHealthBar("health", playerNum, true);
			this.addChild(healthbar);
			healthbar.setPosition(this.getWidth() * 0.1, this.getHeight() * 0.4552);
		} else {
			player = new Player("Player1", "player/player1.png", "player/player-spritesheet-1.png",
						"resources/player/player-spritesheet-1-frameInfo.txt", 1);
			if (playerNum == 2){
				player = new Player("Player2", "player/player2.png", "player/player-spritesheet-2.png",
						"resources/player/player-spritesheet-1-frameInfo.txt", 2);
			}
			player.setActive(false);
			this.addChild(player);
			player.setPosition(this.getWidth() * 0.2766, this.getHeight() * 0.3224);
			
		}
		attrAdditions = 0;
		numPlayer = playerNum;
		this.updateDisplay();
		this.selected = false;
	}
	
	@Override
	public void draw(Graphics g) {
		
		super.draw(g);
		
		if (this.isVisible()) {
			Font f = new Font("Dialog", Font.PLAIN, 12);
			if(this.getScaleX() <= .7 && this.getScaleX() > .5) {
				f = new Font("Dialog", Font.PLAIN, 10);
			} else if(this.getScaleX() <= .5 && this.getScaleX() > .3) {
				f = new Font("Dialog", Font.PLAIN, 8);
			}
			g.setFont(f);
			if(this.isSelected()) {
				g.setColor(Color.WHITE);
			} else {
				g.setColor(Color.BLACK);
			}
			String numToDisplay = "";
			if(this.getId().equals(HEALTH)) {
				numToDisplay = "" + this.playerManager.getMaxHealth(numPlayer);
			} else if(this.getId().equals(MOVEMENT_SPEED)) {
				numToDisplay = "" + (int)this.playerManager.getSpeed(numPlayer);
			} else if(this.getId().equals(SWING_SPEED)) {
				numToDisplay = "" + this.playerManager.getSwingSpeed(numPlayer);
			}
			g.drawString(numToDisplay, 
					(int) (this.getxPos() + this.getWidth()*.4322), 
					(int) (this.getyPos() + this.getHeight()*.6621));
			g.setColor(Color.BLACK); //set back for others
		}

	}
	
	public int getNumPlayer() {
		return numPlayer;
	}

	public void setNumPlayer(int numPlayer) {
		this.numPlayer = numPlayer;
		if (this.getId().equals(HEALTH)) {
			this.healthbar.setPlayerNum(numPlayer);
		} else {
			player = new Player("Player1", "player/player1.png", "player/player-spritesheet-1.png",
					"resources/player/player-spritesheet-1-frameInfo.txt", 1);
			if (numPlayer == 2){
				player = new Player("Player2", "player/player2.png", "player/player-spritesheet-2.png",
						"resources/player/player-spritesheet-1-frameInfo.txt", 2);
			}
		}
		this.updateDisplay();
	}

	public int getAttrAdditions() {
		return attrAdditions;
	}

	public void setAttrAdditions(int attrAdditions) {
		this.attrAdditions = attrAdditions;
	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		
		//navigate
		if (this.isVisible() && this.isSelected()) {
			navigateAttribute(pressedKeys);
		}
		
	}
	
	//call when leaving end level screen
	public void storeChanges() {
		if(this.getId().equals(HEALTH)) {
			//is updated already
		} else if(this.getId().equals(MOVEMENT_SPEED)) {
			
		} else if(this.getId().equals(SWING_SPEED)) {
			
		}
	}
	
	//only happens if this module is selected
	private void navigateAttribute(ArrayList<String> pressedKeys) {
		ArrayList<String> releasedKeys = new ArrayList<String>(this.prevPressedKeys);
		releasedKeys.removeAll(pressedKeys);
		
		if (releasedKeys.contains(this.playerManager.getUpKey(this.numPlayer))) {
			this.incrementAttr();
		} else if (releasedKeys.contains(this.playerManager.getDownKey(this.numPlayer))) {
			//select 2 players if not currently selected(visible)
			this.decrementAttr();
		} 
		
		this.prevPressedKeys.clear();
		this.prevPressedKeys.addAll(pressedKeys);
	}

	private void incrementAttr() {
		this.upArrow.play(.5);
		if(this.playerManager.getAttrPoints(numPlayer) > 0) {
			if(this.getId().equals(HEALTH)) {
				//increase health
				this.playerManager.setMaxHealth(this.playerManager.getMaxHealth(numPlayer) + 1, numPlayer);
				this.playerManager.setHealth(this.playerManager.getMaxHealth(numPlayer), numPlayer);
			} else if(this.getId().equals(MOVEMENT_SPEED)) {
				//increase movement speed
				this.playerManager.setSpeed(this.playerManager.getSpeed(numPlayer) + 1, numPlayer);
			} else if(this.getId().equals(SWING_SPEED)) {
				//increase swing speed
				this.playerManager.setSwingSpeed(this.playerManager.getSwingSpeed(numPlayer) + 1, numPlayer);
			}
			this.updateDisplay();
			this.attrAdditions ++;
			this.playerManager.setAttrPoints(this.playerManager.getAttrPoints(numPlayer) - 1, numPlayer);
		}
	}

	private void decrementAttr() {
		this.downArrow.play(.5);
		if(this.attrAdditions > 0) { //they have added attribute points, so they can decrement
			if(this.getId().equals(HEALTH)) {
				//decrease health
				this.playerManager.setMaxHealth(this.playerManager.getMaxHealth(numPlayer) - 1, numPlayer);
				this.playerManager.setHealth(this.playerManager.getMaxHealth(numPlayer), numPlayer);
			} else if(this.getId().equals(MOVEMENT_SPEED)) {
				//decrease movement speed
				this.playerManager.setSpeed(this.playerManager.getSpeed(numPlayer) - 1, numPlayer);
			} else if(this.getId().equals(SWING_SPEED)) {
				//decrease swing speed
				this.playerManager.setSwingSpeed(this.playerManager.getSwingSpeed(numPlayer) - 1, numPlayer);
			}
			this.updateDisplay();
			this.attrAdditions--;
			this.playerManager.setAttrPoints(this.playerManager.getAttrPoints(numPlayer) + 1, numPlayer);
		}
	}

	public void updateDisplay() {
		if(this.getId().equals(HEALTH)) {
			//is updated automatically when added as child
		} else if(this.getId().equals(MOVEMENT_SPEED)) {
			player.animate("right", (this.playerManager.getSpeed(numPlayer)/4));
		} else if(this.getId().equals(SWING_SPEED)) {
			player.animate("netright", this.playerManager.getSwingSpeed(numPlayer));
		}
		
	}
	public void stopAnimation() {
		if(this.getId().equals(MOVEMENT_SPEED)) {
			player.stopAnimation();
		} else if(this.getId().equals(SWING_SPEED)) {
			player.stopAnimation();
		}
	}
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		if (selected == true) {
			this.setImage("end-level/attribute-selected.png");
		} else {
			this.setImage("end-level/attribute-unselected.png");
			//this.stopAnimation();
		}
	}


	
	
	
	
	
}

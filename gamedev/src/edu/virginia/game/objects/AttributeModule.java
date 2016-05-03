package edu.virginia.game.objects;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.display.ToastSprite;
import edu.virginia.game.managers.PlayerManager;

public class AttributeModule extends Sprite{
	private PlayerManager playerManager = PlayerManager.getInstance();
	private static final String SWING_SPEED = "swing speed";
	private static final String HEALTH = "health";
	private static final String MOVEMENT_SPEED = "movement speed";
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
		downArrow = new ToastSprite("up-arrow", "end-level/down.png");
		if(id.equals(HEALTH)) {
			healthbar = new PlayerHealthBar("healthbar", playerNum, true);
		} else {
			if (playerNum == 1) {
				player = new Player("Player1", "player/player1.png", "player/player-spritesheet-1.png",
						"resources/player/player-spritesheet-1-frameInfo.txt", 1);
			} else if (playerNum == 2){
				player = new Player("Player2", "player/player2.png", "player/player-spritesheet-2.png",
						"resources/player/player-spritesheet-1-frameInfo.txt", 2);
			}
			player.setActive(false);
			
		}
		attrAdditions = 0;
		numPlayer = playerNum;
	}
	
	@Override
	public void draw(Graphics g) {
		if (isVisible()) {
			super.draw(g);
		}

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
			this.decrementAttr();
		} else if (releasedKeys.contains(this.playerManager.getDownKey(this.numPlayer))) {
			//select 2 players if not currently selected(visible)
			this.incrementAttr();
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
		}
	}

	public void updateDisplay() {
		if(this.getId().equals(HEALTH)) {
			//is updated automatically when added as child
		} else if(this.getId().equals(MOVEMENT_SPEED)) {
			player.animate("right", this.playerManager.getSpeed(numPlayer));
		} else if(this.getId().equals(SWING_SPEED)) {
			player.animate("netright", this.playerManager.getSwingSpeed(numPlayer));
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
		}
	}


	
	
	
	
	
}

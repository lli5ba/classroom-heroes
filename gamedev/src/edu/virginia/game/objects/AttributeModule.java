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
		numPlayer = PlayerNum;
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
	
	private void navigateAttribute(ArrayList<String> pressedKeys) {
		ArrayList<String> releasedKeys = new ArrayList<String>(this.prevPressedKeys);
		releasedKeys.removeAll(pressedKeys);
		if (releasedKeys.contains(this.playerManager.getUpKey(this.numPlayer))) {
			//select 1 player if not currently selected(visible)
			if(!this.player1bar.isVisible()) {
				this.switchPlayerSelection();
			}
		} else if (releasedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_DOWN))) {
			//select 2 players if not currently selected(visible)
			if(!this.player2bar.isVisible()) {
				this.switchPlayerSelection();
			}
		} else if (releasedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_RIGHT))) {
			
		} else if (releasedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_LEFT))) {
		
		}
		this.prevPressedKeys.clear();
		this.prevPressedKeys.addAll(pressedKeys);
	}

	public void updateDisplay() {
		if(this.getId().equals(HEALTH)) {
			//is updated already
		} else if(this.getId().equals(MOVEMENT_SPEED)) {
			player.animate("right");
			
		} else if(this.getId().equals(SWING_SPEED)) {
			
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

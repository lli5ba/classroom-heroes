package edu.virginia.game.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Sprite;
import edu.virginia.game.managers.GameManager;
import edu.virginia.game.managers.LevelManager;
import edu.virginia.game.managers.PlayerManager;


public class TitleScreen extends DisplayObjectContainer {
	
	
	private PlayerManager playerManager = PlayerManager.getInstance();
	private LevelManager levelManager = LevelManager.getInstance();
	private GameManager gameManager = GameManager.getInstance();
	private Sprite player1bar;
	private Sprite player2bar;
	private ArrayList<String> prevPressedKeys = new ArrayList<String>();
	
	public TitleScreen(String id) {
		super(id, "titlescreen/title-screen-background.png"); //background
		player1bar =  new Sprite(id + "-player1bar", "titlescreen/player1-bar.png");
		player2bar =  new Sprite(id + "-player2bar", "titlescreen/player2-bar.png");
		Sprite playerButtons = new Sprite(id + "-playerbuttons", "titlescreen/title-screen-1-or-2.png");
		this.addChild(player1bar);
		this.addChild(player2bar);
		this.addChild(playerButtons);
		this.player2bar.setVisible(false);
		
		//Do this last
		this.setHeight(gameManager.getGameHeight());
		this.setWidth(gameManager.getGameWidth());
	}
	
	public void switchPlayerSelection() {
		if(player1bar.isVisible()) {
			this.player1bar.setVisible(false);
			this.player2bar.setVisible(true);
		} else {
			this.player2bar.setVisible(false);
			this.player1bar.setVisible(true);
		}
	}

	public void navigateTitleScreen(ArrayList<String> pressedKeys) {
		ArrayList<String> releasedKeys = new ArrayList<String>(this.prevPressedKeys);
		releasedKeys.removeAll(pressedKeys);
		if (releasedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_SPACE))
				|| releasedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_ENTER))) {
			//player is selecting action, so start the game
			//FIXME: or show instruction screen
			if(this.player1bar.isVisible()) {
				this.gameManager.setNumPlayers(1);
			} else {
				this.gameManager.setNumPlayers(2);
			}
			this.gameManager.setActiveGameScene("classroom1");
		}  else if (releasedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_UP))) {
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
	
	@Override
	public void draw(Graphics g) {
		super.draw(g); // draws children
		
	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys); // draws children
		if (this.isVisible()) {
			navigateTitleScreen(pressedKeys);
		}
	}
	

}

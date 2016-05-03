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


public class PauseMenu extends DisplayObjectContainer {
	
	
	private PlayerManager playerManager = PlayerManager.getInstance();
	private LevelManager levelManager = LevelManager.getInstance();
	private GameManager gameManager = GameManager.getInstance();
	private Sprite resumeBar;
	private Sprite exitBar;
	private ArrayList<String> prevPressedKeys = new ArrayList<String>();
	
	public PauseMenu(String id) {
		super(id); //background
		resumeBar =  new Sprite(id + "-resumebar", "pause-menu/select-resume.png");
		exitBar =  new Sprite(id + "-exitbar", "pause-menu/select-exit.png");
		Sprite words = new Sprite(id, "pause-menu/pause-menu-background.png");
		this.addChild(resumeBar);
		this.addChild(exitBar);
		this.addChild(words);
		this.exitBar.setVisible(false);
		
		this.setScaleX(2);
		this.setScaleY(2);
	}
	
	public void switchMenuSelection() {
		if(resumeBar.isVisible()) {
			this.resumeBar.setVisible(false);
			this.exitBar.setVisible(true);
		} else {
			this.exitBar.setVisible(false);
			this.resumeBar.setVisible(true);
		}
	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);
		this.setDrawChildren(visible);
	}
	
	public void navigatePauseMenu(ArrayList<String> pressedKeys) {
		ArrayList<String> releasedKeys = new ArrayList<String>(this.prevPressedKeys);
		releasedKeys.removeAll(pressedKeys);
		if (releasedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_SPACE))
				|| releasedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_ENTER))) {
			if(this.resumeBar.isVisible()) {
				//resume game
				this.setVisible(false);
			} else {
				//exit to menu
				this.gameManager.setActiveGameScene("title");
				this.gameManager.restartGame();
				this.playerManager.resetPlayerStats();
			}
			
		}  else if (releasedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_UP))) {
			//select 1 player if not currently selected(visible)
			if(!this.resumeBar.isVisible()) {
				this.switchMenuSelection();
			}
		} else if (releasedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_DOWN))) {
			//select 2 players if not currently selected(visible)
			if(!this.exitBar.isVisible()) {
				this.switchMenuSelection();
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
			navigatePauseMenu(pressedKeys);
		}
	}
	

}

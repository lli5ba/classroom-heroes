package edu.virginia.game.objects;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.util.Position;
import edu.virginia.game.managers.LevelManager;
import edu.virginia.game.managers.PlayerManager;

public class EndLevelScreen extends DisplayObjectContainer {

	private static final String WIN = "You win!";
	private static final String LOSE_NO_HEALTH = "Out of health! You lose :( ";
	private static final String LOSE_STUDENTS = "All your friends perished! You lose :( ";
	private Sprite highlight;
	private String currentHighlight;
	private String winOrLose;
	private PlayerManager playerManager = PlayerManager.getInstance();
	private LevelManager levelManager = LevelManager.getInstance();
	private int numPlayer;
	private ArrayList<String> prevPressedKeys = new ArrayList<String>();

	public EndLevelScreen(String id, String winOrLose12, int numPlayerEnding) {
		super(id, "end-level/notebook.png"); // notebook
		this.highlight = new Sprite(id + "-highlight", "store/insert-cancel-highlight.png");
		this.winOrLose = winOrLose12;
	}

	public int getNumPlayer() {
		return numPlayer;
	}

	public void setNumPlayer(int numPlayer) {
		this.numPlayer = numPlayer;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		this.setDrawChildren(visible); // don't draw children if store isn't
										// visible
	}


	public void navigateScreen(ArrayList<String> pressedKeys) {
		ArrayList<String> releasedKeys = new ArrayList<String>(this.prevPressedKeys);
		releasedKeys.removeAll(pressedKeys);
		if (releasedKeys.contains(this.playerManager.getPrimaryKey(this.numPlayer))) { 
			
		} else if (releasedKeys.contains(this.playerManager.getSecondaryKey(this.numPlayer))) {

		} else if (releasedKeys.contains(this.playerManager.getUpKey(this.numPlayer))) {
			
		} else if (releasedKeys.contains(this.playerManager.getDownKey(this.numPlayer))) {
			
		} else if (releasedKeys.contains(this.playerManager.getRightKey(this.numPlayer))) {
			
		} else if (releasedKeys.contains(this.playerManager.getLeftKey(this.numPlayer))) {
			
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
			navigateScreen(pressedKeys);
		}
	}
}

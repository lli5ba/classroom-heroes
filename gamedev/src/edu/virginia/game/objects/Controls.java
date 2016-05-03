package edu.virginia.game.objects;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.game.managers.GameManager;

public class Controls extends DisplayObjectContainer {

	KeyboardKey up;
	KeyboardKey down;
	KeyboardKey left;
	KeyboardKey right;
	KeyboardKey secondary;
	KeyboardKey primary;
	private int numPlayer;
	private AnimatedSprite player;
	private GameManager gameManager = GameManager.getInstance();
	private ArrayList<String> prevPressedKeys = new ArrayList<String>();

	public Controls(String id) {
		super(id, "instructions/instructions-background.png"); // background
		// get last character in styleCode
		//numPlayer = Integer.parseInt(id.substring(id.length() - 1)); //id is either 1 or 2
		
		up = new KeyboardKey("up", numPlayer);
		this.up.setScaleX(1.0);
		this.up.setScaleY(1.0);
		this.addChild(up);
		this.up.setPosition(300, 100);
		
		down = new KeyboardKey("down", numPlayer);
		this.down.setScaleX(1.0);
		this.down.setScaleY(1.0);
		this.addChild(down);
		this.down.setPosition(300, 150);
		
		left = new KeyboardKey("left", numPlayer);
		this.left.setScaleX(1.0);
		this.left.setScaleY(1.0);
		this.addChild(left);
		this.left.setPosition(250, 150);
		
		right = new KeyboardKey("right", numPlayer);
		this.right.setScaleX(1.0);
		this.right.setScaleY(1.0);
		this.addChild(right);
		this.right.setPosition(350, 150);
	
		/**
		primary = new KeyboardKey("primary", numPlayer);
		this.primary.setScaleX(1.0);
		this.primary.setScaleY(1.0);
		this.addChild(primary);
		this.primary.setPosition(475, 100);
		
		secondary = new KeyboardKey("secondary", numPlayer);
		this.secondary.setScaleX(1.0);
		this.secondary.setScaleY(1.0);
		this.addChild(secondary);
		this.secondary.setPosition(575, 100);
		**/
		
		this.setHeight(gameManager.getGameHeight());
		this.setWidth(gameManager.getGameWidth());
	}

	public void navigate(ArrayList<String> pressedKeys) {
		ArrayList<String> releasedKeys = new ArrayList<String>(this.prevPressedKeys);
		releasedKeys.removeAll(pressedKeys);
		if (releasedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_SPACE))
				|| releasedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_ENTER))) {
			this.gameManager.setActiveGameScene("classroom1");
		}
		this.prevPressedKeys.clear();
		this.prevPressedKeys.addAll(pressedKeys);
	}

	public void draw(Graphics g) {
		super.draw(g);
	}

	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		if (this.isVisible()) {
			navigate(pressedKeys);
		}
	}
}

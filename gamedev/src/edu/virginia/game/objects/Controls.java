package edu.virginia.game.objects;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.game.managers.GameManager;

public class Controls extends AnimatedSprite {

	KeyboardKey UP;
	KeyboardKey DOWN;
	KeyboardKey LEFT;
	KeyboardKey RIGHT;
	KeyboardKey SECONDARY;
	KeyboardKey PRIMARY;
	private AnimatedSprite player;
	private GameManager gameManager = GameManager.getInstance();
	private ArrayList<String> prevPressedKeys = new ArrayList<String>();

	public Controls(String id) {
		super(id, "instructions/instructions-background.png"); // background

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
		this.gameManager.draw(g);
		UP = new KeyboardKey("up", 1);
	}

	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		if (this.isVisible()) {
			navigate(pressedKeys);
		}
	}
}

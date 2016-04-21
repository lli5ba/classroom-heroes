package edu.virginia.game.objects;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Sprite;
import edu.virginia.game.managers.GameManager;
import edu.virginia.game.managers.LevelManager;
import edu.virginia.game.managers.PlayerManager;

public class Instructions extends DisplayObjectContainer {

	private LevelManager levelManager = LevelManager.getInstance();
	private PlayerManager playerManager = PlayerManager.getInstance();
	private GameManager gameManager = GameManager.getInstance();
	private DisplayObjectContainer antidote;
	private DisplayObjectContainer cheese;
	private Sprite VPicon1;

	public Instructions(String id) {
		super(id);

	}

	public void draw(Graphics g) {
		super.draw(g);
		super.draw(g);
		Font f = new Font("Dialog", Font.BOLD, 20);
		Font h = new Font("Dialog", Font.BOLD, 15);
		g.setFont(f);
		g.drawString("Welcome to the classroom!", 10, 10);
		g.setFont(h);
		g.drawString("Here is your player:", 10, 20);
	}

	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
	}

}
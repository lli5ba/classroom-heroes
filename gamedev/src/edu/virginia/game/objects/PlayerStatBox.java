package edu.virginia.game.objects;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.game.managers.LevelManager;
import edu.virginia.game.managers.PlayerManager;

public class PlayerStatBox extends DisplayObjectContainer {

	private int vp1Stat;
	private int vp2Stat;
	private int health1Stat;
	private int health2Stat;
	private LevelManager levelManager = LevelManager.getInstance();
	private PlayerManager playerManager = PlayerManager.getInstance();

	public PlayerStatBox(String id) {
		super(id);
		this.vp1Stat = vp1Stat;
		this.vp2Stat = vp2Stat;
		this.health1Stat = health1Stat;
		this.health2Stat = health2Stat;
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		Font f = new Font("Dialog", Font.PLAIN, 8);
		g.setFont(f);

		g.drawString("Player 1's Health: " + this.playerManager.getHealth(1), 120, 40);
		g.drawString("Player 1's VP Collected: " + this.levelManager.getVPCollected(1), 110, 50);

		g.drawString("Player 2's Health: " + this.playerManager.getHealth(2), 380, 40);
		g.drawString("Player 2's VP Collected: " + this.levelManager.getVPCollected(2), 370, 50);
	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);

	}

}

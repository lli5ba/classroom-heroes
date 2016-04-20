package edu.virginia.game.objects;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Sprite;
import edu.virginia.game.managers.GameManager;
import edu.virginia.game.managers.LevelManager;
import edu.virginia.game.managers.PlayerManager;

public class PlayerStatBox extends DisplayObjectContainer {

	private LevelManager levelManager = LevelManager.getInstance();
	private PlayerManager playerManager = PlayerManager.getInstance();
	private GameManager gameManager = GameManager.getInstance();
	private DisplayObjectContainer antidote;
	private DisplayObjectContainer cheese;
	private Sprite VPicon1;
	private Sprite VPicon2;

	public PlayerStatBox(String id) {
		super(id);

		this.antidote = new DisplayObjectContainer("antidote", "statbox/soda-icon.png");
		this.antidote.setScaleX(.8);
		this.antidote.setScaleY(.8);
		this.addChild(antidote);
		this.antidote.setPosition(285, 38);

		this.cheese = new DisplayObjectContainer("cheese", "statbox/bomb-icon.png");
		this.cheese.setScaleX(.8);
		this.cheese.setScaleY(.8);
		this.addChild(cheese);
		this.cheese.setPosition(253, 38);

		this.VPicon1 = new Sprite("vp-icon1", "statbox/vp-icon.png");
		this.VPicon1.setScaleX(.8);
		this.VPicon1.setScaleY(.8);
		this.addChild(VPicon1);
		this.VPicon1.setPosition(78, 38);

		if (this.gameManager.getNumPlayers() == 2) {
			this.VPicon2 = new Sprite("vp-icon2", "statbox/vp-icon.png");
			this.VPicon2.setScaleX(.8);
			this.VPicon2.setScaleY(.8);
			this.addChild(VPicon2);
			this.VPicon2.setPosition(450, 38);
		}
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		Font f = new Font("Dialog", Font.BOLD, 9);
		g.setFont(f);

//		g.drawString("Player 1's Health: " + this.playerManager.getHealth(1), 115, 48);
		g.drawString(": " + (this.levelManager.getVPCollected(1)), 93, 48);
		g.drawString(": " + this.playerManager.getNumCheesePuffs(), 270, 48);
		g.drawString(": " + this.playerManager.getNumGingerAle(), 300, 48);
		if (this.gameManager.getNumPlayers() == 2) {
			//g.drawString("Player 2's Health: " + this.playerManager.getHealth(2), 350, 48);
			g.drawString(": " + this.levelManager.getVPCollected(2), 465, 48);
		}
	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
	}

}

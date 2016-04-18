package edu.virginia.game.objects;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.Sprite;
import edu.virginia.game.managers.GameManager;

public class PlayerStat extends Sprite {

	private Sprite halfheart;
	private Sprite wholeheart;
	private Sprite emptyheart;
	private Sprite VPicon1;
	private Sprite VPicon2;
	private GameManager gameManager = GameManager.getInstance();

	public PlayerStat(String id) {
		super(id);

	/**	this.halfheart = new Sprite("half", "statbox/half-heart.png");
		this.addChild(halfheart);

		this.wholeheart = new Sprite("full", "statbox/heart-whole.png");
		this.addChild(wholeheart);

		this.emptyheart = new Sprite("empty", "statbox/heart-empty.png");
		this.addChild(emptyheart);
**/
		this.VPicon1 = new Sprite("vp-icon1", "statbox/vp-icon.png");
		this.VPicon1.setScaleX(.8);
		this.VPicon1.setScaleY(.8);
		this.addChild(VPicon1);
		this.VPicon1.setPosition(78,38);

		if (this.gameManager.getNumPlayers() == 2) {
		this.VPicon2 = new Sprite("vp-icon2", "statbox/vp-icon.png");
		this.VPicon2.setScaleX(.8);
		this.VPicon2.setScaleY(.8);
		this.addChild(VPicon2);
		this.VPicon2.setPosition(450,38);
		}
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
	}

}

package edu.virginia.game.objects;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.Sprite;
import edu.virginia.game.managers.GameManager;
import edu.virginia.game.managers.PlayerManager;

public class PlayerStat extends Sprite {

	private Sprite halfHeart;
	private Sprite wholeHeart;
	private Sprite emptyHeart;
	private double numHeartsTot;
	private double numHearts;
	private double halfHearts;
	private int y;
	private int z;
	private GameManager gameManager = GameManager.getInstance();
	private PlayerManager playerManager = PlayerManager.getInstance();

	public PlayerStat(String id) {
		super(id);

		setHearts(1);
		if (this.gameManager.getNumPlayers() == 2) {
			setHearts(2);
		}
	}

	public void setHearts(int player) {
		numHeartsTot = this.playerManager.getMaxHealth(player) / 2;
		numHearts = this.playerManager.getHealth(player) / 2;
		halfHearts = this.playerManager.getHealth(player) % 2;
		int x = 180;
		if (player == 2) {
			x = 350;
		}
		for (int i = 0; i < numHearts; i++) {
			this.wholeHeart = new Sprite("wholeheart" + i, "statbox/heart-whole.png");
			this.addChild(wholeHeart);
			// System.out.println("heart" + i);
			this.wholeHeart.setPosition(x, 40);
			this.wholeHeart.setScaleX(1.1);
			this.wholeHeart.setScaleY(1.1);
			x += 12;
			numHeartsTot--;
		}

		if (halfHearts == 1) {
			this.halfHeart = new Sprite("half", "statbox/heart-half.png");
			this.addChild(halfHeart);
			this.halfHeart.setPosition(x, 40);
			this.halfHeart.setScaleX(1.1);
			this.halfHeart.setScaleY(1.1);
			x += 12;
			numHeartsTot--;
		}
		for (int i = 0; i < numHeartsTot; i++) {
			this.emptyHeart = new Sprite("empty", "statbox/heart-empty.png");
			this.addChild(emptyHeart);
			this.emptyHeart.setPosition(x, 40);
			this.emptyHeart.setScaleX(1.1);
			this.emptyHeart.setScaleY(1.1);
			x += 12;
		}
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);

	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		this.removeAll();
		setHearts(1);
		if (this.gameManager.getNumPlayers() == 2) {
			setHearts(2);
		}
		// System.out.println("tot: " + numHeartsTot);
		// System.out.println("hearts: " + numHearts);
		// System.out.println("half: " + halfHearts);
	}

	public double getNumHearts() {
		return numHearts;
	}

	public void setNumHearts(double numHearts) {
		this.numHearts = numHearts;
	}

	public double getNumHeartsTot() {
		return numHeartsTot;
	}

	public void setNumHeartsTot(double numHeartsTot) {
		this.numHeartsTot = numHeartsTot;
	}

	public double getHalfHearts() {
		return halfHearts;
	}

	public void setHalfHearts(double halfHearts) {
		this.halfHearts = halfHearts;
	}

}

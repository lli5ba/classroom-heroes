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
	private int x;
	private int y;
	private GameManager gameManager = GameManager.getInstance();
	private PlayerManager playerManager = PlayerManager.getInstance();

	public PlayerStat(String id) {
		super(id);

		numHeartsTot = this.playerManager.getMaxHealth(1) / 2;
		numHearts = this.playerManager.getHealth(1) / 2;
		halfHearts = this.playerManager.getHealth(1) % 2;

		for (int i = 0; i < numHearts; i++) {
			this.wholeHeart = new Sprite("wholeheart" + i, "statbox/heart-whole.png");
			this.addChild(wholeHeart);
			System.out.println("heart" + i);
			this.wholeHeart.setPosition(150 + (12 * i), 40);
			this.wholeHeart.setScaleX(1.0);
			this.wholeHeart.setScaleY(1.0);

			if (halfHearts == 1) {
				this.halfHeart = new Sprite("half", "statbox/heart-half.png");
				this.addChild(halfHeart);
				this.halfHeart.setPosition(150 + (12 * numHearts), 40);
				this.halfHeart.setScaleX(1.0);
				this.halfHeart.setScaleY(1.0);
			}
			if (this.gameManager.getNumPlayers() == 2) {
				for (int j = 0; j < numHearts; j++) {
					this.wholeHeart = new Sprite("wholeheart" + j, "statbox/heart-whole.png");
					this.addChild(wholeHeart);
					System.out.println("heart" + j);
					this.wholeHeart.setPosition(360 + (12 * j), 40);
					this.wholeHeart.setScaleX(1.0);
					this.wholeHeart.setScaleY(1.0);
				}
				if (halfHearts == 1) {
					this.halfHeart = new Sprite("half", "statbox/heart-half.png");
					this.addChild(halfHeart);
					this.halfHeart.setPosition(360 + (12 * numHearts), 40);
					this.halfHeart.setScaleX(1.0);
					this.halfHeart.setScaleY(1.0);
				}

			}
		}
		// this.emptyHeart = new Sprite("empty", "statbox/heart-empty.png");
		// this.addChild(emptyHeart);

	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);

	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
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

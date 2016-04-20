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

		numHeartsTot = playerManager.getMaxHealth(1)/2;
		numHearts = playerManager.getHealth(1)/2;
		halfHearts = playerManager.getHealth(1)%2;
		System.out.println("tot: "+numHeartsTot);
		System.out.println("heart: " + numHearts);
		System.out.println("half: " + halfHearts);
		
		this.halfHeart = new Sprite("half", "statbox/heart-half.png");
		this.addChild(halfHeart);
		//if(halfHearts == 1) {
			
		//}
		
		
		this.wholeHeart = new Sprite("full", "statbox/heart-whole.png");
		this.addChild(wholeHeart);
	//	for(int i=0; )
		
		
		this.emptyHeart = new Sprite("empty", "statbox/heart-empty.png");
		this.addChild(emptyHeart);
		
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		
		}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
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

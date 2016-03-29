package edu.virginia.engine.display;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import edu.virginia.engine.util.Position;

public class Store extends DisplayObjectContainer {
	
	ItemDetail cheesePuffDetail;
	ItemDetail gingerAleDetail;
	boolean buyingItem;
	
	public Store(String id) {
		super(id, "store/background.png"); //store background
		cheesePuffDetail = new ItemDetail("cheesePuffs", "store/cheese-puffs.png", "These are da bomb!", 2);
		gingerAleDetail = new ItemDetail("gingerAle", "store/ginger-ale.png", "Cure your classmates!", 2);
		this.addChild(cheesePuffDetail);
		this.addChild(gingerAleDetail);
		this.gingerAleDetail.setPosition(
				new Position(this.getWidth()*.1, this.getHeight()*.1));
		this.cheesePuffDetail.setPosition(
				new Position(this.getWidth()*.1, this.getHeight()*.1 + this.gingerAleDetail.getHeight()));
		this.buyingItem = false;
		
	}

	@Override
	public void draw(Graphics g){
		super.draw(g); //draws children
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys){
		super.update(pressedKeys); //draws children
		
	}
}

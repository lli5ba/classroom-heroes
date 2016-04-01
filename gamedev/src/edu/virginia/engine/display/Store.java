package edu.virginia.engine.display;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import edu.virginia.engine.util.Position;

public class Store extends DisplayObjectContainer {
	
	private ItemDetail cheesePuffsDetail;
	private ItemDetail gingerAleDetail;
	private Sprite highlight;
	private String currentHighlight;
	private ItemDetail itemToBuy;
	private String primaryKey = KeyEvent.getKeyText(KeyEvent.VK_SPACE);
	private String secondaryKey = KeyEvent.getKeyText(KeyEvent.VK_B);
	private String upKey = KeyEvent.getKeyText(KeyEvent.VK_UP);
	private String downKey = KeyEvent.getKeyText(KeyEvent.VK_DOWN);
	private String rightKey = KeyEvent.getKeyText(KeyEvent.VK_RIGHT);
	private String leftKey = KeyEvent.getKeyText(KeyEvent.VK_LEFT);
	
	public Store(String id, String color) {
		super(id, "store/store-background-" + color + ".png"); //store background
		this.highlight = new Sprite(id + "-highlight", "store/insert-cancel-highlight.png");
		this.highlight.setVisible(false);
		cheesePuffsDetail = new ItemDetail("cheesePuffs", "store/cheese-puffs.png", "These are da bomb!", 2);
		gingerAleDetail = new ItemDetail("gingerAle", "store/ginger-ale.png", "Cure your classmates!", 2);
		Sprite glassSheen = new Sprite("glassSheen", "store/glass-sheen.png");
		this.addChild(cheesePuffsDetail);
		this.addChild(gingerAleDetail);
		this.addChild(glassSheen);
		glassSheen.setPosition(
				this.getWidth()*.1324, this.getHeight()*.2174); //position of top-left corner of inner vending machine
		this.gingerAleDetail.setPosition(
				this.getWidth()*.15, this.getHeight()*.23);
		this.cheesePuffsDetail.setPosition(
				this.getWidth()*.15, this.getHeight()*.23 + this.gingerAleDetail.getHeight()*1.05);
		this.highlightGingerAle(); //highlight the first item
		this.currentHighlight = this.gingerAleDetail.getId();
		
	}
	
	public void highlightCancel(){
		this.currentHighlight = "cancel";
		this.highlight.setVisible(true);
		this.highlight.setPosition(
				new Position(this.getWidth()*.8101, this.getHeight()*.5031));
	}
	
	public void highlightInsert(){
		this.currentHighlight = "insert";
		this.highlight.setVisible(true);
		this.highlight.setPosition(
				new Position(this.getWidth()*.7213, this.getHeight()*.5031));
	}
	
	public void highlightCheesePuffs(){
		this.currentHighlight = "cheesePuffs";
		this.cheesePuffsDetail.setHighlighted(true);
		this.gingerAleDetail.setHighlighted(false);
	}
	
	public void highlightGingerAle(){
		this.currentHighlight = "gingerAle";
		this.cheesePuffsDetail.setHighlighted(false);
		this.gingerAleDetail.setHighlighted(true);
	}
	
	public void stopBuy(){ //navigate back to items
		this.highlightGingerAle();
		this.highlight.setVisible(false);
		this.currentHighlight = "gingerAle";
	}
	
	public void startBuy(){ //navigate to buy sequence
		switch (this.currentHighlight) {
			case "gingerAle":
				this.itemToBuy = this.gingerAleDetail;
				break;
			case "cheesePuffs":
				this.itemToBuy = this.cheesePuffsDetail;
				break;
			default:
				System.out.println("Error in Store startBuy(). Should not happen.");
				break;
		}
		this.cheesePuffsDetail.setHighlighted(false);
		this.gingerAleDetail.setHighlighted(false);
		this.highlightCancel(); //start on cancel, so player doesn't accidentally buy
		this.currentHighlight = "cancel";
	}
	
	public void buyItem(ItemDetail item) {
		//TODO: Leandra
		//check whether enough VP, if so, decrement VP, Increase Inventory, and stopBuy() 
		//if not enough VP then...?
		System.out.println("Item purchesed!");
		
	}
	
	public void navigateStore(ArrayList<String> pressedKeys) {
		if(pressedKeys.contains(this.primaryKey)) { //the player is selecting an action
			switch (this.currentHighlight) {
				case "insert":
					this.buyItem(this.itemToBuy);
					break;
				case "cancel":
					this.stopBuy();
					break;
				default: //An item is selected
					this.startBuy();
					break;
			}
		} else if(pressedKeys.contains(this.secondaryKey)) {
			
		} else if(pressedKeys.contains(this.upKey)) {
			switch (this.currentHighlight) {
				case "cheesePuffs":
					this.highlightGingerAle();
					break;
				default: //do nothing if anything else is highlighted
					break;
			}
		} else if(pressedKeys.contains(this.downKey)) {
			switch (this.currentHighlight) {
				case "gingerAle":
					this.highlightCheesePuffs();
					break;
				default: //do nothing if anything else
					break;
			}
		} else if(pressedKeys.contains(this.rightKey)) {
			switch (this.currentHighlight) {
				case "insert":
					this.highlightCancel();
					break;
				default: //do nothing if anything else
					break;
			}
		} else if(pressedKeys.contains(this.leftKey)) {
			switch (this.currentHighlight) {
				case "cancel":
					this.highlightInsert();
					break;
				default: //do nothing if anything else
					break;
			}
		}
	}


	@Override
	public void draw(Graphics g){
		super.draw(g); //draws children
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys){
		super.update(pressedKeys); //draws children
		navigateStore(pressedKeys);
	}
}

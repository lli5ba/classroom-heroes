package edu.virginia.engine.display;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import edu.virginia.engine.util.Position;

public class Store extends DisplayObjectContainer {
	
	private ItemDetail cheesePuffsDetail;
	private ItemDetail gingerAleDetail;
	private boolean buyingItem; //player is either about to buy an item, or viewing items
	private Sprite highlight;
	private String currentHighlight;
	private ItemDetail itemToBuy;
	
	public Store(String id) {
		super(id, "store/background.png"); //store background
		this.highlight = new Sprite(id + "-highlight", "store/insert-cancel-highlight.png");
		this.highlight.setVisible(false);
		cheesePuffsDetail = new ItemDetail("cheesePuffs", "store/cheese-puffs.png", "These are da bomb!", 2);
		gingerAleDetail = new ItemDetail("gingerAle", "store/ginger-ale.png", "Cure your classmates!", 2);
		this.addChild(cheesePuffsDetail);
		this.addChild(gingerAleDetail);
		this.gingerAleDetail.setPosition(
				new Position(this.getWidth()*.1, this.getHeight()*.1));
		this.cheesePuffsDetail.setPosition(
				new Position(this.getWidth()*.1, this.getHeight()*.1 + this.gingerAleDetail.getHeight()));
		this.highlightGingerAle(); //highlight the first item
		this.currentHighlight = this.gingerAleDetail.getId();
		this.buyingItem = false;
		
	}
	
	public void highlightCancel(){
		this.currentHighlight = "cancel";
		this.highlight.setVisible(true);
		this.highlight.setPosition(
				new Position(this.getWidth()*.1, this.getHeight()*.1));
	}
	
	public void highlightInsert(){
		this.currentHighlight = "insert";
		this.highlight.setVisible(true);
		this.highlight.setPosition(
				new Position(this.getWidth()*.1, this.getHeight()*.1));
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
	
	public void cancelBuy(){ //navigate back to items
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
		this.highlightCancel();
		this.currentHighlight = "cancel";
	}
	
	public void buyItem(ItemDetail item) {
		//TODO: Leandra
		//decrement VP, Increase Inventory
	}
	
	public void navigateStore(ArrayList<String> pressedKeys) {
		if(pressedKeys.contains("primaryKey")) { //the player is selecting an action
			switch (this.currentHighlight) {
				case "insert":
					this.buyItem(this.itemToBuy);
					break;
				case "cancel":
					this.cancelBuy();
					break;
				default: //An item is selected
					this.startBuy();
					break;
			}
		} else if(pressedKeys.contains("secondaryKey")) {
			
		} else if(pressedKeys.contains("UpKey")) {
			switch (this.currentHighlight) {
				case "cheesePuffs":
					this.highlightGingerAle();
					break;
				default: //do nothing if anything else is highlighted
					break;
			}
		} else if(pressedKeys.contains("downKey")) {
			switch (this.currentHighlight) {
				case "gingerAle":
					this.highlightCheesePuffs();
					break;
				default: //do nothing if anything else
					break;
			}
		} else if(pressedKeys.contains("rightKey")) {
			switch (this.currentHighlight) {
				case "insert":
					this.highlightCancel();
					break;
				default: //do nothing if anything else
					break;
			}
		} else if(pressedKeys.contains("leftKey")) {
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
		
	}
}

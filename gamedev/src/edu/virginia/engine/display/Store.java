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
	private ArrayList<String> prevPressedKeys = new ArrayList<String>();
	
	public Store(String id, String color) {
		super(id, "store/store-background-" + color + ".png"); //store background
		this.highlight = new Sprite(id + "-highlight", "store/insert-cancel-highlight.png");
		this.highlight.setVisible(false);
		cheesePuffsDetail = new ItemDetail("cheesePuffs", "store/cheese-puffs.png", "These are da bomb!", 2);
		gingerAleDetail = new ItemDetail("gingerAle", "store/ginger-ale.png", "Cure your classmates!", 2);
		Sprite glassSheen = new Sprite("glassSheen", "store/glass-sheen.png");
		this.addChild(highlight);
		this.addChild(cheesePuffsDetail);
		this.addChild(gingerAleDetail);
		this.addChild(glassSheen);
		glassSheen.setPosition(
				this.getWidth()*.20, this.getHeight()*.218); //position of top-left corner of inner vending machine
		this.gingerAleDetail.setPosition(
				this.getWidth()*.15, this.getHeight()*.23);
		this.cheesePuffsDetail.setPosition(
				this.getWidth()*.15, this.getHeight()*.23 + this.gingerAleDetail.getHeight()*1.05);
		this.highlightGingerAle(); //highlight the first item
		this.currentHighlight = "gingerAle";
		
	}
	
	public void highlightCancel(){
		this.currentHighlight = "cancel";
		this.highlight.setVisible(true);
		this.highlight.setPosition(
				new Position(this.getWidth()*.8125, this.getHeight()*.507));
	}
	
	public void highlightInsert(){
		this.currentHighlight = "insert";
		this.highlight.setVisible(true);
		this.highlight.setPosition(
				new Position(this.getWidth()*.7238, this.getHeight()*.507));
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
		switch (this.currentHighlight) { //switch statements b/c easy to add new items
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
		System.out.println("You bought " + item.getId() + " for " + item.getCost() + " VP");
		this.stopBuy();
		
	}
	
	public void navigateStore(ArrayList<String> pressedKeys) {
		ArrayList<String> releasedKeys = new ArrayList<String>(this.prevPressedKeys);
		releasedKeys.removeAll(pressedKeys);
		//System.out.println("Prev: " +prevPressedKeys.toString() + "   Curr: " + pressedKeys.toString()
		//+ "   Rela: " + releasedKeys.toString());
		if(releasedKeys.contains(this.primaryKey)) { //the player is selecting an action
			System.out.println("pressed space! current highlight: " + this.currentHighlight);
			
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
		} else if(releasedKeys.contains(this.secondaryKey)) {
			
		} else if(releasedKeys.contains(this.upKey)) {
			System.out.println("pressed up! current highlight: " + this.currentHighlight);
			
			switch (this.currentHighlight) {
				case "cheesePuffs":
					this.highlightGingerAle();
					break;
				default: //do nothing if anything else is highlighted
					break;
			}
		} else if(releasedKeys.contains(this.downKey)) {
			System.out.println("pressed down! current highlight: " + this.currentHighlight);
			switch (this.currentHighlight) {
				case "gingerAle":
					this.highlightCheesePuffs();
					break;
				default: //do nothing if anything else
					break;
			}
		} else if(releasedKeys.contains(this.rightKey)) {
			System.out.println("pressed right! current highlight: " + this.currentHighlight);
			switch (this.currentHighlight) {
				case "insert":
					this.highlightCancel();
					break;
				default: //do nothing if anything else
					break;
			}
		} else if(releasedKeys.contains(this.leftKey)) {
			System.out.println("pressed left! current highlight: " + this.currentHighlight);
			switch (this.currentHighlight) {
				case "cancel":
					this.highlightInsert();
					break;
				default: //do nothing if anything else
					break;
			}
		}
		this.prevPressedKeys.clear();
		this.prevPressedKeys.addAll(pressedKeys);
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

package edu.virginia.game.objects;

import java.awt.Graphics;
import java.util.ArrayList;

import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Sprite;

//This class represents a game screen object to be used for levels and hallway scenes.
//This way you can instantiate a GameScreen and add game elements as children.
public class Hallway extends DisplayObjectContainer{

	private Store store;
	private Sprite vendingMachine;
	private Sprite drinkMachine;
	
	public Hallway(String id, String styleCode) {
		super(id, "hallway/hallway-background-" + styleCode + ".png");
		String drinkCode = "0";
		String vendingCode = "0";
		switch (styleCode) {
		case "0":
			drinkCode = "0";
			vendingCode = "0";
			break;
		case "1":
			drinkCode = "1";
			vendingCode = "1";
			break;
		case "2":
			drinkCode = "0";
			vendingCode = "1";
			break;
		case "3":
			drinkCode = "1";
			vendingCode = "0";
			break;
		default:
			break;
		}
		store = new Store(id+"-store", styleCode);
		vendingMachine = new Sprite(id+"-vending-machine", "hallway/vending-machine-" + vendingCode + ".png");
		drinkMachine = new Sprite(id+"-drink-machine", "hallway/drink-machine-" + drinkCode + ".png");
		this.addChild(vendingMachine);
		this.addChild(drinkMachine);
		this.addChild(store);
		
		this.vendingMachine.setPosition(
				this.getWidth()*.4181, this.getHeight()*.2267);
		this.drinkMachine.setPosition(
				this.getWidth()*.4181 + this.vendingMachine.getWidth()*1.1, this.getHeight()*.2267);
		
		
	}

	
	public Hallway(String id, String styleCode, int gameHeight, int gameWidth) {
		super(id, "hallway/hallway-background-" + styleCode + ".png");
		this.setHeight(gameHeight);
		this.setWidth(gameWidth);
		String drinkCode = "0";
		String vendingCode = "0";
		switch (styleCode) {
		case "0":
			drinkCode = "0";
			vendingCode = "0";
			break;
		case "1":
			drinkCode = "1";
			vendingCode = "1";
			break;
		case "2":
			drinkCode = "0";
			vendingCode = "1";
			break;
		case "3":
			drinkCode = "1";
			vendingCode = "0";
			break;
		default:
			break;
		}
		store = new Store(id+"-store", styleCode);
		vendingMachine = new Sprite(id+"-vending-machine", "hallway/vending-machine-" + vendingCode + ".png");
		drinkMachine = new Sprite(id+"-drink-machine", "hallway/drink-machine-" + drinkCode + ".png");
		this.addChild(vendingMachine);
		this.addChild(drinkMachine);
		this.addChild(store);
		
		this.vendingMachine.setPosition(
				this.getWidth()*.4181, this.getHeight()*.2267);
		this.drinkMachine.setPosition(
				this.getWidth()*.4181 + this.vendingMachine.getWidth()*1.1, this.getHeight()*.2267);
	}
	
	@Override
	public void draw(Graphics g){
		super.draw(g); //draws children
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys){
		super.update(pressedKeys); //updates children
	}
}

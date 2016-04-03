package edu.virginia.game.objects;

import java.awt.Graphics;
import java.util.ArrayList;

import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Sprite;
import edu.virginia.game.managers.LevelManager;
import edu.virginia.game.managers.PlayerManager;

//This class represents a game screen object to be used for levels and hallway scenes.
//This way you can instantiate a GameScreen and add game elements as children.
public class Hallway extends DisplayObjectContainer{

	private Store store;
	private Sprite vendingMachine;
	private Sprite drinkMachine;
	private PlayerManager playerManager = PlayerManager.getInstance();
	private LevelManager levelManager = LevelManager.getInstance();
	private ArrayList<String> prevPressedKeys = new ArrayList<String>();
	private Player player1;
	private Player player2;
	
	public Hallway(String id, String styleCode, int gameHeight, int gameWidth) {
		super(id, "hallway/hallway-background-" + styleCode + ".png");
		switch(this.levelManager.getNumPlayers()) {
			case 1: //set player2 invisible	
				
				break;
			case 2: //check two players
			
		}
		this.setHeight(gameHeight);
		this.setWidth(gameWidth);
		/* use "styleCode" to produce different combos of drink and snack machines */
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
		store = new Store(id+"-store", styleCode, 1);
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
	
	public void openStore() {
		
	}
	
	public void closeStore() {
		
	}
	
	public void navigateStore(ArrayList<String> pressedKeys) {
		ArrayList<String> releasedKeys = new ArrayList<String>(this.prevPressedKeys);
		releasedKeys.removeAll(pressedKeys);
		switch(this.levelManager.getNumPlayers()) {
			case 1: //only check one player	
				if(releasedKeys.contains(
						this.playerManager.getPrimaryKey(1))) { //check if player near vending machine
				}
				break;
			case 2: //check two players
				
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
		super.update(pressedKeys); //updates children
	}
}

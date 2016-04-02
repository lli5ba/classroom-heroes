package edu.virginia.engine.display;

//This class represents a game screen object to be used for levels and hallway scenes.
//This way you can instantiate a GameScreen and add game elements as children.
public class Hallway extends DisplayObjectContainer{

	private Store store;
	private Sprite vendingMachine;
	
	public Hallway(String id, String styleCode) {
		super(id, "");
		
		store = new Store(id+"-store.png", styleCode);
		vendingMachine
		
		
		
	}

	
	public Hallway(String id, String backgroundImageFileName, int gameHeight, int gameWidth) {
		super(id, backgroundImageFileName);
		this.setHeight(gameHeight);
		this.setWidth(gameWidth);
	}
	

}

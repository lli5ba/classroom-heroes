package edu.virginia.engine.display;

//This class represents a game screen object to be used for levels and hallway scenes.
//This way you can instantiate a GameScreen and add game elements as children.
public class Hallway extends DisplayObjectContainer{

	public Hallway(String id) {
		super(id);
		
	}

	public Hallway(String id, String imageFileName) {
		super(id, imageFileName);
	}
	
	public Hallway(String id, String backgroundImageFileName, int gameHeight, int gameWidth) {
		super(id, backgroundImageFileName);
		this.setHeight(gameHeight);
		this.setWidth(gameWidth);
	}
	

}

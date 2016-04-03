package edu.virginia.game.objects;

import edu.virginia.engine.display.DisplayObjectContainer;

//This class represents a game screen object to be used for levels and hallway scenes.
//This way you can instantiate a GameScreen and add game elements as children.
public class Classroom extends DisplayObjectContainer{

	public Classroom(String id) {
		super(id);
		
	}

	public Classroom(String id, String imageFileName) {
		super(id, imageFileName);
	}
	
	public Classroom(String id, String imageFileName, int gameHeight, int gameWidth) {
		super(id, imageFileName);
		this.setHeight(gameHeight);
		this.setWidth(gameWidth);
	}
	
	public void openDoor() {
		//TODO: Leandra
	}
	
	

}

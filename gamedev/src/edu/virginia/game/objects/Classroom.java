package edu.virginia.game.objects;

import edu.virginia.engine.display.DisplayObjectContainer;

//This class represents a game screen object to be used for levels and hallway scenes.
//This way you can instantiate a GameScreen and add game elements as children.
public class Classroom extends DisplayObjectContainer{

	int numLevel;
	public Classroom(String id, int numLevel) {
		super(id, "classroom/classroom-background-" + numLevel + ".png");
		this.numLevel = numLevel;
	}
	
	public Classroom(String id, int numLevel, int gameHeight, int gameWidth) {
		super(id, "classroom/classroom-background-" + numLevel + ".png");
		this.numLevel = numLevel;
		this.setHeight(gameHeight);
		this.setWidth(gameWidth);
	}
	
	public void openDoor() {
		//TODO: Leandra
	}
	
	

}

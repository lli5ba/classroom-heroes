package edu.virginia.game.managers;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.tween.Tween;
import edu.virginia.game.objects.Classroom;
import edu.virginia.game.objects.Classroom2;
import edu.virginia.game.objects.Classroom3;
import edu.virginia.game.objects.Hallway;
import edu.virginia.game.objects.Instructions;
import edu.virginia.game.objects.TitleScreen;

/*
 * Singleton class that handles all game details
*/
public class GameManager {

	private static volatile GameManager instance;
	private int gameHeight;
	private int numLevel = 1;

	private int gameWidth;
	private int numPlayers;

	private HashMap<String, DisplayObjectContainer> gameScenes;
	private ArrayList<String> toRemoveQueue;
	private String activeGameScene;

	/****************** Constructors ********************/

	public static GameManager getInstance() {
		if (instance == null) {
			instance = new GameManager();
		}
		return instance;
	}

	public GameManager() {
		instance = this;
		gameHeight = 725;
		gameWidth = 1365;
		numPlayers = 1;
		numLevel = 1;
		setActiveGameScene("title");
		gameScenes = new HashMap<String, DisplayObjectContainer>();
		toRemoveQueue = new ArrayList<String>();

	}

	/*********** Game Details Getters and Setters *********/

	public int getGameHeight() {
		return gameHeight;
	}

	public void setGameHeight(int gameHeight) {
		this.gameHeight = gameHeight;
	}

	public int getGameWidth() {
		return gameWidth;
	}

	public void setGameWidth(int gameWidth) {
		this.gameWidth = gameWidth;
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

	/*********** Level Stats Getters and Setters *********/

	public int getNumLevel() {
		return numLevel;
	}

	public void setNumLevel(int numLevel) {
		this.numLevel = numLevel;
	}

	public String getActiveGameScene() {
		return activeGameScene;
	}

	public void setActiveGameScene(String activeGameScene) {
		this.activeGameScene = activeGameScene;
	}

	public void addGameScene(String name, DisplayObjectContainer scene) {
		this.gameScenes.put(name, scene);
	}

	public void removeGameScene(String name) {
		this.toRemoveQueue.add(name);
	}

	public boolean activeGameSceneIsCreated() {
		return this.gameScenes.containsKey(this.getActiveGameScene());
	}

	/********* Update Frame ********/

	public void update(ArrayList<String> pressedKeys) {
		// update the active game scene
		if (this.gameScenes.containsKey(this.activeGameScene)) {
			if (this.activeGameScene.contains("title")) {
				TitleScreen title = (TitleScreen) this.gameScenes.get(this.activeGameScene);
				if (title != null) {
					title.update(pressedKeys);
				}
			} else if (this.activeGameScene.contains("instructions")) {
				Instructions instruction = (Instructions) this.gameScenes.get(this.activeGameScene);
				if (instruction != null) {
					instruction.update(pressedKeys);
				}
			} else if(this.activeGameScene.contains("hallway")) {
				Hallway hallway = (Hallway) this.gameScenes.get(this.activeGameScene);
				if (hallway != null) {
					hallway.update(pressedKeys);
				}
			} else if (this.activeGameScene.contains("classroom")) {
				if (this.activeGameScene.equals("classroom1")) {
					Classroom classroom = (Classroom) this.gameScenes.get(this.activeGameScene);
					if (classroom != null) {
						classroom.update(pressedKeys);
					}
				}
				if (this.activeGameScene.contains("2")) {
					Classroom2 classroom2 = (Classroom2) this.gameScenes.get(this.activeGameScene);
					if (classroom2 != null) {
						classroom2.update(pressedKeys);
					}
				}
				if (this.activeGameScene.contains("3"))
				{
					Classroom3 classroom3 = (Classroom3) this.gameScenes.get(this.activeGameScene);
					if(classroom3 != null) {
						classroom3.update(pressedKeys);
					}
				}
			}

		}

		// garbage collection
		for (String name : this.gameScenes.keySet()) {
			if (!name.equals(this.activeGameScene)) {
				this.removeGameScene(name);
			}
		}
		
		for (String name : this.toRemoveQueue) {
			this.gameScenes.remove(name);
		}
		this.toRemoveQueue.clear();
		

	}

	public void draw(Graphics g) {
		// update the active game scene
		if (this.gameScenes.containsKey(this.activeGameScene)) {
			if (this.activeGameScene.contains("title")) {
				TitleScreen title = (TitleScreen) this.gameScenes.get(this.activeGameScene);
				if (title != null) {
					title.draw(g);
				}
			} else if (this.activeGameScene.contains("instructions")) {
				Instructions instruction = (Instructions) this.gameScenes.get(this.activeGameScene);
				if (instruction != null) {
					instruction.draw(g);
				}
			} else if (this.activeGameScene.contains("hallway")) {
				Hallway hallway = (Hallway) this.gameScenes.get(this.activeGameScene);
				if (hallway != null) {
					hallway.draw(g);
				}
			} 
			else if(this.activeGameScene.contains("classroom")) {
				if(this.activeGameScene.equals("classroom1"))
				{
					Classroom classroom = (Classroom) this.gameScenes.get(this.activeGameScene);
					if (classroom != null) {
						classroom.draw(g);
					}
				} else if (this.activeGameScene.equals("classroom2")) {
					Classroom2 classroom = (Classroom2) this.gameScenes.get(this.activeGameScene);
					if (classroom != null) {
						classroom.draw(g);
					}
				}
				
				else if (this.activeGameScene.equals("classroom3"))
				{
					Classroom3 classroom = (Classroom3) this.gameScenes.get(this.activeGameScene);
					if(classroom != null) {
						classroom.draw(g);
					}
				}
			}
		}
	}

	public void restartGame() {
		// TODO Auto-generated method stub
		
	}
}

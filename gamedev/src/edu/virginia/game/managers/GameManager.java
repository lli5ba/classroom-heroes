package edu.virginia.game.managers;

/*
 * Singleton class that handles all game details
*/
public class GameManager {

	private static volatile GameManager instance;
	private int gameHeight = 500;
	

	private int gameWidth = 800;
	private int numPlayers;

	/****************** Constructors ********************/

	public static GameManager getInstance() {
		if (instance == null) {
			instance = new GameManager();
		}
		return instance;
	}

	public GameManager() {
			instance = this;
		}
	
	/***********Game Details Getters and Setters *********/

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
}

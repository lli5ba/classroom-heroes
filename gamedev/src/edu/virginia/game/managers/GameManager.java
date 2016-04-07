package edu.virginia.game.managers;

/*
 * Singleton class that handles all game details
*/
public class GameManager {

	private static volatile GameManager instance;
	private int gameHeight;
	private int numLevel = 1;

	private int gameWidth;
	private int numPlayers;
	
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
		gameHeight = 700;
		gameWidth = 1000;
		numPlayers = 1;
		numLevel = 1;
		setActiveGameScene("classroom1");
		
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
}

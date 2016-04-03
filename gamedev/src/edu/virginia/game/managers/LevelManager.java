package edu.virginia.game.managers;


/*
 * Singleton class that handles all the current level (time, what level, etc) details
*/
public class LevelManager {
	private static volatile LevelManager instance;
	int numLevel;
	//Some way to keep track of time
	
	public static LevelManager getInstance(){
               if(instance == null) {
                         instance = new LevelManager();
               }
               return instance;
     }
	 
	public LevelManager() {
		instance = this;
		numLevel = 1; //start on level 1
	}
}

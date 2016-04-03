package edu.virginia.game.managers;

/*
 * Singleton class that handles all the VP and Poison events
*/
public class ProjectileManager {
	private static volatile ProjectileManager instance;
	
	public static ProjectileManager getInstance(){
               if(instance == null) {
                         instance = new ProjectileManager();
               }
               return instance;
     }
	 
	public ProjectileManager() {
		instance = this;
	}
}

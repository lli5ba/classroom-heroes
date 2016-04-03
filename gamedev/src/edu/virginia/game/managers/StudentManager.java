package edu.virginia.game.managers;

import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventListener;

/*
 * Singleton class that handles all the classmate interactions
*/
public class StudentManager implements IEventListener{
	private static volatile StudentManager instance;
	
	public static StudentManager getInstance(){
               if(instance == null) {
                         instance = new StudentManager();
               }
               return instance;
     }
	 
	public StudentManager() {
		instance = this;
	}

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		
	}
}

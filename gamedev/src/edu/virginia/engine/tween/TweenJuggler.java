package edu.virginia.engine.tween;

import java.util.ArrayList;
import java.util.Iterator;


/*
 * Singleton class that handles all of the tweens in the game and updates them
 * appropriately once per frame.
*/
public class TweenJuggler {
	private static volatile TweenJuggler instance;
	ArrayList<Tween> tweens;
	ArrayList<Tween> tweenQueue;
	 public static TweenJuggler getInstance(){
               if(instance == null) {
                         instance = new TweenJuggler();
               }
               return instance;
     }
	 
	public TweenJuggler() {
		instance = this;
		tweens = new ArrayList<Tween>();
		tweenQueue = new ArrayList<Tween>();
	}
	
	public void add(Tween tween) {
		tweenQueue.add(tween);
	}
	
	public ArrayList<Tween> getTweens(){
		return this.tweens;
	}
	
	//invoked every frame by Game, 
	//calls update() on every Tween 
	//and cleans up old complete tweens
	public void nextFrame() { 
		//ArrayList<Tween> toRemove = new ArrayList<Tween>();
		for (Iterator<Tween> iterator = tweens.iterator(); iterator.hasNext();) {
		    Tween t = iterator.next();
		    if (t.isComplete()) {
		    	iterator.remove();
		       // toRemove.add(t);
		    } else {
		    	t.update();
		    }
		}
		for (Tween t :tweenQueue) {
			tweens.add(t);
		}
		tweenQueue.clear();
		/*for (Tween t : tweens) {
		    if (t.isComplete()) {
		        //toRemove.add(t);
		    } else {
		    	t.update();
		    }
		} /*
		for (Tween t : toRemove) {
			tweens.remove(t);
		}*/
	}
}

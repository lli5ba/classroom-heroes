package edu.virginia.lab1test;

import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.display.PickedUpItem;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.engine.tween.Tween;
import edu.virginia.engine.tween.TweenJuggler;
import edu.virginia.engine.tween.TweenTransitions;
import edu.virginia.engine.tween.TweenableParam;
import edu.virginia.engine.tween.TweenEvent;

public class QuestManager implements IEventListener{
	TweenJuggler myTweenJuggler;
	public QuestManager(){
		myTweenJuggler = TweenJuggler.getInstance();
	}
	
	@Override
	public void handleEvent(Event event) {
		if (event.getEventType().equals(PickedUpEvent.KEY_PICKED_UP)) {
			System.out.println("Quest is complete!");
			Sprite pickedUpItem = (PickedUpItem) event.getSource();
			
			System.out.println(pickedUpItem.getId());
			
			if (pickedUpItem.isVisible()) { //has faded out already
				System.out.println("set " + pickedUpItem.getId() + " to invisible");
				pickedUpItem.setVisible(false);
			}
			
			/**Code for zoom and disappear
			 *Tween tween1 = new Tween(pickedUpItem, TweenTransitions.LINEAR);
			myTweenJuggler.add(tween1);
			tween1.animate(TweenableParam.POS_X, pickedUpItem.getxPos(), 175, 1000);
			tween1.animate(TweenableParam.POS_Y, pickedUpItem.getyPos(), 75, 1000);
			tween1.animate(TweenableParam.SCALE_X, 1, 4, 1000);
			tween1.animate(TweenableParam.SCALE_Y, 1, 4, 1000);
			 **/
		} 
		else if (event.getEventType().equals(TweenEvent.TWEEN_COMPLETE_EVENT)) {
			//System.out.println("Tween is complete!");
			PickedUpItem pickedUpItem = (PickedUpItem) event.getSource();
			if (pickedUpItem.getAlpha() < 0.5) { //has faded out already
				pickedUpItem.setVisible(false);
			}
			if (pickedUpItem.isVisible()) {
				Tween tween2 = new Tween(pickedUpItem, TweenTransitions.LINEAR);
				myTweenJuggler.add(tween2);
				tween2.animate(TweenableParam.ALPHA, 1, 0, 1000);
			}
			
			//pickedUpItem.setVisible(false);
		}
		
	}
	

}

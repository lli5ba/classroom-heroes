package edu.virginia.engine.tween;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.events.EventDispatcher;
import edu.virginia.engine.util.GameClock;
import edu.virginia.game.main.PickedUpEvent;

//Object representing on Sprite being tweened in some way. Can have multiple
//TweenParam objects
public class Tween extends EventDispatcher{
	DisplayObject object;
	TweenTransitions transition;
	ArrayList<TweenParam> tweenOps;
	boolean complete;
	GameClock gameClock;
	
	public Tween(DisplayObject object){
		this.object = object;
		
		tweenOps = new ArrayList<TweenParam>();
		complete = false;
	}
	public Tween(DisplayObject object, TweenTransitions transition) {
		this.object = object;
		this.transition = transition;
		tweenOps = new ArrayList<TweenParam>();
		complete = false;
	}
	
	public DisplayObject getObject(){
		return this.object;
	}
	
	public void animate(TweenableParam fieldToAnimate, double startVal, double endVal, double time) {
		this.tweenOps.add(new TweenParam(fieldToAnimate, startVal, endVal, time));
		if(this.gameClock == null) {
			gameClock = new GameClock();
		}
		this.gameClock.resetGameClock();
		this.complete = false;
	}
	public void update() { //invoked once per frame by the TweenJuggler. Updates this tween / DisplayObject
		if(this.gameClock == null) {
			gameClock = new GameClock();
		}
		
		if(!this.tweenOps.isEmpty() && this.gameClock.getElapsedTime() >= Collections.max(this.tweenOps).getTweenTime()){
			completedTween();
		}
		
		for (TweenParam tweenOp : tweenOps) {
			if (tweenOp.getTweenTime() > this.gameClock.getElapsedTime()) {
				double percentDone = 1 - ((tweenOp.getTweenTime() - this.gameClock.getElapsedTime()) / tweenOp.getTweenTime());
				double newValue = (tweenOp.getEndVal() - tweenOp.getStartVal())
						*TweenTransition.applyTransition(this.transition, percentDone)
						+ tweenOp.getStartVal();
				this.setValue(tweenOp.getParamToTween(), 
						newValue);
			}
		}
	}
	
	@Override
	public String toString() {
		return "Tween [transition=" + transition + ", tweenOps=" + tweenOps + ", complete=" + complete + "]";
	}
	private void completedTween() {
		this.object.dispatchEvent(new TweenEvent(TweenEvent.TWEEN_COMPLETE_EVENT, this.object));
		this.complete = true;
	}
	
	public boolean isComplete() {
		return this.complete;
	}

	public void setValue(TweenableParam param, double value) {
		switch (param) {
		case SCALE_X:
			object.setScaleX(value);
			break;
		case SCALE_Y:
			object.setScaleY(value);
			break;
		case POS_X:
			object.setxPos(value);
			break;
		case POS_Y:
			object.setyPos(value);
			break;
		case ALPHA:
			object.setAlpha((float) value);
			break;
		case ROTATION:
			object.setRotationDegrees(value);
			break;
		default:
			break;
		}
	}

}

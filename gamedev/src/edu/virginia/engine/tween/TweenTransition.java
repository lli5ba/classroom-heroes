package edu.virginia.engine.tween;

public class TweenTransition {

	public static double applyTransition(TweenTransitions transitionType, double percentDone) {
		switch (transitionType) {
		case LINEAR:
			return percentDone;
		case EASE_IN_OUT:
			return easeInOut(percentDone);
		default:
			return percentDone;
		}
	}

	private static double easeInOut(double percentDone) {
		return Math.pow(percentDone, percentDone); //(percent done can't )
	}
	


}

package edu.virginia.engine.tween;

public class TweenTransition {

	public static double applyTransition(TweenTransitions transitionType, double percentDone) {
		switch (transitionType) {
		case LINEAR:
			return percentDone;
		case EASE_IN_OUT:
			return easeInOut(percentDone);
		case SPEED_UP:
			return speedUp(percentDone);
		case BOOMERANG:
			return boomerang(percentDone);
		default:
			return percentDone;
		}
	}

	private static double easeInOut(double percentDone) {
		return Math.pow(percentDone, percentDone); //(percent done can't )
	}
	
	private static double speedUp(double percentDone) {
		return 7*Math.pow(percentDone*2, 2);
	}
	
	private static double boomerang(double percentDone) {
		double c = Math.sin(percentDone*(Math.PI))/5;
		//System.out.println(percentDone);
		return c;
	}


}

package edu.virginia.engine.tween;

//object storing information relevant to one parameter being tweened.
public class TweenParam implements Comparable<TweenParam> {
	TweenableParam paramToTween;
	double startVal;
	double endVal;
	double tweenTime;

	public TweenParam(TweenableParam paramToTween, double startVal, double endVal, double tweenTime) {
		super();
		this.paramToTween = paramToTween;
		this.startVal = startVal;
		this.endVal = endVal;
		this.tweenTime = tweenTime;
	}

	public TweenableParam getParamToTween() {
		return paramToTween;
	}

	public void setParamToTween(TweenableParam paramToTween) {
		this.paramToTween = paramToTween;
	}

	public double getStartVal() {
		return startVal;
	}

	public void setStartVal(double startVal) {
		this.startVal = startVal;
	}

	public double getEndVal() {
		return endVal;
	}

	public void setEndVal(double endVal) {
		this.endVal = endVal;
	}

	public double getTweenTime() {
		return tweenTime;
	}

	public void setTweenTime(double tweenTime) {
		this.tweenTime = tweenTime;
	}

	@Override
	public int compareTo(TweenParam other) {
		// TODO Auto-generated method stub
		return Double.compare(this.getTweenTime(), ((TweenParam) other).getTweenTime());
	}

	@Override
	public String toString() {
		return "TweenParam [" + "startVal=" + startVal + ", endVal=" + endVal + ", tweenTime=" + tweenTime
				+ ", paramToTween=" + this.paramToTween + "]";
	}

}

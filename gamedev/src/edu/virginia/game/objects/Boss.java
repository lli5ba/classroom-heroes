package edu.virginia.game.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.tween.Tween;
import edu.virginia.engine.tween.TweenJuggler;
import edu.virginia.engine.tween.TweenTransitions;
import edu.virginia.engine.tween.TweenableParam;
import edu.virginia.engine.util.Position;
import edu.virginia.game.managers.GameManager;
import edu.virginia.game.managers.LevelManager;
import edu.virginia.game.managers.PlayerManager;
import edu.virginia.game.managers.ProjectileManager;
import edu.virginia.game.managers.SoundManager;

public class Boss extends AnimatedSprite {

	private PlayerManager playerManager = PlayerManager.getInstance();
	private LevelManager levelManager = LevelManager.getInstance();
	private GameManager gameManager = GameManager.getInstance();
	private ProjectileManager projectileManager = ProjectileManager.getInstance();
	private TweenJuggler myTweenJuggler = TweenJuggler.getInstance();

	private double lastThrownDegrees;
	private String animation;
	
	
	public Boss(String id, String imageFileName) {
		super(id, imageFileName);
		this.setPivotPoint(new Position(this.getWidth() / 2, this.getHeight() / 2));
	}

	public Boss(String id, String imageFileName, String thisSheetFileName, String specsFileName) {
		super(id, imageFileName, thisSheetFileName, specsFileName);
		this.setPivotPoint(new Position(this.getWidth() / 2, this.getHeight() / 2));
	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
	}
	
	public void rotateBoss(double angle){
		int throwSpeed = 6;
		if (angle > 0 && angle < 60) {
			this.animateOnce("tossdownright", throwSpeed);
		} else if (angle > 60 && angle < 120) {
			this.animateOnce("tossdown", throwSpeed);
		} else if (angle > 120 && angle < 180) {
			this.animateOnce("tossdownleft", throwSpeed);
		}
	}
	

	public double getLastThrownDegrees() {
		return lastThrownDegrees;
	}

	public void setLastThrownDegrees(double lastThrownDegrees) {
		this.lastThrownDegrees = lastThrownDegrees;
	}

	public String getAnimation() {
		return animation;
	}

	public void setAnimation(String animation) {
		this.animation = animation;
	}

	public VP spawnVP() {
		if (myTweenJuggler != null) {
			VP vp = new VP("VP");
			vp.setCenterPos(this.getCenterPos());
			vp.addEventListener(projectileManager, EventTypes.PICKUP_VP.toString());
			vp.addEventListener(playerManager, EventTypes.PICKUP_VP.toString());
			Tween tween2 = new Tween(vp, TweenTransitions.LINEAR);
			myTweenJuggler.add(tween2);
			Position pos = generatePosition("vp", vp.getxPos(), vp.getyPos(), 1000);
			tween2.animate(TweenableParam.POS_X, vp.getxPos(), pos.getX(), 20000);
			tween2.animate(TweenableParam.POS_Y, vp.getyPos(), pos.getY(), 20000);
			return vp;
		}
		return null;
	}



	public Poison spawnPoison() {
		if (myTweenJuggler != null) {
			// FIXME: sprite sheet not implemented
			Poison poison = new Poison("Poison");
			poison.setCenterPos(this.getCenterPos());
			poison.addEventListener(projectileManager, EventTypes.PICKUP_POISON.toString());
			poison.addEventListener(playerManager, EventTypes.PICKUP_POISON.toString());
			Tween tween2 = new Tween(poison, TweenTransitions.LINEAR);
			myTweenJuggler.add(tween2);
			Position pos = generatePosition("posion", poison.getxPos(), poison.getyPos(), 1000);
			tween2.animate(TweenableParam.POS_X, poison.getxPos(), pos.getX(), 25000);
			tween2.animate(TweenableParam.POS_Y, poison.getyPos(), pos.getY(), 25000);
			return poison;
		}
		return null;
	}
	
	/** Generates random position on semi-circle for spawning poison/VP **/
	public Position generatePosition(String vpOrPoison, double centerx, double centery, double radius) {
		double ang_min = (0);
		double ang_max = (Math.PI);
		Random rand1 = new Random();
		double d = ang_min + rand1.nextDouble() * (ang_max - ang_min);
		// System.out.println("d: " + d);
		// if vp is thrown, give boss right direction to turn
		// FIXME
		this.rotateBoss(Math.toDegrees(d));
		double x = centerx + radius * Math.cos(d);
		double y = centery + radius * Math.sin(d);
		return new Position(x, y);
	}

	/**
	public void floryan(String position) {
		if (boss != null) {
			if (position.equals("tossdown")) {
				boss.setyPos(this.getHeight());
				boss.setxPos(-(this.getWidth() / 4.0));
				boss.setWidth(this.getHeight());
				boss.setHeight(this.getWidth());
				boss.animate("tossdown");
				boss.setAnimation("tossdown");
			} else if (position.equals("tossdownright")) {
				boss.setxPos((this.getUnscaledWidth() * this.getScaleX()));
				boss.setyPos(0);
				boss.setWidth(this.getWidth());
				boss.setHeight(this.getHeight());
				boss.animate("tossdownright");
				boss.setAnimation("tossdownright");
			} else if (position.equals("tossdownleft")) {
				boss.setxPos(-(this.getUnscaledWidth() * this.getScaleX()));
				boss.setyPos(0);
				boss.setWidth(this.getWidth());
				boss.setHeight(this.getHeight());
				boss.animate("tossdownleft");
				boss.setAnimation("tossdownleft");
			}
		}
	}
**/

}

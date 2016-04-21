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

	private int vpSpeed;
	private int poisonSpeed;
	private double lastThrownDegrees;
	private String animation;
	private TweenTransitions tweenTypeX;
	private TweenTransitions tweenTypeY;

	public Boss(String id, String imageFileName, String thisSheetFileName, String specsFileName, TweenTransitions tx, TweenTransitions ty) {
		super(id, imageFileName, thisSheetFileName, specsFileName);
		this.setPivotPoint(new Position(this.getWidth() / 2, this.getHeight() / 2));
		this.tweenTypeX = tx;
		this.tweenTypeY = ty;
		if (this.gameManager.getNumLevel() == 1) {
			this.poisonSpeed = 20000;
			this.vpSpeed = 17000;
		} else if (this.gameManager.getNumLevel() == 2) {
			this.poisonSpeed = 17500;
			this.vpSpeed = 18000;
		} else if (this.gameManager.getNumLevel() == 3) {
			this.poisonSpeed = 16000;
			this.vpSpeed = 18000;
		}
		
	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
	}
	
	public void rotateBoss(double angle, int tweenSpeed){
		int throwSpeed = 200000/tweenSpeed;
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
			Position pos = generatePosition("vp", vp.getxPos(), vp.getyPos());
			if (pos == null) {
				return null;
			}
			tween2.animate(TweenableParam.POS_X, vp.getxPos(), pos.getX(), this.vpSpeed);
			tween2.animate(TweenableParam.POS_Y, vp.getyPos(), pos.getY(), this.vpSpeed);
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
			Tween tween1 = new Tween(poison, tweenTypeX);
			myTweenJuggler.add(tween1);
			Tween tween2 = new Tween(poison, tweenTypeY);
			myTweenJuggler.add(tween2);
			Position pos = generatePosition("posion", poison.getxPos(), poison.getyPos());
			if (pos == null) {
				return null;
			}
			tween1.animate(TweenableParam.POS_X, poison.getxPos(), pos.getX(), this.poisonSpeed);
			tween2.animate(TweenableParam.POS_Y, poison.getyPos(), pos.getY(), this.poisonSpeed);
			return poison;
		}
		return null;
	}
	
	/** Generates random position on semi-circle for spawning poison/VP 
	 * Returns null if projectile should not spawn **/
	
	public Position generatePosition(String vpOrPoison, double centerx, double centery) {
		double radius = this.gameManager.getGameWidth();
		double ang_min = (0);
		double ang_max = (Math.PI);
		Random rand1 = new Random();
		double d = ang_min + rand1.nextDouble() * (ang_max - ang_min);
		double angleDeg = Math.toDegrees(d);
		
		/* smokebomb logics */
		if (this.levelManager.getSmokebombList() != null) {
			for (Smokebomb bomb : this.levelManager.getSmokebombList())
				if (bomb.isExploding()) {
					int bombRangeDeg = 10; //range of bomb on either side
					double bombXPos = bomb.getxPosGlobal();
					double bombYPos = bomb.getyPosGlobal();
					double centerAngleDeg = this.calcAngleFromCenterGlobal(bombXPos, bombYPos);
					//if angle is in line of the bomb, then regenerate the angle
					while (angleDeg > centerAngleDeg - bombRangeDeg && angleDeg < centerAngleDeg + bombRangeDeg) {
						//calc a new position for VP
						if (vpOrPoison.equals("vp")) {
							d = ang_min + rand1.nextDouble() * (ang_max - ang_min);
							angleDeg = Math.toDegrees(d);
						} else { //return null and don't spawn a Poison
							return null;
						}
						
					}
			}
		}
		
		//use corresponding tweenSpeed in the rotateBoss method to control 
		//animation speed
		if(vpOrPoison.equals("vp")) {
			this.rotateBoss(Math.toDegrees(d), this.vpSpeed);
		} else {
			this.rotateBoss(Math.toDegrees(d), this.poisonSpeed);
		}
		double x = centerx + radius * Math.cos(d);
		double y = centery + radius * Math.sin(d);
		
		return new Position(x, y);
	}
	
	//angle in degrees (0 degrees is at 3 o'clock and it goes Clockwise)
	public double calcAngleFromCenterGlobal(double xPos, double yPos) {
		double angleRad = Math.atan2(xPos - this.getCenterPosGlobal().getX(), 
				this.getCenterPosGlobal().getY() - yPos); 
		double angleDeg = (Math.toDegrees(angleRad) + 270.0) % 360.0;
		return angleDeg;
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

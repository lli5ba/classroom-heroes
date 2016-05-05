package edu.virginia.game.objects;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.GameEvent;
import edu.virginia.engine.tween.Tween;
import edu.virginia.engine.tween.TweenJuggler;
import edu.virginia.engine.tween.TweenTransitions;
import edu.virginia.engine.tween.TweenableParam;
import edu.virginia.engine.util.Position;
import edu.virginia.game.managers.GameManager;
import edu.virginia.game.managers.LevelManager;
import edu.virginia.game.managers.PlayerManager;
import edu.virginia.game.managers.ProjectileManager;
import edu.virginia.game.managers.StudentManager;

public class Controls extends DisplayObjectContainer {
	public static final String[] CARDINAL_DIRS = new String[] { "up", "down", "left", "right" };
	private PlayerManager playerManager = PlayerManager.getInstance();
	private TweenJuggler myTweenJuggler = TweenJuggler.getInstance();
	private ProjectileManager projectileManager = ProjectileManager.getInstance(); 
	private StudentManager studentManager = StudentManager.getInstance();
	private LevelManager levelManager = LevelManager.getInstance();
	private GameManager gameManager = GameManager.getInstance();
	KeyboardKey up;
	KeyboardKey down;
	KeyboardKey left;
	KeyboardKey right;
	KeyboardKey secondaryCure;
	KeyboardKey secondaryBomb;
	KeyboardKey primaryCure;
	KeyboardKey primaryBomb;
	private int numPlayer;
	private AnimatedSprite Upplayer;
	private Player Downplayer;
	private AnimatedSprite Leftplayer;
	private AnimatedSprite Rightplayer;
	private ArrayList<String> prevPressedKeys = new ArrayList<String>();
	private NavButtonIcon contButton;
	private AnimatedSprite notebook;
	private Sprite notebook2;
	private boolean turnPage;
	private Player player;
	private Student student;
	private VP vp;
	private Poison poison;
	

	public Controls(String id) {
		super(id, "instructions/wood-grain-background.png"); // background
		
		this.notebook2 = new Sprite("notebook", "notebook/default.png");
		this.addChild(notebook2);
		
		this.notebook = new AnimatedSprite("notebook", "notebook/default.png",
				"notebook/notebook-spritesheet.png", "resources/notebook/notebook-spritesheet.txt");
		this.notebook.setHeight(gameManager.getGameHeight());
		this.notebook.setWidth(gameManager.getGameWidth());
		
		this.turnPage = false;
		
		// get last character in styleCode
		this.numPlayer = Integer.parseInt(id.substring(id.length() - 1)); //id is either 1 or 2
		//this.numPlayer = 1;
		
		if(this.numPlayer == 1) {
			this.Upplayer = new AnimatedSprite("Upplayer", "player/player1.png", "player/player-spritesheet-1.png",
				"resources/player/player-spritesheet-1-frameInfo.txt");
		} else {
			this.Upplayer = new AnimatedSprite("Upplayer", "player/player2.png", "player/player-spritesheet-2.png",
					"resources/player/player-spritesheet-1-frameInfo.txt");
		}
		this.Upplayer.setScaleX(.8);
		this.Upplayer.setScaleY(.8);
		this.Upplayer.setPosition(400, 140);
		this.Upplayer.animate("up", .3);
		this.addChild(Upplayer);
		
		up = new KeyboardKey("up", numPlayer);
		this.up.setScaleX(1.0);
		this.up.setScaleY(1.0);
		this.addChild(up);
		this.up.setPosition(400, 175);

		if (this.numPlayer == 1) {
			this.Downplayer = new Player("Downplayer", "player/player1.png", "player/player-spritesheet-1.png",
				"resources/player/player-spritesheet-1-frameInfo.txt", 1);
		} else {
			this.Downplayer = new Player("Downplayer", "player/player2.png", "player/player-spritesheet-2.png",
					"resources/player/player-spritesheet-1-frameInfo.txt", 2);
		}
		this.Downplayer.setScaleX(.8);
		this.Downplayer.setScaleY(.8);
		this.Downplayer.setPosition(400, 235);
		this.Downplayer.animate("down", .3);
		this.addChild(Downplayer);

		down = new KeyboardKey("down", numPlayer);
		this.down.setScaleX(1.0);
		this.down.setScaleY(1.0);
		this.addChild(down);
		this.down.setPosition(400, 205);
	
		if (this.numPlayer == 1) {
			this.Leftplayer = new AnimatedSprite("Leftplayer", "player/player1.png", "player/player-spritesheet-1.png",
					"resources/player/player-spritesheet-1-frameInfo.txt");
		} else {
			this.Leftplayer = new AnimatedSprite("Leftplayer", "player/player2.png", "player/player-spritesheet-2.png",
					"resources/player/player-spritesheet-1-frameInfo.txt");
		}
		
		this.Leftplayer.setScaleX(.8);
		this.Leftplayer.setScaleY(.8);
		this.Leftplayer.setPosition(335, 205);
		this.Leftplayer.animate("left", .3);
		this.addChild(Leftplayer);

		left = new KeyboardKey("left", numPlayer);
		this.left.setScaleX(1.0);
		this.left.setScaleY(1.0);
		this.addChild(left);
		this.left.setPosition(370, 205);
		
		if (this.numPlayer == 1) {
			this.Rightplayer = new AnimatedSprite("Rightplayer", "player/player1.png", "player/player-spritesheet-1.png",
				"resources/player/player-spritesheet-1-frameInfo.txt");
		} else {
			this.Rightplayer = new AnimatedSprite("Rightplayer", "player/player2.png", "player/player-spritesheet-2.png",
					"resources/player/player-spritesheet-1-frameInfo.txt");
		}
		this.Rightplayer.setScaleX(.8);
		this.Rightplayer.setScaleY(.8);
		this.Rightplayer.setPosition(465, 205);
		this.Rightplayer.animate("right", .3);
		this.addChild(Rightplayer);
		
		right = new KeyboardKey("right", numPlayer);
		this.right.setScaleX(.9);
		this.right.setScaleY(.9);
		this.addChild(right);
		this.right.setPosition(430, 205);
	
		student = new Student(id, "0", "left");
		this.addChild(student);
		student.addEventListener(studentManager, EventTypes.CURE_STUDENT.toString());
		student.setPosition(this.getWidth()*.4, this.getHeight()*.39);
		this.student.setScaleX(0.8);
		this.student.setScaleY(0.8);
		this.student.setDrainInterval(1500);
		this.student.setPercentToDrain(0.01);
		this.student.setPoisoned(true);
		this.student.animateOnce("fallleft");
		this.addChild(student);
		
		secondaryCure = new KeyboardKey("secondary", numPlayer);
		this.secondaryCure.setScaleX(1.0);
		this.secondaryCure.setScaleY(1.0);
		this.addChild(secondaryCure);
		this.secondaryCure.setPosition(200, 153);
		
		primaryCure = new KeyboardKey("primary", numPlayer);
		this.primaryCure.setScaleX(1.0);
		this.primaryCure.setScaleY(1.0);
		this.addChild(primaryCure);
		this.primaryCure.setPosition(250, 153);
		
		secondaryBomb = new KeyboardKey("secondary", numPlayer);
		this.secondaryBomb.setScaleX(1.0);
		this.secondaryBomb.setScaleY(1.0);
		this.addChild(secondaryBomb);
		this.secondaryBomb.setPosition(200, 228);
		
		primaryBomb = new KeyboardKey("primary", numPlayer);
		this.primaryBomb.setScaleX(1.0);
		this.primaryBomb.setScaleY(1.0);
		this.addChild(primaryBomb);
		this.primaryBomb.setPosition(250, 228);
		
		/* continue button */
		this.contButton = new NavButtonIcon(NavButtonIcon.CONTINUE, 
				true, this.playerManager.getKey("third", numPlayer));
		this.addChild(contButton);
		this.contButton.setPosition(this.getWidth() * .78, this.getHeight() * .88);
		

		if (this.numPlayer == 1) {
			this.player = new Player("player", "player/player1.png", "player/player-spritesheet-1.png",
				"resources/player/player-spritesheet-1-frameInfo.txt", 1);
		} else {
			this.player = new Player("player", "player/player2.png", "player/player-spritesheet-2.png",
					"resources/player/player-spritesheet-1-frameInfo.txt", 2);
		}
		this.player.setScaleX(0.8);
		this.player.setScaleY(0.8);
		this.player.setPosition(this.getWidth() * .5, this.getHeight() * .19);
		this.player.addEventListener(playerManager, EventTypes.POISON_PLAYER.toString());
		this.addChild(player);
		
		this.poison = this.spawnPoison(this.getWidth() * .56, this.getHeight() * .38);
		this.poison.animate("poison");
		this.poison.setScaleX(0.8);
		this.poison.setScaleY(0.8);
		
		this.vp = this.spawnVP(this.getWidth() * .48, this.getHeight() * .38);
		this.vp.animate("vp");
		this.vp.setScaleX(0.8);
		this.vp.setScaleY(0.8);

		
		this.setDrawChildren(true);
		this.setHeight(gameManager.getGameHeight());
		this.setWidth(gameManager.getGameWidth());
	}

	public void navigate(ArrayList<String> pressedKeys) {
		ArrayList<String> releasedKeys = new ArrayList<String>(this.prevPressedKeys);
		releasedKeys.removeAll(pressedKeys);
		if (releasedKeys.contains(this.playerManager.getKey("third", this.numPlayer))) {
			if (this.numPlayer == 1 && this.gameManager.getNumPlayers() == 2) {
				//this.gameManager.setActiveGameScene("controls2");
				this.turnPage = true;
				this.notebook.animateOnce("turn", 4);
				this.setDrawChildren(false);
			}
			else if (this.numPlayer == 2 || 
					(this.numPlayer == 1 && this.gameManager.getNumPlayers() == 1) ) {
				this.gameManager.setActiveGameScene("classroom1");  //move on
			}
		}
	}

	public void draw(Graphics g) {
		super.draw(g);
		if (this.notebook != null && this.turnPage) {
			this.notebook.draw(g);
			Font f = new Font("Dialog", Font.BOLD, 100);
			g.setFont(f);
			g.drawString("Controls", 30, 85);
		}
		if (!this.notebook.isPlaying() && !this.turnPage) {
			Font f = new Font("Dialog", Font.BOLD, 100);
			g.setFont(f);
			g.drawString("Controls", 30, 85);
			f = new Font("Dialog", Font.PLAIN, 50);
			g.setFont(f);
			g.drawString("Player " + this.numPlayer, 445, 195);
			
			Font h = new Font("Dialog", Font.BOLD, 18);
			g.setFont(h);
			drawString(g, "Cure Student\n(when nearby)", 440, 420);
			g.drawString("Swing net", 590, 420);
			g.drawString("+", 560, 540);
			g.drawString("Aim", 490, 590);
			g.drawString("Throw Smoke Bomb", 580, 590);
		}
		
		// smokebomb
		this.levelManager.drawBombs(g);
	}
	
	private void drawString(Graphics g, String text, int x, int y) {
		double inc = 0;
        for (String line : text.split("\n")) {
            g.drawString(line, x, y += inc);
            inc += g.getFontMetrics().getHeight();
        }
    }

	public void checkSwitch(ArrayList<String> pressedKeys) {
		if (this.turnPage && !this.notebook.isPlaying()){
			this.gameManager.setActiveGameScene("controls2");
		} else {
			this.notebook.update(pressedKeys);
		}
		
	}

	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		if (this.isVisible()) {
			if (!this.turnPage)
				navigate(pressedKeys);
			this.checkSwitch(pressedKeys);
		}
		
		this.checkVPCollisions(pressedKeys);
		this.checkPoisonCollisions(pressedKeys);
		this.checkStudentCollisions(pressedKeys);
		this.updatePlayer(pressedKeys, this.player);
		this.aimThrowSmokeBomb(pressedKeys, this.player);
		// smokebomb
		this.levelManager.removeCompleteBombs(pressedKeys);
		
		if (myTweenJuggler != null) {
			myTweenJuggler.nextFrame();
		}

		/* to calculate releasedKeys for use in moving player */
		this.prevPressedKeys.clear();
		this.prevPressedKeys.addAll(pressedKeys);
	}
	
	/* collision detection and movement for players */

	public void updatePlayer(ArrayList<String> pressedKeys, Player player) {
		if (player != null && player.getNetHitbox() != null) {
			if (player.isActive()) {
				this.moveSpriteCartesianAnimate(pressedKeys, player);
			}
			// if there are no keys being pressed, and sprite is walking, then
			// stop the animation
			if (pressedKeys.isEmpty() && player.isPlaying()
					&& Arrays.asList(CARDINAL_DIRS).contains(player.getCurrentAnimation())) {
				player.stopAnimation();
			}

		}
	}
	
	private void checkVPCollisions(ArrayList<String> pressedKeys) {
			if (player.getNetHitboxGlobal().intersects(this.vp.getHitboxGlobal()) 
					&& !vp.isPickedUp()) {
				vp.dispatchEvent(new GameEvent(EventTypes.PICKUP_VP.toString(), vp));
			}
	}
	
	private void checkPoisonCollisions(ArrayList<String> pressedKeys) {
		if (player.collidesWithGlobal(poison) && !poison.isPickedUp()) {
			poison.dispatchEvent(new GameEvent(EventTypes.PICKUP_POISON.toString(), poison));
			this.player.dispatchEvent(new GameEvent(EventTypes.POISON_PLAYER.toString(), this.player));
		}
		// Check all poison collisions with each player's net
		if (player.getNetHitboxGlobal().intersects(poison.getHitboxGlobal()) && !poison.isPickedUp()) {
			poison.dispatchEvent(new GameEvent(EventTypes.PICKUP_POISON.toString(), poison));
		}

	}

	private void checkStudentCollisions(ArrayList<String> pressedKeys) {
		int distToCure = 30; // how close you need to be to the student
		if (player.inRangeGlobal(student, distToCure) && student.isPoisoned()
				&& this.playerManager.getNumGingerAle() > 0 && !student.isDead()
				&& pressedKeys.contains(this.playerManager.getSecondaryKey(1))) {
			student.dispatchEvent(new GameEvent(EventTypes.CURE_STUDENT.toString(), student));
		} 
	}
	
	public VP spawnVP(double x, double y) {
		VP vp = new VP("VP");
		vp.setPosition(x, y);
		vp.addEventListener(projectileManager, EventTypes.PICKUP_VP.toString());
		vp.addEventListener(playerManager, EventTypes.PICKUP_VP.toString());
		this.addChild(vp);
		return vp;
	}
	
	public Poison spawnPoison(double x, double y) {
		Poison poison = new Poison("Poison");
		poison.setPosition(x, y);
		poison.addEventListener(projectileManager, EventTypes.PICKUP_POISON.toString());
		poison.addEventListener(playerManager, EventTypes.PICKUP_POISON.toString());
		this.addChild(poison);
		return poison;
	}
	
	public void aimThrowSmokeBomb(ArrayList<String> pressedKeys, Player player) {
		ArrayList<String> releasedKeys = new ArrayList<String>(this.prevPressedKeys);
		releasedKeys.removeAll(pressedKeys);
		/*
		 * Make sure this is not null. Sometimes Swing can auto cause an extra
		 * frame to go before everything is initialized
		 */
		if (!pressedKeys.contains(this.playerManager.getSecondaryKey(player.getNumPlayer()))) {
			player.setSmokebombVisible(false);
		}
		if (player != null && player.getNetHitbox() != null
				&& pressedKeys.contains(this.playerManager.getSecondaryKey(player.getNumPlayer()))
				&& this.playerManager.getNumCheesePuffs() > 0) {
			/*
			 * update player's position depending on key pressed
			 */
			player.moveSmokebomb(player.getDirection());
			player.setSmokebombDir(player.getDirection());
			if (pressedKeys.contains(this.playerManager.getUpKey(player.getNumPlayer()))
					&& pressedKeys.contains(this.playerManager.getRightKey(player.getNumPlayer()))) {
				String dir = "up"; // right
				player.setDirection(dir);
				player.setDefaultImage(dir);
				player.moveSmokebomb(dir + "right");
				player.setSmokebombDir(dir + "right");

			} else if (pressedKeys.contains(this.playerManager.getUpKey(player.getNumPlayer()))
					&& pressedKeys.contains(this.playerManager.getLeftKey(player.getNumPlayer()))) {
				String dir = "up"; // left
				player.setDirection(dir);
				player.setDefaultImage(dir);
				player.moveSmokebomb(dir + "left");
				player.setSmokebombDir(dir + "left");
			} else if (pressedKeys.contains(this.playerManager.getDownKey(player.getNumPlayer()))
					&& pressedKeys.contains(this.playerManager.getRightKey(player.getNumPlayer()))) {
				String dir = "down"; // right
				player.setDirection(dir);
				player.setDefaultImage(dir);
				player.moveSmokebomb(dir + "right");
				player.setSmokebombDir(dir + "right");
			} else if (pressedKeys.contains(this.playerManager.getDownKey(player.getNumPlayer()))
					&& pressedKeys.contains(this.playerManager.getLeftKey(player.getNumPlayer()))) {
				String dir = "down"; // left
				player.setDirection(dir);
				player.setDefaultImage(dir);
				player.moveSmokebomb(dir + "left");
				player.setSmokebombDir(dir + "left");
			} else if (pressedKeys.contains(this.playerManager.getUpKey(player.getNumPlayer()))) {
				String dir = "up";
				player.setDirection(dir);
				player.setDefaultImage(dir);
				player.moveSmokebomb(dir);
				player.setSmokebombDir(dir);
			} else if (pressedKeys.contains(this.playerManager.getDownKey(player.getNumPlayer()))) {
				String dir = "down";
				player.setDirection(dir);
				player.setDefaultImage(dir);
				player.moveSmokebomb(dir);
				player.setSmokebombDir(dir);
			} else if (pressedKeys.contains(this.playerManager.getLeftKey(player.getNumPlayer()))) {
				String dir = "left";
				player.setDirection(dir);
				player.setDefaultImage(dir);
				player.moveSmokebomb(dir);
				player.setSmokebombDir(dir);
			} else if (pressedKeys.contains(this.playerManager.getRightKey(player.getNumPlayer()))) {
				String dir = "right";
				player.setDirection(dir);
				player.setDefaultImage(dir);
				player.moveSmokebomb(dir);
				player.setSmokebombDir(dir);
			}
			if (releasedKeys.contains(this.playerManager.getPrimaryKey(player.getNumPlayer()))) {
				int bombSpeed = 1000;
				/* throw puffbag */
				double range = player.getHeight() * 5;
				// a global position, will be the final position of the
				// smokebomb
				Position candidatePos = Smokebomb.generatePosition(player.getSmokebombDir(), range,
						player.getSmokebombPos().getX(), player.getSmokebombPos().getY());
				// Position finalPos = correctSmokebombPos(candidatePos);
				Position finalPos = candidatePos;
				Smokebomb bomb = new Smokebomb("bomb", (int) finalPos.getX(), (int) finalPos.getY());
				bomb.setScaleX(player.getScaleXGlobal());
				bomb.setScaleY(player.getScaleYGlobal());
				bomb.setCenterPos(player.getSmokebombPos());
				Tween tween = new Tween(bomb, TweenTransitions.LINEAR);
				myTweenJuggler.add(tween);
				tween.animate(TweenableParam.POS_X, (int) bomb.getxPos(), (int) finalPos.getX(), bombSpeed);
				tween.animate(TweenableParam.POS_Y, (int) bomb.getyPos(), (int) finalPos.getY(), bombSpeed);

				/* add to list */
				this.levelManager.addSmokebomb(bomb);
				player.setSmokebombVisible(false);
				player.dispatchEvent(new GameEvent(EventTypes.THROW_SMOKEBOMB.toString(), player));
			}
		}
	}

	

	public void moveSpriteCartesianAnimate(ArrayList<String> pressedKeys, Player player) {
		ArrayList<String> releasedKeys = new ArrayList<String>(this.prevPressedKeys);
		releasedKeys.removeAll(pressedKeys);
		double speed = this.playerManager.getSpeed(player.getNumPlayer());
		/*
		 * Make sure this is not null. Sometimes Swing can auto cause an extra
		 * frame to go before everything is initialized
		 */
		Position originalPos = new Position(player.getxPos(), player.getyPos());

		if (player != null && player.getNetHitbox() != null
				&& !pressedKeys.contains(this.playerManager.getSecondaryKey(player.getNumPlayer()))) {
			/*
			 * update player's position depending on key pressed
			 */

			if (pressedKeys.contains(this.playerManager.getUpKey(player.getNumPlayer()))) {

				player.setyPos(player.getyPos() - speed);
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), this));

				if (!player.isPlaying() || player.getCurrentAnimation() != "up") {
					player.animate("up", speed / 2);
				}
				player.setDirection("up");

			}
			if (pressedKeys.contains(this.playerManager.getDownKey(player.getNumPlayer()))) {

				player.setyPos(player.getyPos() + speed);
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), this));
				if (!player.isPlaying() || player.getCurrentAnimation() != "down") {
					player.animate("down", speed / 2);
				}
				player.setDirection("down");
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), player));

			}
			if (pressedKeys.contains(this.playerManager.getLeftKey(player.getNumPlayer()))) {
				player.setxPos(player.getxPos() - speed);
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), this));

				if (!player.isPlaying() || player.getCurrentAnimation() != "left") {
					player.animate("left", speed / 2);
				}
				player.setDirection("left");
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), player));

			}
			if (pressedKeys.contains(this.playerManager.getRightKey(player.getNumPlayer()))) {

				player.setxPos(player.getxPos() + speed);
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), this));

				if (!player.isPlaying() || player.getCurrentAnimation() != "right") {
					player.animate("right", speed / 2);
				}
				player.setDirection("right");
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), player));

			}
			if (releasedKeys.contains(this.playerManager.getPrimaryKey(player.getNumPlayer()))) {
				String currentDir = player.getDirection();
				// Until we have combined net and walking animation, net
				// animation overrides walking animation
				if (player.isPlaying() && !player.getCurrentAnimation().contains("net")) {
					// System.out.println("STOPPING\n");
					player.stopAnimation();
				}

				player.animateOnceLock("net" + currentDir, this.playerManager.getSwingSpeed(player.getNumPlayer()));
				player.dispatchEvent(new GameEvent(EventTypes.SWING_NET.toString(), player));
			}

			// this revises the net animation mid-swing
			// if direction changes and animateOnce net sequence is playing
			if (player.isPlaying() && player.getCurrentAnimation().contains("net")
					&& !player.getCurrentAnimation().contains(player.getDirection())) {
				player.setCurrentAnimation("net" + player.getDirection());
			}
			// System.out.println("position: " + player.getHitboxGlobal().getX()
			// + ", " + player.getHitboxGlobal().getY());

			// FIXME: check for collisions
			if (playerCollision(player.getHitboxGlobal(), player.getNumPlayer())) {
				player.setPosition(originalPos);// move the player back
			} else {
				// don't move the player
			}
		}
	}

	// Rectangle r is the players global hitbox
	private boolean playerCollision(Rectangle r, int numPlayer) {
		/* Check collisions with student */
		if (r.intersects(this.student.getHitboxGlobal())) {
			return true;
		}

		/* Check whether player is not inside of the play area */
		if (!(this.getHitboxGlobal().contains(r))) {
			return true;
		}
		
		return false;
	}

	public void resetStats(){
		this.playerManager.setHealth(this.playerManager.getMaxHealth(1), 1);
		if(this.gameManager.getNumPlayers() == 2) {
			this.playerManager.setHealth(this.playerManager.getMaxHealth(2), 2);
		}
		this.levelManager.clearStats();
	}
}

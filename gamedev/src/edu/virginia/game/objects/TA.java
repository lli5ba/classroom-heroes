package edu.virginia.game.objects;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.display.ToastSprite;
import edu.virginia.game.managers.GameManager;
import edu.virginia.game.managers.PlayerManager;

public class TA extends ToastSprite {

	private PlayerManager playerManager = PlayerManager.getInstance();
	private GameManager gameManager = GameManager.getInstance();
	
	public TA(String id, String imageFileName) {
		super(id, imageFileName);

		this.setVisible(false);
	}

	public TA(String id) {
		super(id, "tas/rock.png");

		this.setVisible(false);
	}

	public void draw(Graphics g) {
		super.draw(g);
	}

	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
	}

	public void appear(double seconds) {
		Random rand = new Random();
		String tafile = null;
		int r = rand.nextInt(4) + 1;
		// System.out.println(r);
		if (r > 0 && r <= 1) {
			tafile = "tas/david.png";
		} else if (r > 1 && r <= 2) {
			tafile = "tas/nick.png";
		} else if (r > 2 && r <= 3) {
			tafile = "tas/rock.png";
		} else if (r > 3 && r <= 4) {
			tafile = "tas/qian.png";
		}
		this.setImage(tafile);
		this.play(seconds); // becomes visible for x seconds
	}

	public void doAction(int player) {
		// randomize effect on game
		Classroom currentScene = (Classroom) this.gameManager.getCurrentScene();
		Random r = new Random();
		int action = r.nextInt(24) + 1;
		if(action > 0 && action <= 3) {
			//decrease health by 1
			System.out.println("decreasing health");
			currentScene.displayHearts(player);
			if(player == 1) {
				this.playerManager.setHealth(this.playerManager.getHealth(1)-1, 1);
			} else if(player == 2) {
				this.playerManager.setHealth(this.playerManager.getHealth(2)-1, 2);
			}
		} else if(action > 3 && action <= 6) {
			//decrease inventory by 1
			System.out.println("Decreasing inventory");
			currentScene.getHighlightBox();
			this.playerManager.setNumCheesePuffs(this.playerManager.getNumCheesePuffs()-1);
			this.playerManager.setNumGingerAle(this.playerManager.getNumGingerAle()-1);
			
		} else if(action > 6 && action <= 12) {
			//cure all students
			currentScene.cureAllStudents();
			System.out.println("Cure all students");
		} else if(action > 12 && action <= 18) {
			//inventory all increases by two
			currentScene.getHighlightBox();
			this.playerManager.setNumCheesePuffs(this.playerManager.getNumCheesePuffs()+2);
			this.playerManager.setNumGingerAle(this.playerManager.getNumGingerAle()+2);
		
		} else if(action > 18 && action <= 24) {
			//Player health is full
			currentScene.displayHearts(player);
			this.playerManager.setHealth(this.playerManager.getMaxHealth(1), 1);
			this.playerManager.setHealth(this.playerManager.getMaxHealth(2), 2);
		}
	}
}

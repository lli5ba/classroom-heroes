package edu.virginia.game.objects;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.display.ToastSprite;

public class TA extends ToastSprite {

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
			tafile = "tas/weimer.png";
			// FIXME: qian here
		}
		this.setImage(tafile);
		this.play(seconds); // becomes visible for x seconds
	}

	public void doAction() {
		// randomize effect on game
	}
}

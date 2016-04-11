package edu.virginia.game.objects;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.util.GameClock;
import edu.virginia.game.managers.LevelManager;
import edu.virginia.game.managers.PlayerManager;

public class Timer extends DisplayObjectContainer {

	private GameClock clock;

	public Timer(String id) {
		super(id);
		this.clock = clock;
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		Font f = new Font("Dialog", Font.PLAIN, 8);
		g.setFont(f);


	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);

	}

}

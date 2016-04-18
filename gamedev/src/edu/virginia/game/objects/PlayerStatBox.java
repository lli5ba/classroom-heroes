package edu.virginia.game.objects;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.game.managers.GameManager;
import edu.virginia.game.managers.LevelManager;
import edu.virginia.game.managers.PlayerManager;

public class PlayerStatBox extends DisplayObjectContainer {


	private LevelManager levelManager = LevelManager.getInstance();
	private PlayerManager playerManager = PlayerManager.getInstance();
	private GameManager gameManager = GameManager.getInstance();
	private DisplayObjectContainer antidote;
	
	public PlayerStatBox(String id) {
		super(id);
		
		this.antidote = new DisplayObjectContainer("antidote", "store/ginger-ale.png");
		this.antidote.setWidth(this.getWidth());
		this.antidote.setHeight(this.getHeight());
		this.addChild(antidote);
		this.antidote.setPosition(130, 60);
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		Font f = new Font("Dialog", Font.BOLD, 9);
		g.setFont(f);

		g.drawString("Player 1's Health: " + this.playerManager.getHealth(1), 120, 40);
		g.drawString("Player 1's VP Collected: " + this.levelManager.getVPCollected(1), 110, 50);
		//g.drawImage(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9)
		g.drawString("Antidotes: " + this.playerManager.getNumGingerAle(), 130, 60);
		if (this.gameManager.getNumPlayers() == 2) {
			g.drawString("Player 2's Health: " + this.playerManager.getHealth(2), 380, 40);
			g.drawString("Player 2's VP Collected: " + this.levelManager.getVPCollected(2), 370, 50);
		}
	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);

	}

}

package edu.virginia.game.objects;

import java.util.ArrayList;
import java.util.Arrays;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.util.Position;
import edu.virginia.game.managers.GameManager;
import edu.virginia.game.managers.LevelManager;
import edu.virginia.game.managers.PlayerManager;

public class Boss extends AnimatedSprite {

	private PlayerManager playerManager = PlayerManager.getInstance();
	private LevelManager levelManager = LevelManager.getInstance();
	private GameManager gameManager = GameManager.getInstance();

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

}

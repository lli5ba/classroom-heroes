package edu.virginia.game.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Sprite;
import edu.virginia.game.managers.PlayerManager;

//This class represents an itemdetail to be displayed in the store
public class ItemDetail extends DisplayObjectContainer {
	private Sprite itemIcon;
	private String description;
	private int cost;
	private boolean highlighted;
	private Sprite highlight;
	private boolean grey;
	private String fileName;

	public ItemDetail(String iconId, String iconImageFileName, String description, int cost) {
		super(iconId, "store/item-detail-background.png");
		this.highlighted = true;
		// this.setWidth(width);
		// this.setHeight(height);
		// this.setAlpha(0);
		this.grey = false;
		this.description = description;
		this.cost = cost;
		this.fileName = iconImageFileName;
		this.itemIcon = new Sprite(iconId, iconImageFileName + ".png");
		this.addChild(itemIcon);
		this.itemIcon.setHeight(this.getHeight());
		this.itemIcon.setWidth(this.getHeight());
		this.itemIcon.setPosition(0, 0);
		highlight = new Sprite(iconId + "-highlight", "store/item-icon-highlight.png");
		this.highlight.setHeight(this.getHeight());
		this.highlight.setWidth(this.getHeight());
		this.addChild(highlight);
		this.highlight.setVisible(false);

	}

	private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		Font f = new Font("Dialog", Font.PLAIN, 12);
		g.setFont(f);
		g.setColor(Color.white);
		this.drawString(g, description, (int) (this.getxPos() + this.itemIcon.getWidth() * 1.1),
				(int) (this.getyPos() + this.itemIcon.getHeight() * .25));
		this.drawString(g, "Cost: " + cost + " VP", (int) (this.getxPos() + this.itemIcon.getWidth() * 1.1),
				(int) (this.getyPos() + this.itemIcon.getHeight() * .75));
		g.setColor(Color.black);

	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);

	}

	public void setGrey(boolean isGrey){
		this.grey = isGrey;
		if (isGrey) {
			this.itemIcon.setImage(this.fileName + "-grey.png");
		} else {
			this.itemIcon.setImage(this.fileName + ".png");
		}
	}
	
	public boolean isGrey() {
		return this.grey;
	}
	public boolean isHighlighted() {
		return highlighted;
	}

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
		this.highlight.setVisible(highlighted);
	}

	public String getDescription() {
		return description;
	}

	public int getCost() {
		return cost;
	}

}
package edu.virginia.engine.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

//This class represents an itemdetail to be displayed in the store
public class ItemDetail extends DisplayObjectContainer{

	private Sprite itemIcon;
	private String description;
	private int cost;
	private boolean highlighted;
	private Sprite highlight;
	
	public ItemDetail(String iconId, String iconImageFileName, 
			String description, int cost) {
		super(iconId + "-detail", "store/item-detail-background.png");
		this.highlighted = true;
		//this.setWidth(width);
		//this.setHeight(height);
		//this.setAlpha(0);
		
		this.description = description;
		this.cost = cost;
		this.itemIcon = new Sprite(iconId, iconImageFileName);
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
	
	@Override
	public void draw(Graphics g){
		super.draw(g);
		Font f = new Font("Dialog", Font.PLAIN, 12);
		g.setFont(f);
		g.setColor(Color.white);
		g.drawString(description, (int)(this.getxPos() + this.itemIcon.getWidth()*1.1), 
				(int)(this.getyPos() + this.itemIcon.getHeight()*.25));
		g.drawString("Cost: " + cost + " VP",(int)(this.getxPos() + this.itemIcon.getWidth()*1.1), 
				(int)(this.getyPos() + this.itemIcon.getHeight()*.75));
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		
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
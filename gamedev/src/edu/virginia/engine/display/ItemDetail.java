package edu.virginia.engine.display;

//This class represents an itemdetail to be displayed in the store
public class ItemDetail extends DisplayObjectContainer{

	private Sprite itemIcon;
	private String description;
	private int cost;
	private boolean highlighted;
	
	public ItemDetail(String id, String iconId, 
			String iconImageFileName, String description, int cost, int width, int height) {
		super(id);
		this.setWidth(width);
		this.setHeight(height);
		this.setAlpha(0);
		this.itemIcon = new Sprite(iconId, iconImageFileName);
		this.description = description;
		this.cost = cost;
		this.addChild(itemIcon);
		this.highlighted = false;
		this.itemIcon.setHeight(height*.5);
		this.itemIcon.setHeight(height*.5);
		this.itemIcon.setPosition(height*.25, height*.25);
		
	}

	public ItemDetail(String id, String imageFileName, 
			String iconId, String iconImageFileName, String description, int cost, int width, int height) {
		super(id, imageFileName);
		this.setWidth(width);
		this.setHeight(height);
		this.setAlpha(0);
		this.itemIcon = new Sprite(iconId, iconImageFileName);
		this.description = description;
		this.cost = cost;
		this.addChild(itemIcon);
		this.highlighted = false;
		this.itemIcon.setHeight(height*.5);
		this.itemIcon.setHeight(height*.5);
		this.itemIcon.setPosition(height*.25, height*.25);
	}

	

}
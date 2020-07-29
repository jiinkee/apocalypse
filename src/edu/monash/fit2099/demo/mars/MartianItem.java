package edu.monash.fit2099.demo.mars;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Item;

public class MartianItem extends Item{

	public MartianItem(String name, char displayChar, boolean portable) {
		super(name, displayChar, portable);
	}
	
	public void addAction(Action action) {
		this.allowableActions.add(action);
	}
	
	/**
	 * Getter for heal point of the item.
	 * 
	 * @return an integer which represents the heal point of the item
	 */
	public int getHealPoint() {
		return 0;
	}
}

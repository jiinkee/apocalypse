package edu.monash.fit2099.interfaces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.ZombieCapability;

/**
 * This interface provides the ability to add methods to Item, without modifying code in the engine,
 * or downcasting references in the game.  
 * 
 * @author Tan Wei Li
 * @author Kee Pei Jiin
 */
public interface ItemInterface {
	
	/**
	 * A default method that retrieves the heal point of the item.
	 * Only Food has its own heal point, the other items will have heal point of 0.
	 * 
	 * @return an integer which represents the heal point of the item
	 */
	default int getHealPoint() {
		return 0;
	};
	
	
	/**
	 * A default method that returns an unmodifiable list of Action.
	 * By default returns an empty list of Action.
	 * Can be overridden in subclasses which need to perform condition
	 * checking using Actor or GameMap before returning the list of actions allowable
	 * for the item.
	 * 
	 * @param actor	actor that we're interested in, i.e. owner of the list of possible actions
	 * @param map	game map the actor is currently on
	 * @return 		an unmodifiable list of possible Action that can be carried out by the actor
	 */
	default List<Action> getAllowableActions(Actor actor, GameMap map) {
		return Collections.unmodifiableList(new ArrayList<Action>());
	};
	
	/**
	 * A default method that check if the item is in a Player's inventory.
	 * 
	 * @param actor	actor that we're interested in, i.e. owner of the inventory
	 * @param map	game map the actor is currently on
	 * @return 		true if item is in a Player's inventory, and false otherwise.
	 */	
	default Boolean isInPlayerInventory(Actor actor, GameMap map) {
		if ((actor.getInventory().contains(this)) & (actor.hasCapability(ZombieCapability.PLAYER))) {
			return true;
		} 
		return false;
	}
		
}

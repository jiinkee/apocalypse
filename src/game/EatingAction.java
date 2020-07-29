package game;

import java.util.Objects;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * A class which represents an action to eat food.
 *
 *@author Tan Wei Li
 */
public class EatingAction extends Action {
	/**An item which represents the food*/
	private Item food;
	
	/**
	 * Constructor. Create an EatingAction instance with the food 
	 * arguments as Item attribute.
	 * @param food Food to be eaten.
	 * @throws NullPointerException if food is null
	 */
	public EatingAction(Item food) throws NullPointerException{
		Objects.requireNonNull(food);
		this.food = food;
	}
	/**
	 * If player eats the food, remove the food from the player inventory
	 * and add heal points to player. If Human eats the food, remove
	 * the food from the ground at location of the Human and add heal
	 * points to the Human. 
	 * Return a description of the eating action to be displayed to user.
	 *
	 * @param actor The actor performing the action
	 * @param map The map the actor is on
	 * @return a description of the action suitable for feedback in the UI
	 * @throws NullPointerException if actor or map argument is null
	 */
	@Override
	public String execute(Actor actor, GameMap map) throws NullPointerException{
		Objects.requireNonNull(actor);
		Objects.requireNonNull(map);
	
		if (actor.hasCapability(ZombieCapability.PLAYER)) {
			actor.removeItemFromInventory(food);
		} else {
			map.locationOf(actor).removeItem(food);
		}
		int healPoint = food.getHealPoint();
		actor.heal(healPoint);
		return menuDescription(actor);
	}

	/**
	 * A string describing the action suitable for displaying in the UI menu.
	 *
	 * @param actor The actor performing the action.
	 * @return a String, e.g. "Player eat the food"
	 * @throws NullPointerException if actor is null
	 */
	@Override
	public String menuDescription(Actor actor) throws NullPointerException{
		Objects.requireNonNull(actor);
		return actor + " eats the " + food;
	}
}

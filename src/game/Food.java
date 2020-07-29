package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Represents food in the Zombie game world.
 * 
 * @author Kee Pei Jiin
 * @author Tan Wei Li
 *
 */
public class Food extends PortableItem {
	/**A constant that represents the health point that will be added to the Human or Player that eat the food.*/
	private static final int HEAL_POINT = 10;
	
	/**
	 * Constructor.
	 * Food is the only item that has the capability of EDIBLE.
	 */
	public Food() {
		super("food", '^');
		addCapability(ZombieCapability.EDIBLE);
	}
	
	/**
	 * Getter for heal point of the food.
	 * 
	 * @return an integer which represents the heal point of the food
	 */
	@Override
	public int getHealPoint() {
		return HEAL_POINT;
	}
	
	/**
	 * Return a list consisting of EatingAction only if the actor is Player and has
	 * the food in his inventory. This is to make sure only Player can
	 * perform EatingAction for the food and the food must be picked up before 
	 * it is able to be eaten.
	 * 
	 * @return an unmodifiable list of Action
	 */
	public List<Action> getAllowableActions(Actor actor, GameMap map) {
		List<Action> action = new ArrayList<Action>();
		if (this.isInPlayerInventory(actor, map)) {
			action.add(new EatingAction(this));
			return Collections.unmodifiableList(action);
		}
		return Collections.unmodifiableList(action);
	}
	
}


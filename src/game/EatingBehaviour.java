package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * A class that generates an EatingBehaviour if the current Actor who has
 * less than half of his hit point, is standing in a location which has a 
 * food.
 * 
 * @author Tan Wei Li
 */
public class EatingBehaviour implements Behaviour {

	/**
	 * Returns an EatingAction if the current Actor has hit point of 
	 * value less than half of his maximum hit points and is standing in
	 *  a location which has a food, else return null.
	 * 
	 * @param actor the Actor enacting the behaviour
	 * @param map the map that actor is currently on
	 * @return an Action, or null if no EatingAction is possible
	 * 
	 * @throws NullPointerException if actor or map argument is null
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) throws NullPointerException {
		Objects.requireNonNull(actor);
		Objects.requireNonNull(map);
		
		if (actor.hitPoints() > (actor.maxHitPoints()/2)) {
			return null;
		}
		
		List<Item> itemAtActorLocation = new ArrayList<Item>();
		itemAtActorLocation = map.locationOf(actor).getItems();
		
		if (itemAtActorLocation.isEmpty()) {
			return null;
		} else {
			for (Item item: itemAtActorLocation) {
				if (item.hasCapability(ZombieCapability.EDIBLE)) {
					return new EatingAction(item);
				}
			}
		}
		return null;
	}
}

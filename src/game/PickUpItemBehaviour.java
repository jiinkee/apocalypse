package game;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.PickUpItemAction;

/**
 * A class that generates an PickUpItemAction if the current Actor is standing
 * on a location which has weapon.
 * 
 * @author Tan Wei Li
 * 
 */
public class PickUpItemBehaviour implements Behaviour {
	
	/**
	 * Returns a PickUpItemAction to pick up a weapon, if there is
	 * weapon at the actor's location.
	 * If there is no weapon at the actor's location, returns null.
	 * 
	 * @param actor the Actor enacting the behaviour
	 * @param map the map that actor is currently on
	 * @return an Action, or null if no PickUpItemAction is possible
	 * @throws NullPointerException if actor or map argument is null
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) throws NullPointerException {
		Objects.requireNonNull(actor);
		Objects.requireNonNull(map);
		List<Item> itemAtActorLocation = new ArrayList<Item>();
		itemAtActorLocation = map.locationOf(actor).getItems();
		
		if (itemAtActorLocation.isEmpty()) {
			return null;
		} else {
			for (Item item: itemAtActorLocation) {
				if (item.asWeapon() != null) {
					return new PickUpItemAction(item);
				}
			}
		}
		return null;
	}
}

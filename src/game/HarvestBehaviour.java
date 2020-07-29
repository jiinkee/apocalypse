package game;

import java.util.ArrayList;
import java.util.Objects;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * A class that generates a HarvestAction so that the Actor can harvest ripe crops for food when he stands on or
 * next to the ripe crop.
 * 
 * @author Kee Pei Jiin
 */
public class HarvestBehaviour implements Behaviour{
	/**
	 * Returns an action that allows the actor (Farmer/Player) to harvest a ripe crop when he is standing on or next to a ripe crop.
	 * 
	 * @param actor		The actor that enacts this behaviour
	 * @param map		The map the actor is on
	 * @return 			A HarvestAction, or null if the harvest action is impossible to be carried out that turn
	 * @throws 			NullPointerException if actor or map argument is null
	 */
	public Action getAction(Actor actor, GameMap map) throws NullPointerException {
		Objects.requireNonNull(actor);
		Objects.requireNonNull(map);
		Location actorLoc = map.locationOf(actor);
		// possible locations for actor to harvest crop include the ground he is currently standing on and the grounds surrounding 
		// him
		ArrayList<Location> possibleLocations = new ArrayList<>();
		possibleLocations.add(actorLoc);	
		for (Exit exit: actorLoc.getExits()) {
			possibleLocations.add(exit.getDestination());
		}
		
		for (Location possibleLoc: possibleLocations) {
			// the only Ground that has the capability of HARVEST is ripe crop
			if (possibleLoc.getGround().hasCapability(ZombieCapability.HARVEST)) {
				return new HarvestAction(possibleLoc.x(), possibleLoc.y(), map);
			}
		}		
		return null;
	}

}
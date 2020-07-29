package game;

import java.util.Objects;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;

/**
 * Class the generates a FertiliseAction if Farmer is standing on a unripe crop.
 * 
 * @author Kee Pei Jiin
 */
public class FertiliseBehaviour implements Behaviour{
	/**
	 * Returns an action that allows the Farmer to fertilise when he is standing on an unripe crop.
	 * 
	 * @param actor		The actor that enacts this behaviour. In the game design, the only actor that has this behaviour is Farmer.
	 * @param map		The map the actor is on
	 * @return 			A SowingAction, or null if the harvest action is impossible to be carried out that turn
	 * @throws 			NullPointerException if actor or map argument is null
	 */
	public Action getAction(Actor actor, GameMap map) throws NullPointerException {
		Objects.requireNonNull(actor);
		Objects.requireNonNull(map);
		
		Ground farmerGround = map.locationOf(actor).getGround();
		if (farmerGround.hasCapability(ZombieCapability.FERTILISE) & farmerGround.hasCapability(ZombieCapability.UNRIPE)) {
			return new FertiliseAction();
		}
		return null;
	}
}

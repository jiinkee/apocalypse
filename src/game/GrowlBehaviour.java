package game;

import java.util.Objects;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Class that generates a GrowlAction for Zombie.
 * 
 * @author Kee Pei Jiin
 */
public class GrowlBehaviour implements Behaviour{
	/**The chance for a Zombie to growl at each turn is 10%*/
	private final static int GROWL_CHANCE = 10;
	/**A Random object that is used to model the probability for Zombie to growl*/
	private Random rand = new Random();
	
	/**
	 * Returns an action that allows Zombie to growl, i.e. says something zombie-like.
	 * 
	 * @param actor		The actor that enacts this behaviour
	 * @param map		The map the actor is on
	 * @return 			A GrowlAction, or null if the random value generated does not fall into the probability space
	 * 					where Zombie does not growl
	 * @throws 			NullPointerException if actor or map argument is null
	 */
	public Action getAction(Actor actor, GameMap map) throws NullPointerException {
		Objects.requireNonNull(actor);
		Objects.requireNonNull(map);
		
		if (rand.nextDouble()*100 <= GROWL_CHANCE) {
			return new GrowlAction();
		}else {
			return null;
		}
		
	}

}
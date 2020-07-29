package game;

import java.util.Objects;

import edu.monash.fit2099.engine.*;

/**
 * A class that figures out a MoveAction that will move the actor one step 
 * closer to a target Actor.
 * 
 * @author Tan Wei Li
 */
public class FollowBehaviour implements Behaviour {

	private Actor target;

	/**
	 * Constructor.
	 * 
	 * @param subject the Actor to follow
	 * @throws NullPointerException if subject argument is null
	 */
	public FollowBehaviour(Actor subject) throws NullPointerException {
		Objects.requireNonNull(subject);
		this.target = subject;
	}

	/**
	 * @throws NullPointerException if subject argument is null
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) throws NullPointerException {
		Objects.requireNonNull(actor);
		Objects.requireNonNull(map);
		
		if(!map.contains(target) || !map.contains(actor))
			return null;
		
		Location here = map.locationOf(actor);
		Location there = map.locationOf(target);

		int currentDistance = distance(here, there);
		for (Exit exit : here.getExits()) {
			Location destination = exit.getDestination();
			if (destination.canActorEnter(actor)) {
				int newDistance = distance(destination, there);
				if (newDistance < currentDistance) {
					return new MoveActorAction(destination, exit.getName());
				}
			}
		}

		return null;
	}

	/**
	 * Compute the Manhattan distance between two locations.
	 * 
	 * @param a the first location
	 * @param b the first location
	 * @return the number of steps between a and b if you only move in the four cardinal directions.
	 * @throws NullPointerException if a or b argument is null
	 */
	private int distance(Location a, Location b) throws NullPointerException {
		Objects.requireNonNull(a);
		Objects.requireNonNull(b);
		return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
	}
}
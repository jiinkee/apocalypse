package game;

import java.util.Objects;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * Special class for FertiliseAction so that the Farmer can fertilise an unripe crop and shorten the time needed for crop to ripe.
 * 
 * @author Kee Pei Jiin
 */
public class FertiliseAction extends Action{
	/**The x-coordinate of the Location of unripe crop*/
	private int x;
	/**The y-coordinate of the Location of unripe crop*/
	private int y;
	
	/**
	 * Fertilise the unripe Crop object that is at the Farmer's current location.
	 * 
	 * @see 			Action#execute(Actor, GameMap)
	 * @param actor 	The actor performing the action.
	 * @param map 		The map the actor is on.
	 * @return 			A suitable description to display in the UI
	 * @throws 			NullPointerException if actor or map argument is null
	 */
	@Override
	public String execute(Actor actor, GameMap map) throws NullPointerException {
		Objects.requireNonNull(actor);
		Objects.requireNonNull(map);
		
		Location farmerLoc = map.locationOf(actor);
		// for menuDescription's sake
		x = farmerLoc.x();
		y = farmerLoc.y();
		Ground crop = farmerLoc.getGround();
		if (crop.hasCapability(ZombieCapability.FERTILISE)) {
			crop.fertilise();
		}
		return menuDescription(actor);
	}
	
	/**
	 * Describe the action in a format suitable for displaying in the menu.
	 *
	 * @see 			Action#menuDescription(Actor)
	 * @param actor 	The actor performing the action.
	 * @return 			A string, e.g. "Farmer has fertilised the crop at location (70, 15)"
	 * @throws 			NullPointerException if actor is null
	 */
	@Override
	public String menuDescription(Actor actor) throws NullPointerException {
		Objects.requireNonNull(actor);
		return actor.toString() + " has fertilised the crop at location " + "(" + x + ", " + y + ")";
	}
}

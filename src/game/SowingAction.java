package game;

import java.util.Objects;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * Special class for the Farmer to sow a crop.
 * 
 * @author Kee Pei Jiin
 */
public class SowingAction extends Action{
	/**The x-coordinate of the Location where sowing takes place*/
	private int x;
	/**The y-coordinate of the Location where sowing takes place*/
	private int y;
	
	/**
	 * Constructor.
	 * 
	 * @param xcoord	The x-coordinate of the Location where Farmer will sow
	 * @param ycoord	The y-coordinate of the Location where Farmer will sow
	 * @param map		The map where the action will be carried out
	 * @throws 			IllegalArgumentException if xcoord or ycoord argument is out of the range of the map
	 */
	public SowingAction(int xcoord, int ycoord, GameMap map) throws IllegalArgumentException {
		if (xcoord < map.getXRange().min() || xcoord > map.getXRange().max() || ycoord < map.getYRange().min() || ycoord > map.getYRange().max()) {
			throw new IllegalArgumentException("Sowing location coordinates out of range");
		}
		x = xcoord;
		y = ycoord;
	}
	
	/**
	 * Creates and add a new Crop object at the sowing Location.
	 * 
	 * @see 			Action#execute(Actor, GameMap)
	 * @param actor 	The actor performing the action.
	 * @param map 		The map the actor is on.
	 * @return 			A suitable description to display in the UI
	 * @throws 			NullPointerException if actor or map argument is null
	 */
	@Override
	public String execute(Actor actor, GameMap map) throws NullPointerException{
		Objects.requireNonNull(actor);
		Objects.requireNonNull(map);
		Location sowLocation = map.at(x, y);
		Crop newCrop = new Crop();
		sowLocation.setGround(newCrop);
		return menuDescription(actor);
	}
	
	/**
	 * Describe the action in a format suitable for displaying in the menu.
	 *
	 * @see 			Action#menuDescription(Actor)
	 * @param actor 	The actor performing the action.
	 * @return 			A string, e.g. "Farmer sows at location (70, 15)"
	 * @throws 			NullPointerException if actor argument is null
	 */
	@Override
	public String menuDescription(Actor actor) throws NullPointerException{
		Objects.requireNonNull(actor);
		return actor.toString() + " sows at location " + "(" + x + ", " + y + ")";
	}
	
}
package game;

import java.util.Objects;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * Special action for Farmer and Player to harvest ripe crops.
 * 
 * @author Kee Pei Jiin
 */
public class HarvestAction extends Action{
	/**The x-coordinate of the harvest location.*/
	private int x;
	/**The y-coordinate of the harvest location.*/
	private int y;
	
	/**
	 * Constructor.
	 * 
	 * @param xcoord	x-coordinate of the location which the Farmer/Player will harvest at.
	 * @param ycoord	y-coordinate of the location which the Farmer/Player will harvest at.
	 * @param map		the map where the action is being carried out
	 * @throws 			IllegalArgumentException if xcoord or ycoord argument is out of the range of the map
	 */
	public HarvestAction(int xcoord, int ycoord, GameMap map) throws NullPointerException {
		if (xcoord < map.getXRange().min() || xcoord > map.getXRange().max() || ycoord < map.getYRange().min() || ycoord > map.getYRange().max()) {
			throw new IllegalArgumentException("Harvest location coordinates out of range");
		}
		x = xcoord;
		y = ycoord;
	}
	
	/**
	 * Harvest the ripe crop to become food. If the actor is Farmer, then the food will drop on the harvest location.
	 * If the actor is Player, then the food will be added to the Player's inventory.
	 * After the ripe crop has been harvested, the ground becomes Dirt again.
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
		
		Food newFood = new Food();
		Location harvestLoc = map.at(x, y);
		// only Farmer has the capability SOW
		if (actor.hasCapability(ZombieCapability.SOW)) {
			harvestLoc.addItem(newFood);
		} else {
			actor.addItemToInventory(newFood);
		}	
		harvestLoc.setGround(new Dirt());
		return menuDescription(actor);	
	}
	
	/**
	 * Describe the action in a format suitable for displaying in the menu.
	 *
	 * @see 			Action#menuDescription(Actor)
	 * @param actor 	The actor performing the action.
	 * @return 			A string, e.g. "Player harvested the crop at location (70, 15)"
	 * @throws 			NullPointerException if actor or map argument is null
	 */
	@Override
	public String menuDescription(Actor actor) throws NullPointerException{
		Objects.requireNonNull(actor);
		return actor.toString() + " harvested the crop at location (" + x + ", " + y + ")";
	}

}
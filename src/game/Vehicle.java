package game;

import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Item;

/**
 * Represents the vehicle that is used for Player to travel between different maps.
 * 
 * @author Kee Pei Jiin
 */
public class Vehicle extends Item{
	/**The map that player will reach if he uses this vehicle*/
	private ZombieGameMap destinationMap;
	
	/**
	 * Constructor.
	 * 
	 * @param destination	The destination map if the Player travels using this vehicle
	 * @throws 				NullPointerException when destination argument is null
	 */
	public Vehicle(ZombieGameMap destination) throws NullPointerException{
		super("Vehicle", '?', false);
		destinationMap = destination;
	}
	
	/**
	 * Getter for a list of actions that a Player can do with using vehicle.
	 * 
	 * @return an unmodifiable Actions object that contains a TravelAction.
	 */
	@Override
	public List<Action> getAllowableActions() {
		super.allowableActions = new Actions(new TravelAction(destinationMap));
		return super.getAllowableActions();
	}
}

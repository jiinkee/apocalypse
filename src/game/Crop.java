package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * Represents all crops in the Zombie game world.
 * 
 * @author Kee Pei Jiin
 *
 */
public class Crop extends Ground{
	/**Keeps track of the number of turns since the Crop has been created*/
	private int age = 0;
	/**A constant that stores the number of turns needed for Crop to ripe, which is 20 turns*/
	private static final int RIPE_TURN = 20;
	
	/**
	 * Constructor.
	 * 
	 * Each Crop has two life stages: unripe and ripe. Farmer will always sows unripe crop.
	 * Only unripe crop can be fertilised and only ripe crop can be harvested.
	 * Therefore, at the beginning, a Crop only has the capability of UNRIPE and FERTILISE.
	 */
	public Crop() {
		super('x');
		addCapability(ZombieCapability.UNRIPE);
		addCapability(ZombieCapability.FERTILISE);
	}

	/**
	 * Crop grows faster when it is fertilised. When Crop is fertilised, its age increases by 10.
	 * If the age of Crop becomes greater than or equal to 20, it ripes.
	 */
	@Override
	public void fertilise() {
		age = age + 10;
		if (age >= RIPE_TURN) {
			this.ripe();
		}
	}
	
	/**
	 * When the crop ripe, the capability of Crop changes from UNRIPE to RIPE, and from FERTILISE to HARVEST.
	 */
	private void ripe() {
		if (this.hasCapability(ZombieCapability.UNRIPE)) {
			displayChar = 'X';
			this.removeCapability(ZombieCapability.UNRIPE);
			this.removeCapability(ZombieCapability.FERTILISE);
			this.addCapability(ZombieCapability.RIPE);
			this.addCapability(ZombieCapability.HARVEST);
		}
	}
	
	/**
	 * Crop experiences the passage of time.
	 * If the age of Crop reaches 20, it ripes.
	 * 
	 * @param location 	The location of the crop
	 */
	@Override
	public void tick(Location location) {
		super.tick(location);
		age ++;
		
		if (age == RIPE_TURN) {
			this.ripe();
		}
	}
	
	/**
	 * Returns an Action list that contains the HarvestAction when the ripe crop is beside an actor who has the 
	 * capability to harvest.
	 * Else, returns an empty Action list.
	 * 
	 * @param actor 		the Actor acting
	 * @param location 		the Location of the Crop
	 * @param direction 	the direction of the Crop from the Actor
	 * @return				a new Action list that contains the HarvestAction or a new, empty collection of Actions
	 */
	@Override
	public Actions allowableActions(Actor actor, Location location, String direction){
		if (actor.hasCapability(ZombieCapability.HARVEST) & this.hasCapability(ZombieCapability.HARVEST)) {
			return new Actions(new HarvestAction(location.x(), location.y(), location.map()));
		}
		return new Actions();
	}
}
package game;

import java.util.Arrays;
import java.util.List;

/***
 * Represents farmers in the Zombie game world.
 * 
 * @author Kee Pei Jiin
 */
public class Farmer extends Human{
	/**
	 * Constructor.
	 * Each farmer can sow, fertilise and harvest.
	 * 
	 * @param farmerName	Name of the farmer
	 */
	public Farmer(String farmerName) {
		super(farmerName, 'F', 50);
		addCapability(ZombieCapability.SOW);
		addCapability(ZombieCapability.FERTILISE);
		addCapability(ZombieCapability.HARVEST);
				
		addFarmerBehaviours();
	}
	
	/**
	 * Extends the Human behaviours with Farmer behaviours to get all behaviours of a farmer.
	 */
	private void addFarmerBehaviours() {
		List<Behaviour> farmerBehaviours = Arrays.asList(new Behaviour[] {
				new SowingBehaviour(),
				new FertiliseBehaviour(),
				new HarvestBehaviour()
		});
		
		extendHumanBehaviours(0, farmerBehaviours);
	}
}
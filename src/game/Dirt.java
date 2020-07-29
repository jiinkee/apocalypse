package game;

import edu.monash.fit2099.engine.Ground;

/**
 * A class that represents bare dirt.
 * 
 * @author ram
 * @author Kee Pei Jiin
 */
public class Dirt extends Ground {

	/**
	 * Constructor.
	 * Dirt is the only Ground that has the capability of SOW.
	 */
	public Dirt() {
		super('.');
		// add the SOW capability to indicate that the Dirt can be sowed.
		addCapability(ZombieCapability.SOW);
	}
}

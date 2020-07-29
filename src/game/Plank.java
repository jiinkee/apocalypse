package game;

/**
 * A primitive weapon.
 * 
 * @author ram
 * @author Tan Wei Li
 *
 */
public class Plank extends ShortRangedWeapon {
	
	/**
	 * Constructor.
	 * Create a Plank using ShortRangedWeapon constructor with required arguments
	 * name of "plank", displayChar of ')', damage of 20 and verb to use the mace
	 * which is "whacks".	 */
	public Plank() {
		super("plank", ')', 20, "whacks");
	}

}

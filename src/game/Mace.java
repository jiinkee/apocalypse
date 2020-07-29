package game;

/**
 * A class which represents a mace crafted from a zombie's dropped leg.
 * 
 * @author Tan Wei Li
 */
public class Mace extends ShortRangedWeapon {
	
	/**
	 * Constructor.
	 * Create a mace using ShortRangedWeapon constructor with required arguments
	 * name of "mace", displayChar of '$', damage of 40 and verb to use the mace
	 * which is "use mace to hit".
	 */
	public Mace() {
		super("mace",'$',40,"use mace to hit");
	}
}
package game;

/**
 * A class which represents a club crafted from a  zombie’s dropped arm.
 * 
 * @author Tan Wei Li
 */
public class Club extends ShortRangedWeapon {
	
	/**
	 * Constructor.
	 * Create a club using ShortRangedWeapon constructor with required arguments
	 * name of "club", displayChar of '~', damage of 30 and verb to use the mace
	 * which is "uses club to hit".
	 */
	public Club() {
		super("club",'~',30,"uses club to hit");
	}
}

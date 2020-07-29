package game;

/**
 * A class which represents the ammunition box.
 * 
 * @author Tan Wei Li
 *
 */
public class AmmunitionBox extends PortableItem {
	/**An integer representing the number of bullet left*/
	private int bulletLeft;
	
	/**
	 * Constructor
	 * Create an ammunition box which is also a portable item,
	 * has 20 bullets in it initially ZombieCapability.AMMUNITION_BOX
	 */
	public AmmunitionBox() {
		super("ammunition box", '(');
		this.bulletLeft = 20;
		addCapability(ZombieCapability.AMMUNITION_BOX);
	}
	
	/**
	 * Check if the ammunition box is empty.
	 * @return true is ammunition box is empty, else false.
	 */
	public boolean isEmpty() {
		return bulletLeft == 0;
	}
	
	/**
	 * Use one bullet in the ammunition box.
	 * Decrement bullet number left by one.
	 * 
	 * @throws IllegalStateException if the ammunition box is empty.
	 */
	public void useBullet() throws IllegalStateException {
		if (this.isEmpty()) 
			throw new IllegalStateException();
		bulletLeft --;
	}
	
	/**
	 * Get the number of bullets left in the ammunition box.
	 * @return an integer which represents the number of bullet left in ammunition box.
	 */
	public int getBulletLeft() {
		return this.bulletLeft;
	}
}

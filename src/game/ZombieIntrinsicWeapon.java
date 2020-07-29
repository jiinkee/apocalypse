package game;

import edu.monash.fit2099.engine.IntrinsicWeapon;

/**
 * Represents the intrinsic weapons of Zombie, e.g. punch, bite.
 * 
 * @author Kee Pei Jiin
 */
public class ZombieIntrinsicWeapon extends IntrinsicWeapon{
	/**The health point that Zombie earns after a successful attack*/
	private int healPoint;
	/**The probability for Zombie to miss the attack using the intrinsic weapon*/
	private int missChance;
	
	/**
	 * Constructor.
	 * 
	 * @param verb		Verb that describes how the Zombie use the intrinsic weapon
	 * @param damage	The damage point of the intrinsic weapon
	 * @param heal		The health point that Zombie earns after a successful attack using the intrinsic weapon
	 * @param miss		The probability for Zombie to miss the attack using the intrinsic weapon
	 */
	public ZombieIntrinsicWeapon(String verb, int damage, int heal, int miss) {
		super(damage, new String(verb));
		this.healPoint = heal;
		this.missChance = miss;
	}
	
	/**
	 * Getter for the health point that Zombie earns from a successful attack.
	 * 
	 * @return An integer representing the health point that Zombie gets after a successful attack.
	 */
	public int healPoint() {
		return this.healPoint;
	}
	
	/**
	 * Getter for the probability for Zombie to miss an attack using that intrinsic weapon.
	 * 
	 * @return	A integer representing the probability for Zombie to miss an attack using the intrinsic weapon.
	 */
	public int missChance() {
		return this.missChance;
	}
	
	/**
	 * Getter for verb that describes how the Zombie use the intrinsic weapon
	 * 
	 * @return a string which represents verb that describes how the Zombie use the intrinsic weapon
	 */
	public String getVerb() {
		return new String(this.verb());
	}
	
	/**
	 * Double the chances for Zombie to miss the attack when using this intrinsic weapon
	 */
	public void doubleMissChance() {
		this.missChance = this.missChance*2;
	}
	
}


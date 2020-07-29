package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * A base class for short-ranged weapons.
 *
 * @author Tan Wei Li
 */
public abstract class ShortRangedWeapon extends WeaponItem{
	
	/**
	 * Constructor.
	 * Create a WeaponItem with capability of SHORT_RANGED_WEAPON.
	 * 
	 * @param name name of the item
	 * @param displayChar character to use for display when item is on the ground
	 * @param damage amount of damage this weapon does
	 * @param verb verb to use for this weapon, e.g. "hits", "zaps"
	 */
	public ShortRangedWeapon(String name, char displayChar, int damage, String verb) {
		super(name, displayChar, damage, verb);
		super.addCapability(ZombieCapability.SHORT_RANGED_WEAPON);
	}
	
}

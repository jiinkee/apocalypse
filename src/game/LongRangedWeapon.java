package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.WeaponItem;

/**
 * A base class for long-ranged weapons.
 *
 * @author Tan Wei Li
 */
public abstract class LongRangedWeapon extends WeaponItem{
	/**An AmmunitionBox which contains bullets for the LongRangedWeapon*/
	private AmmunitionBox ammunitionBox = new AmmunitionBox();
	
	/**
	 * Constructor.
	 * Create a WeaponItem with capability of LONG_RANGED_WEAPON.
	 * 
	 * @param name name of the item
	 * @param displayChar character to use for display when item is on the ground
	 * @param damage amount of damage this weapon does
	 * @param verb verb to use for this weapon, e.g. "shoots"
	 */
	public LongRangedWeapon(String name, char displayChar, int damage, String verb) {
		super(name, displayChar, damage, verb);
		super.addCapability(ZombieCapability.LONG_RANGED_WEAPON);
	}
	
	/**
	 * Shoot using the long-ranged weapon and return a string reporting the
	 * number of bullets left.
	 * Use one bullet in the ammunition box of the long-ranged weapon
	 * 
	 * @return a string reporting number of bullet left after using one bullet.
	 * @throws IllegalStateException if the ammunition box is empty.
	 */
	public String shoot() throws IllegalStateException {
		this.ammunitionBox.useBullet();
		String returnString = this + " has ";
		int bulletLeft = ammunitionBox.getBulletLeft();
		if (bulletLeft <= 1) {
			returnString += bulletLeft + " bullet left";
		} else {
			returnString += bulletLeft + " bullets left";
		}
		return returnString;
	}
	
	/**
	 * Refill the ammunitionBox of the long-ranged weapon.
	 * Assign a new AmmunitionBox instance to the ammunitionBox
	 * attribute of the long-ranged weapon.
	 */
	public void refillAmmunitionBox() {
		this.ammunitionBox = new AmmunitionBox();
	}
	
	/**
	 * Check if the long-ranged weapon has bullets left to use.
	 * 
	 * @return true if there is bullet left in ammunition box of the 
	 * long-ranged weapon and false otherwise.
	 */
	public Boolean hasBullet() {
		if (this.ammunitionBox.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * If the Player has a shotgun without bullet in his inventory and has
	 * at least one ammunition in his inventory, return a list consisting of RefillBulletAction.
	 * 
	 * @return an unmodifiable list of Action
	 */
	public List<Action> getAllowableActions(Actor actor, GameMap map) {
		List<Action> action = new ArrayList<Action>();
		if (this.isInPlayerInventory(actor, map) & ! this.hasBullet() & actor.getAmmunitionBoxInInventory() != null) {
			action.add(new RefillBulletAction(this, actor.getAmmunitionBoxInInventory()));
			}
		return Collections.unmodifiableList(action);
	}	
	
}

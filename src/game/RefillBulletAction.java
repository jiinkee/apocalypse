package game;

import java.util.Objects;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * A class which represents an action to refill bullets of a weapon.
 * @author Tan Wei Li
 *
 */
public class RefillBulletAction extends Action {
	/**a LongRangedWeapon object to refill its bullet*/
	private LongRangedWeapon weapon;
	/**an Item representing ammunition box to be used to refill the weapon*/
	private AmmunitionBox newAmmunitionBox;
	
	/**
	 * Constructor.
	 * Specify the weapon and new ammunition box for the RefillBulletAction.
	 * @param weapon a LongRangedWeapon object to refill its bullet
	 * @param newAmmunitionBox an Item representing ammunition box to be used to refill the weapon
	 */
	public RefillBulletAction(LongRangedWeapon weapon, AmmunitionBox newAmmunitionBox) throws NullPointerException{
		Objects.requireNonNull(weapon);
		Objects.requireNonNull(newAmmunitionBox);
		this.weapon = weapon;
		this.newAmmunitionBox = newAmmunitionBox;	
	}
	
	/**
	 * Refill bullets in the weapon and remove the new ammunition box from
	 * the actor inventory.
	 * @throws NullPointerException if actor or map argument is null
	 */
	public String execute(Actor actor, GameMap map) throws NullPointerException{
		Objects.requireNonNull(actor);
		Objects.requireNonNull(map);
		
		this.weapon.refillAmmunitionBox();
		actor.removeItemFromInventory(newAmmunitionBox);
		return this.menuDescription(actor);
	}

	/**
	 * Return a descriptive string of the RefillBulletAction.
	 * @return a String, e.g. "Player refills bullets into shotgun"
	 * @throws NullPointerException if actor is null
	 */
	public String menuDescription(Actor actor) throws NullPointerException {
		Objects.requireNonNull(actor);
		return actor + " refills bullets into " + this.weapon;
	}

}

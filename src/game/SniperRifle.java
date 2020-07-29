package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * A class which represents a sniper rifle.
 * @author Tan Wei Li
 *
 */
public class SniperRifle extends LongRangedWeapon {
	
	/**
	 * Constructor.
	 * Create a sniper rifle LongRangedWeapon which has capability of LONG_RANGED_WEAPON.
	 */
	public SniperRifle() {
		super("sniper rifle", '>', 30, "shoot");
	}
	
	/**
	 * Return a list consisting of UseSniperAction only if the actor is Player, has
	 * the sniperRifle which still has bullet in his inventory and is not aiming at a target. 
	 * This is to make sure only Player can use sniperRifle which still has bullet and the 
	 * sniperRifle must be picked up before it is able to be used and to make sure the 
	 * UseSniperAction is only available if the player is not aiming at any target.
	 * 
	 * Use parent class LongRangedWeapon.getAllowableActions to get RefillBulletAction if
	 * the sniperRifle in the actor's inventory does not have any bullet left and there is at least
	 * one ammunition box in the actor's inventory.
	 * @return an unmodifiable list of Action
	 */
	public List<Action> getAllowableActions(Actor actor, GameMap map) {
		List<Action> action = new ArrayList<Action>();
		action.addAll(super.getAllowableActions(actor, map));
		if (this.isInPlayerInventory(actor, map) & !(actor.aimingAtTarget()) & this.hasBullet()) {
			action.add(new UseSniperAction(this));
		} 
		return Collections.unmodifiableList(action);
	}	
}

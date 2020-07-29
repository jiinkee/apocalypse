package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * A class which represents a shotgun.
 * @author Tan Wei Li
 *
 */
public class Shotgun extends LongRangedWeapon {
	
	/**
	 * Constructor.
	 * Create a shotgun LongRangedWeapon which has capability of LONG_RANGED_WEAPON.
	 */
	public Shotgun() {
		super("shotgun", '*', 20, "shoots");
	}
	
	/**
	 * Return a list consisting of ShootingAction only if the actor is Player and has
	 * the shotgun which still has bullet in his inventory. This is to make sure only Player can
	 * perform ShootingAction using shotgun which still has bullets and the shotgun must be picked up before 
	 * it is able to be used.
	 * Use parent class LongRangedWeapon.getAllowableActions to get RefillBulletAction if
	 * the shotgun in the actor's inventory does not have any bullet left and there is at least
	 * one ammunition box in the actor's inventory.
	 * 
	 * @return an unmodifiable list of Action
	 */
	public List<Action> getAllowableActions(Actor actor, GameMap map) {
		List<Action> action = new ArrayList<Action>();
		action.addAll(super.getAllowableActions(actor, map));
		if (this.isInPlayerInventory(actor, map) & this.hasBullet()) {
			action.add(new ShootingAction(this));
		} 
		return Collections.unmodifiableList(action);
	}	
	
}

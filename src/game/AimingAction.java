package game;

import java.util.Objects;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * A class which represents action to aim at a target.
 * 
 * @author Tan Wei Li
 *
 */
public class AimingAction extends Action {
	/**an Actor acts as target for aiming*/
	private Actor target;
	/**a LongRangedWeapon used to aim at target*/
	private LongRangedWeapon sniperRifle;
	/**an integer representing number of times of consecutive aiming*/
	private int aimNumber;
	
	/**
	 * Constructor.
	 * Specify the target and weapon used in AimingAction. aimNumber by default set as 1.
	 * 
	 * @param target an Actor acts as target for aiming
	 * @param sniperRifle a LongRangedWeapon used to aim at target
	 * @throws NullPointerException if target or SniperRifle is null
	 */
	public AimingAction(Actor target, LongRangedWeapon sniperRifle) throws NullPointerException{
		Objects.requireNonNull(target);
		Objects.requireNonNull(sniperRifle);

		this.target = target;
		this.sniperRifle = sniperRifle;
		this.aimNumber = 1;
	}
	
	/**
	 * Constructor
	 * Specify the target, weapon and aimNumber for the AimingAction.
	 * 
	 * @param target an Actor acts as target for aiming
	 * @param sniperRifle a LongRangedWeapon used to aim at target
	 * @param aimNumber an integer representing number of times of consecutive aiming
	 * @throws NullPointerException if target, SniperRifle or aimNumber is null
	 * @throws IllegalArgumentException if aimNumber is not 1 or 2
	 */
	public AimingAction(Actor target, LongRangedWeapon sniperRifle, int aimNumber) throws NullPointerException, IllegalArgumentException{
		Objects.requireNonNull(target);
		Objects.requireNonNull(sniperRifle);
		Objects.requireNonNull(aimNumber);
		if (!(aimNumber==1 || aimNumber==2)) {
			throw new IllegalArgumentException();
		}
		this.target = target;
		this.sniperRifle = sniperRifle;
		this.aimNumber = aimNumber;
	}

	/**
	 * Set the actor attribute to indicate it is aiming at a target.
	 * 
 	 * @see 			Action#execute(Actor, GameMap)
	 * @param actor 	The actor performing the action.
	 * @param map 		The map the actor is on.
	 * @return 			A suitable description to display in the UI
	 * @throws 			NullPointerException if actor or map argument is null
	 */
	public String execute(Actor actor, GameMap map) throws NullPointerException{
		Objects.requireNonNull(actor);
		Objects.requireNonNull(map);
		actor.setAimingAtTarget(true);
		return menuDescription(actor);
	}

	/**
	 * Get a description string which describes the number of consecutive aiming at target.
	 * 
	 * @see 			Action#menuDescription(Actor)
	 * @param actor		The actor performing the action.
	 * @return 			a string which describe the aiming action (eg: "Player aims at Groan for the first time")
	 * @throws 			NullPointerException if actor is null
	 */
	public String menuDescription(Actor actor) throws NullPointerException{
		Objects.requireNonNull(actor);
		String description = actor + " aims at " + target;
		if (this.aimNumber == 1) {
			description += " for the first time";
		} else if (this.aimNumber > 1) {
			description += " for the second time";
		}
		return description;
	}
	
	/**
	 * Based on the aim number, return the next corresponding AimingAction which
	 * has current aim number+1 and also SnippingAction which 
	 * has specific miss chance and damage and return null if the aim number is
	 * zero or greater than 2
	 * 
	 * @return AimingAction which has current aim number +1 and SnippingAction which has specific miss chance and 
	 * 		   damage if the aim number is 1 or 2, else return null
	 */
	public Actions getNextActions() {
		Actions nextActions = new Actions();
		if (aimNumber == 1) {
			nextActions.add(new AimingAction(target, sniperRifle, aimNumber+1));
			nextActions.add(new SnippingAction(target, sniperRifle, aimNumber));
			return nextActions;
		} else if (aimNumber == 2) {
			nextActions.add(new SnippingAction(target, sniperRifle, aimNumber));
			return nextActions;
		}
		return null;
	}
	
}

package edu.monash.fit2099.interfaces;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.AmmunitionBox;


/**
 * This interface provides the ability to add methods to Actor, without modifying code in the engine,
 * or downcasting references in the game.   
 * 
 * @author Kee Pei Jiin
 * @author Tan Wei Li
 */

public interface ActorInterface {

	/**
	 * Getter for maximum hit point of actor
	 * 
	 * @return An integer representing the maximum hit point of the actor
	 */
	public int maxHitPoints();
	
	/**
	 * Getter for current hit point of actor
	 * 
	 * @return An integer representing the current hit point of the actor
	 */
	public int hitPoints();
	
	/**
	 * Actor takes the damage, i.e. health point is deducted by the damage value of the weapon used in attack
	 *
	 * @param points number of hitpoints to deduct.
	 * @param otherActor The other actor performing the attack action to the current actor.
	 * @param map The map the other actor attacking the current actor is on.
	 * @return A string that describes the damage taken by target, e.g. "Zombie drops an arm"
	 */
	public String takeDamage(int points, Actor otherActor, GameMap map);
	
	/**
	 * Check if the actor is aiming at a target
	 * 
	 * @return a boolean which indicates if the actor is aiming at a target.
	 */
	public Boolean aimingAtTarget();
	
	/**
	 * Set the actor to indicate that it is aiming at a target or not
	 *  
	 * @param aim a boolean attribute to be set to actor's attribute to indicate if it is aiming at target or not
	 */
	public void setAimingAtTarget(Boolean aim);
	
	/**
	 * Get the first ammunition box in actor inventory if there is any.
	 * By default, this method returns null.
	 * 
	 * @return the first ammunition box in actor inventory if there is any,
	 * otherwise return null
	 */
	default AmmunitionBox getAmmunitionBoxInInventory() {
		return null;
	}
	
}

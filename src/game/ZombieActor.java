package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;

/**
 * Base class for Actors in the Zombie World
 * 
 * @author ram
 * @author Kee Pei Jiin
 * @author Tan Wei Li
 *
 */
public abstract class ZombieActor extends Actor {	
	/**a Behaviours object which stores a list of Behaviour that can be performed by the actor*/
	protected Behaviours behaviours = new Behaviours();
	/**a Boolean attribute to keep track if the actor is aiming at its target, initialized as false*/
	protected Boolean aiming = false;
	
	/**
	 * Constructor.
	 * 
	 * @param name			Name of the actor in zombie world
	 * @param displayChar	The character used to represent the actor in display
	 * @param hitPoints		The maximum hit points the actor can have before he dies
	 * @param team			The team (i.e. ZombieCapability) of the team, used to differentiate between Zombie and Human
	 */
	public ZombieActor(String name, char displayChar, int hitPoints, ZombieCapability team){
		super(name, displayChar, hitPoints);
		addCapability(team);
	}
	
	/**
	 * Return an Actions object consisting of HumanAttackAction if the current actor is 
	 * Zombie and the other actor is Human. If the current actor is Human and other actor 
	 * is Zombie, return an Actions object consisting of  ZombieAttackAction.
	 * 
	 * @param otherActor the Actor that might be performing attack
	 * @param direction  String representing the direction of the other Actor
	 * @param map        current GameMap
	 * @return A collection of Actions.
	 */
	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		Actions list = super.getAllowableActions(otherActor, direction, map);

		// if otherActor is Human or Player and the current Actor is a Zombie, add a HumanAttackAction targeting
		// the current Actor into the Action list
		if (otherActor.hasCapability(ZombieCapability.ALIVE) & this.hasCapability(ZombieCapability.UNDEAD)) {
			list.add(new HumanAttackAction(this));
		} else if (otherActor.hasCapability(ZombieCapability.UNDEAD) & this.hasCapability(ZombieCapability.ALIVE)) {
			list.add(new ZombieAttackAction(this));
		}
		
		return list;
	}
	
	/**
	 * Getter for maximum hit point of actor
	 * @return An integer representing the maximum hit point of the actor
	 */
	public int maxHitPoints() {
		return this.maxHitPoints;
	}
	
	/**
	 * Getter for current hit point of actor
	 * @return An integer representing the current hit point of the actor
	 */
	public int hitPoints() {
		return this.hitPoints;
	}
	
	/**
	 * The ZombieActor takes the damage, i.e. health point is deducted by the damage value of the weapon used in attack
	 *
	 * @param points number of hitpoints to deduct.
	 * @param otherActor The other actor performing the attack action to the current actor.
	 * @param map The map the other actor attacking the current actor is on.
	 * @return An empty string.
	 */
	public String takeDamage(int points, Actor otherActor, GameMap map) {
		super.hurt(points);
		return "";
	}
	
	/**
	 * Return an action which can be performed by the actor in the actor's current turn.
	 * 
	 * @param actions list of possible Actions
	 * @param lastAction previous Action, if it was a multiturn action
	 * @param map the map where the current actor is
	 * @param display the Display where the actor's utterances will be displayed
	 */
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null)
				return action;
		}
		return new DoNothingAction();	
	}
	
	/**
	 * Get the weapon this Actor is using.
	 * 
	 * If the current Actor is carrying weapons, returns the first short-ranged weapon 
	 * in the inventory. Otherwise, returns the Actor's natural fighting equipment e.g.
	 * fists.
	 *
	 * @return the Actor's short-ranged weapon or natural
	 */
	public Weapon getWeapon() {
		for (Item item : inventory) {
			if ((item.asWeapon() != null) & (item.hasCapability(ZombieCapability.SHORT_RANGED_WEAPON))) 
				return item.asWeapon();
		}
		return getIntrinsicWeapon();
	}
	
	/**
	 * Check if actor is aiming at target.
	 * 
	 * @return a boolean which indicates if the actor is aiming at a target.
	 */
	public Boolean aimingAtTarget() {
		return this.aiming;
	}
	
	/**
	 * Set the actor to indicate that it is aiming at a target or not
	 * 
	 * @param aim a boolean attribute to be set to actor's attribute to indicate if it is aiming at target or not
	 */
	public void setAimingAtTarget(Boolean aim) {
		this.aiming = aim;	
	}
	
	/**
	 * Get the first ammunition box in actor inventory if there is any.
	 * @return the first ammunition box in actor inventory if there is any,
	 * otherwise return null
	 */
	public AmmunitionBox getAmmunitionBoxInInventory() {
		for (Item item: this.getInventory()) {
			if (item.hasCapability(ZombieCapability.AMMUNITION_BOX)) {
				return (AmmunitionBox) item;
			}
		}
		return null;
	}
}

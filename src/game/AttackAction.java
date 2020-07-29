package game;

import java.util.Objects;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;

/**
 * Special Action for attacking other Actors.
 * 
 * @author Kee Pei Jiin
 * @author Tan Wei Li
 * 
 */
public abstract class AttackAction extends Action {
	/**The Actor that is to be attacked*/
	protected Actor target;
	/**Random number generator*/
	protected Random rand = new Random();
	/**A String which describe the result of the AttackAction to be displayed to the user*/
	protected String result = "";
	/**A Weapon object used to attack the target*/
	protected Weapon weapon = null;
	/**An integer representing the damage of the attack action to the target*/
	protected Integer damage = null;
	/**An integer representing the miss chance of the attack action*/
	protected Integer missChance = null;
	
	/**
	 * Return Boolean which indicates if the actor misses the target
	 * 
	 * @param weapon	The weapon used in the attack
	 * @return true if the actor misses the target and false otherwise.
	 */
	protected abstract Boolean missAttack(Weapon weapon);
	
	/**
	 * Operations to be carried out when the attack is success.
	 * 
	 * @param damage 	 The damage point the target suffer from the attack.
	 * @param actor 	 The actor performing the attack action.
	 * @param weaponUsed The weapon used in the attack
	 * @param map 		 The map the actor is on.
	 */
	protected abstract void attackSuccess(int damage, Actor actor, Weapon weaponUsed, GameMap map); 
	
	/**
	 * Creates corpse for the target and add the corpse onto the map when the target becomes unconscious, i.e. dead.
	 * 
	 * @param map The map the actor is on.
	 */
	protected abstract void killTarget(GameMap map);

	/**
	 * Perform the Action.
	 * Only get the first weapon from actor inventory which must always either be short-ranged weapon
	 * or intrinsic weapon, if the weapon is not specified in the AttackAction.
	 * If miss chance of attack action is specified, use the miss chance. Else, calculate 
	 * miss chance based on operation in missAttack method.
	 * If the damage point is specified, hurt the target using the damage point, else
	 * use the damage point of the weapon.
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a description of what happened that can be displayed to the user.
	 * @throws NullPointerException if actor or map argument is null
	 */
	@Override
	public String execute(Actor actor, GameMap map) throws NullPointerException {
		Objects.requireNonNull(actor);
		Objects.requireNonNull(map);
		// only get weapon from actor is it is not specified in the AttackAction
		if (this.weapon == null) {
			this.weapon = actor.getWeapon();
		}

		if (missAttack(weapon)) {
			return actor + " misses " + target;
		}
		// only get the damage for the AttackAction if it is not specified in the AttackAction
		if (this.damage == null) {
			this.damage = this.weapon.damage();
		}
		this.result += actor + " " + weapon.verb() + " " + target + " for " + damage + " damage";
		attackSuccess(damage, actor, weapon, map);
		
		if (!target.isConscious()) {
			killTarget(map);
			result += System.lineSeparator() + target + " is killed";
			dropCorpseItems(map);
			map.removeActor(target);
		}
		return result;
	}
	
	/**
	 * When the target dies, all the items in the target's inventory will be dropped to the ground.
	 * 
	 * @param map The map the actor is on.
	 */
	protected void dropCorpseItems(GameMap map) {
		Actions dropActions = new Actions();
		for (Item item : target.getInventory()) {
			dropActions.add(item.getDropAction());
		}
		for (Action drop : dropActions)	{
			drop.execute(target, map);
		}
	}

	
	/**
	 * Describe the action in a format suitable for displaying in the menu.
	 *
	 * @see 			Action#menuDescription(Actor)
	 * @param actor 	The actor performing the action.
	 * @return 			A string, e.g. "Human attacks Zombie"
	 * @throws NullPointerException if actor is null
	 */
	@Override
	public String menuDescription(Actor actor) throws NullPointerException {
		Objects.requireNonNull(actor);
		return actor + " attacks " + target;
	}
}

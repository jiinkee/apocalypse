package game;

import java.util.Objects;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * A class which represents sniping action which perform attack to specific
 * target using specific miss chance and damage.
 * 
 * @author Tan Wei Li
 * @author Kee Pei Jiin
 *
 */
public class SnippingAction extends Action{
	/**An Actor which acts as target of SnippingAction*/
	private Actor target;
	/**a LongRangedWeapon used in SnippingAction*/
	private LongRangedWeapon sniperRifle;
	/**an integer representing the number of consecutive aiming before SnippingAction*/
	private int aimNumber;
	/**an array of integer represent different damage of SnippingAction after different number
	 * of consecutive aiming corresponding to the value of index*/
	private int[] damage;
	/**a constant array of integer represent different miss chance of SnippingAction after different number
	 * of consecutive aiming corresponding to the value of index*/
	private final int[] MISS_CHANCE = {25,10,0};
	
	/**
	 * Constructor.
	 * Specify the target and weapon used in SnippingAction. aimNumber set as 0 initially.
	 * Initialize value for damage array based on the target and sniperRifle.
	 * 
	 * @param target An actor which acts as target of SnippingAction
	 * @param sniperRifle a LongRangedWeapon used in SnippingAction
	 * @throws NullPointerException if actor or sniperRifle argument is null
	 */
	public SnippingAction(Actor target, LongRangedWeapon sniperRifle) throws NullPointerException{
		Objects.requireNonNull(target);
		Objects.requireNonNull(sniperRifle);
		this.target = target;
		this.sniperRifle = sniperRifle;
		this.aimNumber = 0;
		createDamagesLevel(target, sniperRifle);
	}
	
	/**
	 * Constructor.
	 * Specify the target, weapon and aimNumber used in SnippingAction.
	 * Initialize value for damage array based on the target and sniperRifle.
	 * 
	 * @param target 		An actor which acts as target of SnippingAction
	 * @param sniperRifle 	a LongRangedWeapon used in SnippingAction
	 * @param aimNumber		The number of turns that are spent for aiming
	 * @throws 				NullPointerException if actor or sniperRifle argument is null
	 * @throws 				IllegalArgumentException if aimNumber is not 1 or 2
	 */	
	public SnippingAction(Actor target, LongRangedWeapon sniperRifle, int aimNumber) throws NullPointerException, IllegalArgumentException{
		Objects.requireNonNull(target);
		Objects.requireNonNull(sniperRifle);
		Objects.requireNonNull(aimNumber);
		if (!(aimNumber==1 || aimNumber == 2)) {
			throw new IllegalArgumentException();
		}
		this.target = target;
		this.sniperRifle = sniperRifle;
		this.aimNumber = aimNumber;
		createDamagesLevel(target, sniperRifle);
	}
	
	/**
	 * Initialize value for damage of SnippingAction based on target and sniperRifle for each
	 * index which correspond to different number of consecutive aiming before the SnippingAction.
	 * 
	 * @param target		An actor which acts as target of SnippingAction
	 * @param sniperRifle	The LongRangedWeapon used in the SnippingAction
	 */
	private void createDamagesLevel(Actor target, LongRangedWeapon sniperRifle) {
		int standardDamage = sniperRifle.damage();
		int instantKill = target.hitPoints();
		int[] damage = {standardDamage, standardDamage*2, instantKill};
		this.damage = damage;
	}

	/**
	 * Perform attack to the target with different damage and miss chance based on the number of 
	 * consecutive aiming before the SnippingAction. Each execution of SnippingAction uses up one bullet
	 * in the SniperRifle and the actor loses concentration at the target after shooting. 
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
		
		Action attackAction = null;
		if (actor.hasCapability(ZombieCapability.ALIVE)) {
			attackAction = new HumanAttackAction(target, this.sniperRifle, damage[aimNumber], MISS_CHANCE[aimNumber]);
		} else if (actor.hasCapability(ZombieCapability.UNDEAD) || actor.hasCapability(ZombieCapability.PRIESTESS)) {
			attackAction = new ZombieAttackAction(target,this.sniperRifle, damage[aimNumber], MISS_CHANCE[aimNumber]);
		}
		String resultString = menuDescription(actor);
		if (attackAction != null) {
			resultString += "\n" + attackAction.execute(actor, map);
		}
		// shoot one time with the shotgun
		resultString += "\n" + actor + "'s " + this.sniperRifle.shoot();
		
		actor.setAimingAtTarget(false);
		return resultString;
	}

	/**
	 * Return description string which describe the level of damage of the SnippingAction.
	 * 
	 * @see 			Action#menuDescription(Actor)
	 * @param actor		The actor performing the action.
	 * @return 			a string which describe the aiming action (eg: "Player snipe at Groan for double damage", "Player instant kill Groan")
	 * @throws 			NullPointerException if actor is null
	 */
	public String menuDescription(Actor actor) throws NullPointerException{
		Objects.requireNonNull(actor);
		
		if (this.aimNumber == 1) {
			return actor + " snipe at " + this.target + " for double damage";
		}
		if (this.aimNumber == 2) {
			return actor + " instant kill " + this.target;
		}
		return actor + " snipe at " + this.target;
	}
	
}

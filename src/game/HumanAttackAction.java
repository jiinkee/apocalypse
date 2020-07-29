package game;

import java.util.Objects;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;

/**
 *  Special class which represents action taken by alive actors (Human/Player) to attack undead 
 *  actor or priestess (Zombie/Mambo Marie).
 *  
 * @author Kee Pei Jiin
 * @author Tan Wei Li
 */
public class HumanAttackAction extends AttackAction{

	/**
	 * Constructor.
	 * Create a human attack action and set the target of the action.
	 * 
	 * @param target the target of the attack action
	 * 
	 * @throws NullPointerException if target argument is null
	 */
	public HumanAttackAction(Actor target) throws NullPointerException {
		Objects.requireNonNull(target);
		this.target = target;
	}
	
	/**
	 * Constructor.
	 * Specify the target, weapon and miss chance to be used in the HumanAttackAction.
	 * @param target A Zombie object, target of shooting is Zombie
	 * @param weapon A Weapon object used to attack the target
	 * @param missChance An integer representing the miss chance of attack action
	 * 
	 * @throws NullPointerException if target argument is null
	 * @throws NullPointerException if weapon argument is null
	 * @throws NullPointerException if missChance argument is null
	 */
	public HumanAttackAction(Actor target, Weapon weapon, int missChance) throws NullPointerException {
		Objects.requireNonNull(target);
		Objects.requireNonNull(weapon);
		Objects.requireNonNull(missChance);
		this.target = target;
		this.weapon = weapon;
		this.missChance = missChance;

	}
	
	/**
	 * Constructor.
	 * Specify the target, weapon, damage and miss chance to be used in the HumanAttackAction.
	 * @param target A Zombie object, target of shooting is Zombie
	 * @param weapon A Weapon object used to attack the target
	 * @param damage An integer representing the damage of attack action
	 * @param missChance An integer representing the miss chance of attack action
	 *
	 * @throws NullPointerException if target argument is null
	 * @throws NullPointerException if weapon argument is null
	 * @throws NullPointerException if damage argument is null
	 * @throws NullPointerException if missChance argument is null
	 */
	public HumanAttackAction(Actor target, Weapon weapon, int damage, int missChance) throws NullPointerException {
		Objects.requireNonNull(target);
		Objects.requireNonNull(weapon);
		Objects.requireNonNull(damage);
		Objects.requireNonNull(missChance);
		this.target = target;
		this.weapon = weapon;
		this.damage = damage;
		this.missChance = missChance;
	}
	
	/**
	 * Return Boolean to indicate if the actor misses the target based on
	 * miss chance if it is specified in the attack action, else based on 50% chance.
	 * @return True if the actor misses the target and false otherwise.
	 */
	protected Boolean missAttack(Weapon weapon) {
		if (this.missChance != null) {
			return (rand.nextDouble()*100 <= this.missChance);
		}
		return rand.nextBoolean();
	}
	
	/**
	 * Human successfully kills his target, Zombie/Mambo Marie. 
	 * The Zombie/Mambo Marie becomes a corpse.
	 * The method also adds the DEAD capability to the target if target is Priestess (Mambo Marie).
	 * 
	 * @param map The map the actor is on.
	 */
	protected void killTarget(GameMap map) {
		Item corpse = new PortableItem("dead " + target, '%');
		map.locationOf(target).addItem(corpse);
		if (target.hasCapability(ZombieCapability.PRIESTESS)) {
			target.addCapability(ZombieCapability.DEAD);
		}
	}
	
	/**
	 * Attack is successful, hurt the target (Zombie) and have chance to knock one of its limbs off
	 * Update the result string if target's limb is dropped.
	 * 
	 * @param damage 		The damage point that the target suffers from.
	 * @param actor 		The actor performing the attack action.
	 * @param weaponUsed	The weapon used in the attack.
	 * @param map 			The map the actor is on.
	 */
	protected void attackSuccess(int damage, Actor actor, Weapon weaponUsed, GameMap map) {
		String dropLimbResult = target.takeDamage(damage, actor, map);
		if (dropLimbResult != "") {
			result += ", " + dropLimbResult;
		}
	}
}

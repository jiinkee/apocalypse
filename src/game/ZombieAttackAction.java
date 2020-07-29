package game;

import java.util.Objects;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Weapon;

/**
 *  Special class which represents action taken by undead 
 *  actor or priestess (Zombie/Mambo Marie) to attack alive actors (Human/Player) .
 *  
 * @author Kee Pei Jiin
 * @author Tan Wei Li
 */
public class ZombieAttackAction extends AttackAction{
	
	/**
	 * Constructor.
	 * 
	 * @param target A Human object, Zombie's target is Human
	 * 
	 * @throws NullPointerException if target argument is null
	 */
	public ZombieAttackAction(Actor target) throws NullPointerException {
		Objects.requireNonNull(target);
		this.target = target;
	}
	
	/**
	 * Constructor.
	 * Specify the target, weapon and miss chance to be used in the ZombieAttackAction .
	 * @param target A Human object, target of shooting is Human
	 * @param weapon A Weapon object used to attack the target
	 * @param missChance An integer representing the miss chance of attack action
	 * 
	 * @throws NullPointerException if target argument is null
	 * @throws NullPointerException if weapon argument is null
	 * @throws NullPointerException if missChance argument is null
	 */
	public ZombieAttackAction(Actor target, Weapon weapon, int missChance) throws NullPointerException {
		Objects.requireNonNull(target);
		Objects.requireNonNull(weapon);
		Objects.requireNonNull(missChance);

		this.target = target;
		this.weapon = weapon;
		this.missChance = missChance;

	}
	
	/**
	 * Constructor.
	 * Specify the target, weapon, damage and miss chance to be used in the ZombieAttackAction.
	 * @param target A Human object, target of shooting is Human
	 * @param weapon A Weapon object used to attack the target
	 * @param damage An integer representing the damage of attack action
	 * @param missChance An integer representing the miss chance of attack action
	 *
	 * @throws NullPointerException if target argument is null
	 * @throws NullPointerException if weapon argument is null
	 * @throws NullPointerException if damage argument is null
	 * @throws NullPointerException if missChance argument is null
	 */
	public ZombieAttackAction(Actor target, Weapon weapon, int damage, int missChance) throws NullPointerException {
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
	 * Return a Boolean that indicates whether Zombie will miss the attack
	 * based on the miss chance if specified in the attack action, else
	 * based on miss chance of different type of weapon being used in that attack.
	 * 
	 * @return True if Zombie misses the attack, false otherwise
	 */
	protected Boolean missAttack(Weapon weapon) {
		if (this.missChance != null) {
			return (rand.nextDouble()*100 <= missChance);
		}
		int missChance;
		if (weapon.verb() == "punches" || weapon.verb() == "bites") {
			missChance = ((ZombieIntrinsicWeapon) weapon).missChance();
		} else {
			missChance = 0;
		}
		if (rand.nextDouble()*100 <= missChance){
			return true;
		}	
		return false;		
	}
	
	/**
	 * Attack is successful, hurt the target (Human) and if the weapon used is "Zombie bite", then the health point of Zombie
	 * will be restored.
	 * 
	 * @param damage 		The damage point that the target suffers from.
	 * @param actor 		The actor performing the attack action.
	 * @param weaponUsed	The weapon used in the attack.
	 * @param map 			The map the actor is on.
	 */
	protected void attackSuccess(int damage, Actor actor, Weapon weaponUsed, GameMap map) {
		target.hurt(damage);
		if (weaponUsed.verb() == "bites") {
			int healPoint = ((ZombieIntrinsicWeapon) weaponUsed).healPoint();
			actor.heal(healPoint);
		}
	}
	
	/**
	 * Zombie successfully kill his target, Human. The Human becomes a corpse.
	 * 
	 * @param map	The map that the actor is on.
	 */
	protected void killTarget(GameMap map) {
		HumanCorpse corpse = new HumanCorpse(target.toString());
		map.locationOf(target).addItem(corpse);		
	}
}

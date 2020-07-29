package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.IntrinsicWeapon;
import edu.monash.fit2099.engine.Item;

/**
 * This is a class representing a zombie.
 * 
 * In each turn, if a Zombie can attack, it will.  If not, it will pick up weapon in 
 * its location if there is any. If not, it will chase any human within 10 spaces.  
 * If no humans are close enough it has 10% chance of growling, else it will wander randomly. 
 * 
 * 
 * If attacked by human, a zombie might drop one of its limbs to 
 * the ground which can be crafted into weapon by human. After dropping limb,
 * depending on the number of limbs the zombie left, the actions which can be
 * performed by the zombie will be changed (eg, move slower, drop weapon).
 * 
 * Zombie can pick up the dropped limbs but cannot attack or craft them.
 * 
 * @author Kee Pei Jiin
 * @author Tan Wei Li
 */
public class Zombie extends ZombieActor {
	/**A list that stores all Zombie's intrinsic weapons*/
	private List<ZombieIntrinsicWeapon> intrinsicWeapons = new ArrayList<ZombieIntrinsicWeapon>();
	/**A Random Object used to generate random integer/decimal*/
	private Random rand = new Random();
	/**The number of arms of the zombie, by default is 2*/
	private int arms = 2;
	/**The number of legs of the zombie, by default is 2*/
	private int legs = 2;
	/**Indicates if the Zombie is in slow motion (only have one leg), by default is false*/
	private boolean slowMotion = false;
	/** A constant Behaviours object which consists of a list of Behaviour objects which will change location of zombie*/
	private static final Behaviours MOVING_BEHAVIOUR = new Behaviours(Arrays.asList(new Behaviour[] {
			new HuntBehaviour(Human.class, 10),
			new WanderBehaviour()}));
	/** A constant integer which represents the probability of zombie dropping one of its limbs from an attack, by default is 25*/
	private static final int DROP_LIMB_CHANCE = 25;
	/** A constant integer which represents the probability of zombie dropping one of its weapons when dropping one of its arms, by default is 50*/
	private static final int DROP_WEAPON_CHANCE = 50;
	/** A constant that stores the string "punches"*/
	private static final String PUNCH_KEY = "punches";
	

	/**
	 * Creates a Zombie object using super() constructor, add 
	 * a Behaviours object consist of a list of Behaviour 
	 * (AttackBehaviour, PickUpItemBehaviour, HuntBehaviour, GrowlBehaviour 
	 * and WanderBehaviour)  to protected behaviours attribute in its parent 
	 * class which is the ZombieActor class and add ZombieIntrinsicWeapon into
	 * Zombie's private intrinsicWeapons attribute.
	 * 
	 * @param name the name of the zombie
	 */
	public Zombie(String name) {
		super(name, 'Z', 100, ZombieCapability.UNDEAD);
		behaviours.add(new Behaviours(Arrays.asList(new Behaviour[] {
			new AttackBehaviour(ZombieCapability.ALIVE),
			new PickUpItemBehaviour(),
			new HuntBehaviour(Human.class, 10),
			new GrowlBehaviour(),
			new WanderBehaviour()})));
		intrinsicWeapons.add(new ZombieIntrinsicWeapon("punches", 10, 0, 40));
		intrinsicWeapons.add(new ZombieIntrinsicWeapon("bites", 20, 5, 60));
	}
	
	/**
	 * Randomly select one of the Zombie's intrinsic weapons and returns it.
	 * 
	 * @return a randomly chosen intrinsic weapon
	 */
	@Override
	public IntrinsicWeapon getIntrinsicWeapon() {
		// randomly generate an integer from the index range of intrinsicWeapon and this integer will be the index
		// of the chosen intrinsic weapon
		// such implementation allows each of the Zombie's intrinsic weapon to have an equal chance of being chosen
		int randomIndex = rand.nextInt(intrinsicWeapons.size());
		IntrinsicWeapon weapon = intrinsicWeapons.get(randomIndex);	
		return weapon;
		
	}
	
	/**
	 * Do some damage to the zombie and have a fixed 25% chance
	 * to knock at least one of the zombie's limbs off
	 *
	 * @param points number of hitpoints to deduct.
	 * @param otherActor The actor performing the attack action to the zombie.
	 * @param map The map the actor attacking the zombie is on.
	 * @return A String that which limb the Zombie has dropped. If none of the limb is dropped, empty string is returned.
	 * @throws NullPointerException if otherActor or map argument is null
	 */
	public String takeDamage(int points, Actor otherActor, GameMap map) throws NullPointerException{
		Objects.requireNonNull(otherActor);
		Objects.requireNonNull(map);
		
		String dropLimbResult = "";
		super.hurt(points);
		
		if (rand.nextDouble()*100 < DROP_LIMB_CHANCE) {
			dropLimbResult = dropLimb(otherActor, map);
		}
		return dropLimbResult;
	}
	
	/**
	 * Randomly choose to drop one of the zombie's limbs if there is
	 * still any, else return without doing anything. 
	 * Each limb has an equal probability of getting knocked off.
	 * 
	 * @param actor The actor performing the attack action to the zombie.
	 * @param map The map the actor attacking the zombie is on.
	 * @return A String that indicated the limb that has been dropped. If none of the limb is dropped, empty string is returned.
	 */
	private String dropLimb(Actor actor, GameMap map) {
		int totalLimbsLeft = arms + legs;
		if (totalLimbsLeft <= 0) {
			return "";
		} else {
			int limbNumber = rand.nextInt(totalLimbsLeft) + 1;
			if (limbNumber <= arms) {
				dropArm(actor, map);
				return this.name + " drops an arm";
			} else {
				dropLeg(actor, map);
				return this.name + " drops a leg";
			}
		}
	}
	
	/**
	 * Return an Actions consisting of all the dropping actions of weapons in the 
	 * actor's inventory
	 * 
	 * @param actor The actor performing the attack action.
	 * @param map The map the actor is on.
	 * @return An Actions object consisting of all dropping actions of weapons in the actor's
	 * 		   inventory.
	 */
	private Actions dropWeapon(Actor actor, GameMap map) {
		Actions dropActions = new Actions();
		for (Item item : this.getInventory()) {
			if (item.asWeapon() != null) {
				dropActions.add(item.getDropAction());
			}
		}
		return dropActions;
	}
	
	/**
	 * Drop the first weapon in the zombie's inventory to the location of the attacking actor,
	 * if there is any.
	 * 
	 * @param actor The actor performing the attack action.
	 * @param map The map the actor is on.
	 */
	private void dropFirstWeapon(Actor actor, GameMap map) {
		Actions dropActions = dropWeapon(actor, map);
		for (Action drop : dropActions) {
			drop.execute(actor, map);
			break;
		}
	}
	
	/**
	 * Drop all of the weapons in the zombie's inventory to the location of the attacking actor,
	 * if there is any.
	 * 
	 * @param actor The actor performing the attack action.
	 * @param map The map the actor is on.
	 */
	private void dropAllWeapon(Actor actor, GameMap map) {
		Actions dropActions = dropWeapon(actor, map);
		for (Action drop : dropActions) {
			drop.execute(actor, map);
		}
	}
	
	/**
	 * Perform operations when one Zombie's arm is knocked off. 
	 * This method decrements the value of arms attribute by one, 
	 * and creates a limb object which represents arm at the attacking actor location. 
	 * If the zombie only has one arm left, its probability of
	 * punching is halved and there is a 50% chance of zombie dropping
	 * the weapon it is holding. 
	 * If the zombie does not have any arm left, drop all of its weapons
	 * and it cannot punch anymore. 
	 * 
	 * @param actor The actor performing the attack action.
	 * @param map The map the actor is on.
	 */
	private void dropArm(Actor actor, GameMap map) {
		arms--;
		// create and drop arm onto ground
		Limb arm = new Limb("zombie's arm", '/', ZombieCapability.CRAFTABLE_INTO_CLUB);
		map.locationOf(actor).addItem(arm);

		if (arms > 0) {
			// if arm left is one, double miss chance of punch
			if (intrinsicWeapons.size() > 0) {
				for (ZombieIntrinsicWeapon intrinsicWeapon: intrinsicWeapons) {
					if (intrinsicWeapon.getVerb().equals(PUNCH_KEY)) {
						intrinsicWeapon.doubleMissChance();
					}
				}
			}

			// 50% chance to lose weapon
			if (rand.nextDouble()*100 < DROP_WEAPON_CHANCE) {
				dropFirstWeapon(actor, map);
			}
			
		} else {
			// else, if no arm left, drop all weapon in inventory
			dropAllWeapon(actor, map);
			
			// remove the punches
			if (intrinsicWeapons.size() > 0) {
				intrinsicWeapons.removeIf(weapon -> weapon.getVerb().equals(PUNCH_KEY));
			}
			
			behaviours.remove(new PickUpItemBehaviour());
		}
	}
	
	
	/**
	 * Perform operations when one Zombie's leg is knocked off.
	 * This method decrements the value of legs attribute by one, 
	 * create a limb object which represents leg at the attacking actor location. 
	 * If the zombie only has one leg left, set its slowMotion attribute to
	 * true to indicate that zombie can only move every second turn after this.
	 * If the zombie does not have any leg left, remove all moving behaviours
	 * from zombie behaviours attribute so that zombie cannot move anymore.
	 * 
	 * @param actor The actor performing the attack action.
	 * @param map The map the actor is on.
	 */
	private void dropLeg(Actor actor, GameMap map) {
		legs--;
		// create and drop leg onto ground
		Limb leg = new Limb("zombie's leg", '=', ZombieCapability.CRAFTABLE_INTO_MACE);
		map.locationOf(actor).addItem(leg);
		
		// if leg left is one, set the slowMotion to true
		if (legs > 0) {
			slowMotion = true;
		} else {
			// remove behaviour in the MOVING_BEHAVIOUR from zombie behaviours attribute
			behaviours.remove(MOVING_BEHAVIOUR);
			slowMotion = false;
		}
	}
	


	/**
	 * If the Zombie is in slow motion (lost one leg), make sure the zombie can only move 
	 * every second turn.If slowMotion is true, check if the zombie behaviours attribute 
	 * has any behaviours which involve moving its location. 
	 * If yes, means in previous turn, zombie might probably moved, thus removing all 
	 * moving behaviours from the behaviour attributes for current zombie turn. 
	 * If no, means the zombie did not move in the previous turn, thus adding moving
	 * behaviour into the behaviour attributes for current zombie turn. 
	 * By doing so, we can make sure the zombie can only move in every second turn.
	 */	
	private void checkSlowMotion() {
		if (slowMotion) {
			if (behaviours.hasBehaviour(MOVING_BEHAVIOUR)) {
				behaviours.remove(MOVING_BEHAVIOUR);
			} else {
				behaviours.add(MOVING_BEHAVIOUR);
			}
		}
	}
	
	/**
	 * Check if zombie is in slow motion (left one leg) and return an action which can 
	 * be performed by the zombie in its current turn.
	 * 
	 * @param actions list of possible Actions
	 * @param lastAction previous Action, if it was a multiturn action
	 * @param map the map where the current Zombie is
	 * @param display the Display where the Zombie's utterances will be displayed
	 */
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		checkSlowMotion();
		return super.playTurn(actions, lastAction, map, display);
	}

}
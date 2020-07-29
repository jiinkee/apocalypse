package game;

import java.util.Objects;
import java.util.Random;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;


/**
 * Represents the human corpse in the Zombie game world.
 * Human corpse will revive as Zombie after 5 - 10 turns since the Human dies.
 * 
 * @author Kee Pei Jiin
 */
public class HumanCorpse extends PortableItem {
	/**Keeps track of the number of turns since the human corpse has been created*/
	private int turn = 0;
	/**Stores the number of turns required for human corpse to revive. This value differs for each HumanCorpse*/
	private int revive_turn;
	/**Constant that stores the minimum number of turns needed for human corpse to revive*/
	private final static int MIN_TURN = 5;
	/**Constant that stores the maximum number of turns needed for human corpse to revive*/
	private final static int MAX_TURN = 10;
	
	/**
	 * Constructor.
	 * 
	 * @param deadHumanName		The name of the dead human, will be used as the name of the Zombie when the HumanCorpse revives
	 */
	protected HumanCorpse(String deadHumanName) {
		super(deadHumanName, 'C');
		revive_turn = new Random().nextInt(MAX_TURN - MIN_TURN + 1) + MIN_TURN;
	}
	
	/**
	 * HumanCorpse can experience the passage of time when it is on the Ground.
	 * When the number of turns since human corpse was created reaches {@code revive_turn}, it will revives as a Zombie at the nearest location
	 * that does not have an Actor.
	 * 
	 * @param location	The location of the HumanCorpse
	 * @throws 			NullPointerException if location argument is null
	 */
	@Override
	public void tick(Location location) throws NullPointerException {
		Objects.requireNonNull(location);
		turn += 1;
		if (turn == revive_turn) {
			this.revive(location);
			location.removeItem(this);
		}
	}
	
	/**
	 * HumanCorpse can experience the passage of time when it is in an Actor's inventory.
	 * When the number of turns since human corpse was created reaches {@code revive_turn}, it will revives as a Zombie at the nearest location
	 * that does not have an Actor.
	 * 
	 * @param location	The location of the HumanCorpse
	 * @param actor		The actor that is carrying this human corpse
	 * @throws 			NullPointerException if location or actor argument is null
	 */
	@Override
	public void tick(Location location, Actor actor) throws NullPointerException {
		Objects.requireNonNull(location);
		Objects.requireNonNull(actor);
		turn += 1;
		if (turn == revive_turn) {
			this.revive(location);
			actor.removeItemFromInventory(this);
		}	
	}
	
	/**
	 * Creates a new Zombie and adds it at the nearest location that can be entered by an Actor.
	 * The Zombie can enter a location is the location is passable and does not contain another actor.
	 * 
	 * @param corpseLoc	The location of the HumanCorpse
	 * @throws 			NullPointerException if corpseLoc is null
	 */
	private void revive(Location corpseLoc) throws NullPointerException {
		Objects.requireNonNull(corpseLoc);
		
		Zombie revivedCorpse = new Zombie("Revived " + this.name);
		GameMap map = corpseLoc.map();
		
		Location suitableCorpseLoc = new SuitableLocation(map, corpseLoc, revivedCorpse).findSuitableLocation();
		map.addActor(revivedCorpse, suitableCorpseLoc);
	}
}
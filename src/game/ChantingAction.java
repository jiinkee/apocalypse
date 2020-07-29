package game;

import java.util.Objects;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * Represents the chanting action of priestess, Mambo Marie.
 * Every time Mambo Marie chants, 5 new Zombies will be created on the map Mambo Marie is currently on.
 * 
 * @author Kee Pei Jiin
 */
public class ChantingAction extends Action{
	/**Random number generator*/
	private Random rand = new Random();
	/**The number of zombies that will be created every time the chanting action is being carried out*/
	private static final int ZOMBIE_QUANTITY = 5;
	
	/**
	 * The actor (Mambo Marie) will chant and create 5 new Zombies at random locations of the map 
	 * the actor is currently on.
	 *
	 * @see 			Action#execute(Actor, GameMap)
	 * @param actor 	The actor performing the action.
	 * @param map 		The map the actor is on.
	 * @return 			A suitable description to display in the UI.
	 * @throws			NullPointerException if actor or map argument is null
	 */
	public String execute(Actor actor, GameMap map) throws NullPointerException{
		Objects.requireNonNull(actor);
		Objects.requireNonNull(map);
		
		// count keeps track of the number of Zombies that have already been created
		int count = 0;
		while (count < ZOMBIE_QUANTITY) {
			Zombie zombie = new Zombie(generateZombieName());
			Location randomLocation = generateZombieLoc(map);
			while (!randomLocation.canActorEnter(zombie)) {
				randomLocation = generateZombieLoc(map);
			}
			map.addActor(zombie, randomLocation);
			count++;
		}
		return menuDescription(actor);
	}
	
	/**
	 * Describe the action in a format suitable for displaying in the menu.
	 *
	 * @see 			Action#menuDescription(Actor)
	 * @param actor 	The actor performing the action.
	 * @return 			A string, e.g. "Mambo Marie chanted spells and five Zombie is created! Your doomday is near!"
	 * @throws 			NullPointerException if actor argument is null
	 */
	public String menuDescription(Actor actor) throws NullPointerException{
		Objects.requireNonNull(actor);
		return actor.toString() + " chanted spells and five Zombie is created! Your doomday is near!";
	}
	
	/**
	 * Generates random string to be used as the name of the new Zombie.
	 * 
	 * @return a random string which is the Zombie's name.
	 */
	private String generateZombieName() {
		// length of zombie name will be between 5 and 10 characters
		int nameLength = rand.nextInt(10 - 5 + 1) + 5;
		String alphabets = "abcdefghijklmnopqrstuvwxyz";
		StringBuffer name = new StringBuffer(nameLength);
			
		for (int i = 0; i < nameLength; i++) { 
			// generate a random number between 0 and the alphabets length
			int index = rand.nextInt(alphabets.length());
			name.append(alphabets.charAt(index));
        } 
		String zombieName = name.toString();
        return zombieName.substring(0, 1).toUpperCase() + zombieName.substring(1); 
    } 
	
	/**
	 * Generates random location where the new Zombie can be added onto the map.
	 * 
	 * @param map	The map where new Zombie will be added on
	 * @return		the location where the Zombie will be added onto the map
	 * @throws		NullPointerException when the map argument is null
	 */
	private Location generateZombieLoc(GameMap map) throws NullPointerException{
		Objects.requireNonNull(map);
		
		// make sure that the random x and y coordinates generated is within the map boundary range
		int randX = rand.nextInt(map.getXRange().max() - map.getXRange().min() + 1) + map.getXRange().min();
		int randY = rand.nextInt(map.getYRange().max() - map.getYRange().min() + 1) + map.getYRange().min();	
		Location randomLocation = map.at(randX, randY);	
		return randomLocation;
	}
}

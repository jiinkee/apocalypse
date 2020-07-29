package game;

import java.util.Objects;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * Represents the priestess, which is the source of the local zombie epidemic in the zombie game world.
 * In current game design, there is only one priestess names Mambo Marie currently.
 * 
 * @author Kee Pei Jiin
 */
public class Priestess extends ZombieActor{
	/**Keeps track of the number of turns that has passed since Mambo Marie's has appeared in the map*/
	private int appearTurn = 0;
	/**The probability for the Mambo Marie to spawn on the map every turn*/
	private static final int SPAWN_PROBABILITY = 5;
	/**The turn interval for Mambo Marie to chant and create more zombies*/
	private static final int CHANT_INTERVAL = 10;
	/**The turn interval for Mambo Marie to vanish after her appearance (given that she did not get killed)*/
	private static final int VANISH_INTERVAL = 30;
	
	/**
	 * Constructor.
	 * Creates a Priestess object using super() constructor, add WanderBehaviour to the protected behaviours
	 * attribute of its parent class (ZombieActor class) and adds the capability PRIESTESS to the Priestess instance.
	 */
	public Priestess(){
		super("Mambo Marie", 'M', 100, ZombieCapability.UNDEAD);
		this.addCapability(ZombieCapability.PRIESTESS);
		behaviours.add(new WanderBehaviour());	
	}
	
	/**
	 * Returns an empty Actions instance because Priestess cannot attack humans.
	 */
	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		return new Actions();
	}
	
	/**
	 * Overload the playTurn method in parent class (ZombieActor) because priestess needs to carry out specific action during specific turn, i.e.
	 * chants every 10 turns. At other turns, priestess will either wanders around or do nothing.
	 * This method also increments the attribute appearTurn by 1 every time it got executed.
	 * 
	 * @param actions 			list of possible Actions
	 * @param lastAction 		previous Action, if it was a multiturn action
	 * @param map 				the map where the current actor is
	 * @param display 			the Display where the actor's utterances will be displayed
	 * @return 					an Action that will be carried out by the actor during that turn
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display){
		appearTurn += 1;
		if (appearTurn % CHANT_INTERVAL == 0) {
			// chants every 10 turns
			return new ChantingAction();
		} 
		return super.playTurn(actions, lastAction, map, display);
	}
	
	/**
	 * Determines whether priestess will appear in the map during a particular turn.
	 * 
	 * @return true if the priestess will appear, false otherwise
	 */
	public boolean spawn() {
		Random rand = new Random();
		if (rand.nextDouble()*100 <= SPAWN_PROBABILITY) {
			return true;
		}
		return false;	
	}
	
	/**
	 * Returns the location where Priestess (Mambo Marie) will be added onto the destination map.
	 * 
	 * @param destinationMap	the map where Priestess will be appear on
	 * @return					the location where the Priestess will appear on {@code destinationMap}
	 * @throws					NullPointerException if {@code destinationMap} is null
	 */
	public Location spawnLocation(GameMap destinationMap) throws NullPointerException{
		Objects.requireNonNull(destinationMap);
		return new SuitableLocation(destinationMap, this).findSuitableLocation();
	}
	
	/**
	 * Getter method that allows other classes to retrieve the private attribute {@code VANISH_INTERVAL}.
	 * 
	 * @return an integer representing the number of turns taken for the priestess to vanish from map.
	 */
	public int getVanishInterval() {
		return VANISH_INTERVAL;
	}
	
	/**
	 * Getter method that allows other classes to retrieve the private attribute {@code appearTurn}.
	 * 
	 * @return an integer representing the number of turns that priestess has been on map since her appearance
	 */
	public int getAppearTurn() {
		return appearTurn;
	}
	
	/**
	 * Setter method that resets the private attribute {@code appearTurn} to its initial value, 0.
	 */
	public void resetAppearTurn() {
		appearTurn = 0;
	}
	
}
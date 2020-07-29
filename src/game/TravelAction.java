package game;

import java.util.Objects;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * Represents the Player’s action to travel between the compound map and town map.
 * 
 * @author Kee Pei Jiin
 */
public class TravelAction extends Action{
	/**The map that player will reach after travelling*/
	private ZombieGameMap destinationMap;
	/**The name of the destination map*/
	private String destinationName;
	
	/**
	 * Constructor.
	 * 
	 * @param destination	The map that Player will reach after travelling
	 * @throws				NullPointerException if destination argument is null
	 */
	public TravelAction(ZombieGameMap destination) throws NullPointerException{
		Objects.requireNonNull(destination);
		destinationMap = destination;
		destinationName = destination.getMapName();
	}
	
	/**
	 * The Player will be transported to the destination map at the same x,y coordinates if possible.
	 * If the location has already been occupied, then the Player will be transported to the nearest possible location.
	 * 
	 * @see 					Action#execute(Actor, GameMap)
	 * @param actor 			The actor performing the action.
	 * @param actorCurrentMap 	The map the actor is on.
	 * @return 					A suitable description to display in the UI
	 * @throws 					NullPointerException if actor or map argument is null
	 */
	public String execute(Actor actor, GameMap actorCurrentMap) throws NullPointerException{
		Objects.requireNonNull(actor);
		Objects.requireNonNull(actorCurrentMap);
		
		Location actorCurrentLoc = actorCurrentMap.locationOf(actor);
		Location expectedActorNewLoc = destinationMap.at(actorCurrentLoc.x(), actorCurrentLoc.y());
		Location suitablePlayerLoc = new SuitableLocation(destinationMap, expectedActorNewLoc, actor).findSuitableLocation();
		actorCurrentMap.removeActor(actor);
		destinationMap.addActor(actor, suitablePlayerLoc);
		
		return menuDescription(actor);
	}
	
	/**
	 * Describe the action in a format suitable for displaying in the menu.
	 *
	 * @see 			Action#menuDescription(Actor)
	 * @param actor 	The actor performing the action.
	 * @return 			A string, e.g. "travels to town map"
	 * @throws 			NullPointerException if actor argument is null
	 */
	public String menuDescription(Actor actor) throws NullPointerException{
		Objects.requireNonNull(actor);
		return "travels to " + destinationName + " map";
	}
	
}

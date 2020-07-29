package game;

import java.util.Objects;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Special action for Zombie to growl, i.e. says something zombie-like. 
 * 
 * @author Kee Pei Jiin
 */
public class GrowlAction extends Action{
	/**Represents the word Zombie says when they growl*/
	private String growl = "Braaaaainsssssssss";
	
	/**
	 * Zombie growls.
	 * 
	 * @see 			Action#execute(Actor, GameMap)
	 * @param actor 	The actor performing the action.
	 * @param map 		The map the actor is on.
	 * @return 			A suitable description to display in the UI
	 * @throws 			NullPointerException if actor argument is null
	 */
	@Override
	public String execute(Actor actor, GameMap map) throws NullPointerException {
		Objects.requireNonNull(actor);
		return menuDescription(actor);
	}
	
	/**
	 * Describe the action in a format suitable for displaying in the menu.
	 *
	 * @see 			Action#menuDescription(Actor)
	 * @param actor 	The actor performing the action.
	 * @return 			A string, e.g. "Zombie says Braaaaainsssssssss"
	 * @throws 			NullPointerException if actor argument is null
	 */
	@Override
	public String menuDescription(Actor actor) throws NullPointerException {
		Objects.requireNonNull(actor);
		return actor + " says " + growl;
	}
	
}

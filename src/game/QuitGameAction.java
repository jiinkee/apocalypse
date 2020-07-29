package game;

import java.util.Objects;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Special class for Player to quit the game voluntarily.
 * 
 * @author Kee Pei Jiin
 */
public class QuitGameAction extends Action{
	/**
	 * Quit the game. Only Player can choose to quit the game earlier.
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
		
		actor.addCapability(ZombieCapability.QUIT_GAME);
		return menuDescription(actor);
	}	
	
	/**
	 * Describe the action in a format suitable for displaying in the menu.
	 *
	 * @see 			Action#menuDescription(Actor)
	 * @param actor 	The actor performing the action.
	 * @return 			A string, e.g. "Player chooses to quit game"
	 * @throws 			NullPointerException if actor is null
	 */
	public String menuDescription(Actor actor) throws NullPointerException{
		Objects.requireNonNull(actor);
		
		return "quit game";
	};
	
	/**
	 * Returns the key that will be displayed in the menu and be used to trigger this action.
	 * 
	 * @return the hotkey associated with this action, which is "0".
	 */
	@Override
	public String hotkey() {
		return "0";
	}
}

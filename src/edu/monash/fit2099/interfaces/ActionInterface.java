package edu.monash.fit2099.interfaces;

import edu.monash.fit2099.engine.Actions;

/**
 * This interface provides the ability to add methods to Action, without modifying code in the engine,
 * or downcasting references in the game.   
 * 
 * @author Tan Wei Li
 */
public interface ActionInterface {
	
	/**
	 * Get a list of next actions that can be performed followed by current action.
	 * By default, it returns null which indicate no specific Action that can be performed
	 * after this Action.
	 *  
	 * @return A list of Action which can be performed followed by current action.
	 */
	default Actions getNextActions() {
		return null;
	}

}

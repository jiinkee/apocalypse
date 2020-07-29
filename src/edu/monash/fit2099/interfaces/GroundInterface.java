package edu.monash.fit2099.interfaces;

/**
 * This interface provides the ability to add methods to Ground, without modifying code in the engine,
 * or downcasting references in the game. 
 *   
 * @author Kee Pei Jiin
 */
public interface GroundInterface {
	/**
	 * A default method that fertilise the ground.
	 * Only Crop can be fertilised, hence for all other grounds, fertilise() is a method without implementation.
	 */
	default void fertilise() {
	}
}

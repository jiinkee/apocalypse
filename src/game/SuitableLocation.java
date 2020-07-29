package game;

import java.util.Objects;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * This class is responsible for finding a suitable location to add an Actor onto a map.
 * This class is necessary because it is inevitable that the expected new location of the actor has already been occupied by the other actor.
 * Under this circumstance, the nearest location where the actor can be added needs to be determined.
 * 
 * @author Kee Pei Jiin
 */
public class SuitableLocation {
	/**The map which the actor will be added to*/
	private GameMap destinationMap;
	/**The new location that the actor is expected to be at*/
	private Location expectedNewLoc = null;
	/**The actor which needs to be added onto the map*/
	private Actor actorToAdd;
	
	/**
	 * Constructor that takes in only one location where the actor is expected to be at.
	 * 
	 * @param map				The map where the actor will be added onto
	 * @param newLocation		The location that the actor is expected to be at
	 * @param actor				The actor which needs to be added onto the map
	 * @throws					NullPointerException when any of the arguments (map, newLocation, actor) is null
	 */
	public SuitableLocation(GameMap map, Location newLocation, Actor actor) throws NullPointerException{
		Objects.requireNonNull(map);
		Objects.requireNonNull(newLocation);
		Objects.requireNonNull(actor);
		
		destinationMap = map;
		expectedNewLoc = newLocation;
		actorToAdd = actor;
	}
	
	/**
	 * Constructor that does not take in a location where the actor is expected to be at.
	 * This constructor should be used in the case where the actor is expected to be added at the edge of map.
	 * 
	 * @param map				The map where the actor will be added onto
	 * @param actor				The actor which needs to be added onto the map
	 * @throws 					NullPointerException when any of the arguments, map or actor is null
	 */
	public SuitableLocation(GameMap map, Actor actor) throws NullPointerException{
		Objects.requireNonNull(map);
		Objects.requireNonNull(actor);
		
		destinationMap = map;
		actorToAdd = actor;
	}
	
	/**
	 * Determines a suitable location for the actor to be added onto the map
	 * 
	 * @return the location where actor should be added onto the map
	 */
	public Location findSuitableLocation() {
		if (expectedNewLoc != null) {
			return suitableLocAroundExpectedLoc();
		} else {
			return suitableLocAlongBorder();
		}
	}
	
	/**
	 * Determines whether actor can be added to the map at the given actor's expected location.
	 * If the given location is not suitable, the method will determine and return the nearest
	 * suitable location for the actor to be added onto the map.
	 * A suitable location is a location that does not contain any other actor and has a passable terrain.
	 * 
	 * @return a location where the actor will be added onto the map
	 * @throws RuntimeException when {@code expectedNewLoc} is null
	 */
	private Location suitableLocAroundExpectedLoc() throws RuntimeException{
		if (expectedNewLoc == null) {
			throw new RuntimeException("Method cannot be used if the actor's expected new location is not provided.");
		}
		
		// check if the actor's expected new location is suitable for actor to be added
		if (expectedNewLoc.canActorEnter(actorToAdd)) {
			return expectedNewLoc;
		}else {
			// determine the nearest surrounding location that is suitable for the actor to be added
			Location actorNewLoc = null;
			int n = 0;
			
			while (actorNewLoc == null) {
				n++;
				// offset values that will be added to the initial location's coordinates to obtain the coordinates of neighbour location
				int[][] surroundings = new int[][] {{-n, -n}, {0, -n}, {n, -n}, {-n, 0}, {n, 0}, {-n, n}, {0, n}, {n, n}};
				actorNewLoc = findNewLocation(expectedNewLoc, surroundings);
			}
			return actorNewLoc;
		}
	}
	
	/**
	 * Determines whether actor can be added to the map at the top left or bottom right corner of the map.
	 * If actor cannot be added to these two locations, this method will find and return a location along the map border
	 * which is suitable for actor to be added.
	 * A suitable location is a location that does not contain any other actor and has a passable terrain.
	 * 
	 * @return a location which is at/near the map border and actor will be added onto the map at this location
	 */
	private Location suitableLocAlongBorder() {
		Location topLeftCorner = destinationMap.at(destinationMap.getXRange().min(), destinationMap.getYRange().min());
		Location bottomRightCorner = destinationMap.at(destinationMap.getXRange().max(), destinationMap.getYRange().max());
		
		if (topLeftCorner.canActorEnter(actorToAdd)) {
			return topLeftCorner;
			
		} else if (bottomRightCorner.canActorEnter(actorToAdd)) {
			return bottomRightCorner;
			
		} else {
			Location actorNewLoc = null;
			int n = 0;
			
			while (actorNewLoc == null) {
				n++;
				// offset values that will be added to the coordinates of top left corner to obtain the coordinates of new location along map border
				int [][] topLeftBorders = new int [][] {{n, 0}, {0, n}};
				// offset values that will be added to the coordinates of bottom right corner to obtain the coordinates of new location along map border
				int [][] bottomRightBorders = new int [][] {{-n, 0}, {0, -n}};
				
				actorNewLoc = findNewLocation(topLeftCorner, topLeftBorders);
				
				if (actorNewLoc == null) {
					actorNewLoc = findNewLocation(bottomRightCorner, bottomRightBorders);
				}
			}
			return actorNewLoc;
		}		
	}
	
	/**
	 * This method is called when the given location where the actor is expected to be at is not suitable for actor to be added.
	 * This method continuously find the next nearest neighbour location and checks if the location is suitable for actor to be added
	 * until a suitable location has been found.
	 *
	 * @param startingLoc	The initial location where the actor is expected to be at.
	 * @param nearbyCoords	A list of x, y offset values. The coordinates of all the neighbour locations can be determined when these offset values
	 * 						are added to the coordinates of startingLoc.
	 * @return				A neighbour location that is suitable for actor to be added or null when none of the neighbour location is suitable
	 * @throws				NullPointerException when either startingLoc or nearbyCoords is null
	 */
	private Location findNewLocation(Location startingLoc, int[][] nearbyCoords) throws NullPointerException{
		Objects.requireNonNull(startingLoc);
		Objects.requireNonNull(nearbyCoords);
		
		for (int[] coordPair : nearbyCoords) {
			Location newLoc = computeNewLocation(startingLoc, coordPair);
			if (newLoc != null) {
				return newLoc;
			}
		}
		return null;
	}
	
	/**
	 * Computes the coordinates of neighbour location and determines if the location is suitable for actor to be added.
	 * 
	 * @param startingLoc	The initial location where the actor is expected to be at
	 * @param coordPair		A pair of offset values that will be added to the coordinates of strtingLoc to obtain the new neighbour location
	 * @return				The neighbour location if the location is suitable for actor to be added or null when the neighbour location is not suitable
	 * @throws				NullPointerException when either startingLoc or coordPair is null			
	 */
	private Location computeNewLocation(Location startingLoc, int[] coordPair) throws NullPointerException{
		Objects.requireNonNull(startingLoc);
		Objects.requireNonNull(coordPair);
		
		Location newLocation;
		int newX = startingLoc.x() + coordPair[0];
		int newY = startingLoc.y() + coordPair[1];
		// make sure the newly found neighbour location is still within the map boundary
		if (withinMapRange(newX, newY)) {
			newLocation = destinationMap.at(newX, newY);
			// checks if the newly found neighbour location is suitable for actor to be added
			if (newLocation.canActorEnter(actorToAdd)) {
				return newLocation;
			}
		}		
		return null;	
	}
	
	/**
	 * Determine if a location is within the X, Y range of the destination map.
	 * 
	 * @param x		x-coordinate of a location
	 * @param y		y-coordinate of a location 
	 * @return		true if the location coordinates are within the X, Y range of the map, false otherwise.
	 * @throws 		NullPointerException when either x or y is null.
	 */
	private boolean withinMapRange(int x, int y) throws NullPointerException{
		Objects.requireNonNull(x);
		Objects.requireNonNull(y);
		
		return x >= destinationMap.getXRange().min() & x <= destinationMap.getXRange().max() 
				& y >= destinationMap.getYRange().min() & y <= destinationMap.getYRange().max();
	}
}

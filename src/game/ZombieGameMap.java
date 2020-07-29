package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.GroundFactory;
import edu.monash.fit2099.engine.Location;

/**
 * Class that represents the game map in the Zombie game world.
 * 
 * @author Kee Pei Jiin
 */
public class ZombieGameMap extends GameMap{
	/**The name of the map, e.g. compound, town*/
	private String mapName;
	/**A list of actors that are currently on the map*/
	private ArrayList<Actor> actorsOnMap = new ArrayList<Actor>();

	/**
	 * Constructor.    
	 * 
	 * @param groundFactory	Factory to create Ground objects
	 * @param groundChar	Symbol that will represent empty Ground in this map
	 * @param width			Width of the GameMap, in characters
	 * @param height		Height of the GameMap, in characters
	 * @param name			Name of the map
	 */
	public ZombieGameMap(GroundFactory groundFactory, char groundChar, int width, int height, String name) {
		super(groundFactory, groundChar, width, height);
		mapName = name;
	}
	
	/**
	 * Constructor that creates a map from a sequence of ASCII strings.
	 * 
	 * @param groundFactory	Factory to create Ground objects
	 * @param lines			List of Strings representing rows of the map
	 * @param name			Name of the map
	 */
	public ZombieGameMap(GroundFactory groundFactory, List<String> lines, String name) {
		super(groundFactory, lines);
		mapName = name;
	}
	
	/**
	 * Constructor that reads a map from file.
	 *
	 * @param groundFactory Factory to create Ground objects
	 * @param mapFile       Name of a file containing an ASCII representation of a level
	 * @param name			Name of the map
	 * @throws 				IOException when file I/O fails
	 */
	public ZombieGameMap(GroundFactory groundFactory, String mapFile, String name) throws IOException {
		super(groundFactory, mapFile);
		mapName = name;
	}
	
	/**
	 * Add a new Actor at the given Location. Update {@code actorsOnMap} accordingly.
	 *
	 * @param actor the Actor to place
	 * @param location where to place the Actor
	 * @throws IllegalArgumentException if the Actor is already placed or there is already an Actor at the target Location
	 */
	@Override
	public void addActor(Actor actor, Location location) {
		super.addActor(actor, location);
		actorsOnMap.add(actor);
	}

	/**
	 * Remove an Actor from the system. Update {@code actorsOnMap} accordingly.
	 *
	 * @param actor the Actor to remove
	 */
	@Override
	public void removeActor(Actor actor) {
		super.removeActor(actor);
		actorsOnMap.remove(actor);
	}
	
	/**
	 * Getter method that allows other classes to retrieve the name of the map.
	 * 
	 * @return the name of the map
	 */
	public String getMapName() {
		return mapName;
	}
	
	/**
	 * Getter method that allows other classes to retrieve a list of actors that are on the map.
	 * 
	 * @return an unmodifiable list of actors that are on the map
	 */
	public List<Actor> getActorList(){
		return Collections.unmodifiableList(actorsOnMap);
	}

}

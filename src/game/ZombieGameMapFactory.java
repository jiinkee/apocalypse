package game;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Class that defines the models (size, terrain, actors etc.) of the maps in the Zombie game world.
 * Factory for creating maps of the game world.
 * 
 * @author Kee Pei Jiin
 */
public abstract class ZombieGameMapFactory{
	/**The map terrain expressed as list of strings.*/
	private List<String> map;
	/**A list of names of the humans that appear on the map.*/
	private List<String> humansOnMap;
	/**A HashMap that stores the names and location coordinates of all zombies on the map.*/
	private HashMap<String, Integer[]> zombiesOnMap;
	/**A HashMap that stores the names and location coordinates of all farmers on the map.*/
	private HashMap<String, Integer[]> farmersOnMap;
	/**The name of the map*/
	private String mapName;
	
	/**
	 * Takes in a string that describes the map terrain and assigns it to the private attribute, {@code map}.
	 * 
	 * @param mapString		a String that represents the terrain of the map
	 * @throws 				NullPointerException when the argument mapString is null
	 */
	protected void setMapString(String[] mapString) throws NullPointerException{
		Objects.requireNonNull(mapString);
		map = Arrays.asList(mapString);
	}
	
	/**
	 * Takes in a list of human names and assigns it to the private attribute, {@code humansOnMap}.
	 * 
	 * @param humanNames	a list of human names
	 * @throws 				NullPointerException when the argument humanNames is null
	 */
	protected void setHumans(String[] humanNames) throws NullPointerException{
		Objects.requireNonNull(humanNames);
		humansOnMap = Arrays.asList(humanNames);
	}
	
	/**
	 * Takes in a HashMap that contains zombie information and assigns the HashMap to the private attribute, {@code zombiesOnMap}.
	 * 
	 * @param zombies	a HashMap object that stores zombie name as keys and their location coordinates as values.
	 * 					The location coordinates must be in a list with the format [x-coordinate, y-coordinate].
	 * @throws 			NullPointerException when the argument zombies is null
	 */
	protected void setZombies(HashMap<String, Integer[]> zombies) throws NullPointerException{
		Objects.requireNonNull(zombies);
		zombiesOnMap = new HashMap<String, Integer[]>(zombies);
	}
	
	/**
	 * Takes in a HashMap that contains farmer information and assigns the HashMap to the private attribute, {@code farmersOnMap}. 
	 * 
	 * @param farmers	a HashMap object that stores farmer name as keys and their location coordinates as values.
	 * 					The location coordinates must be in a list with the format [x-coordinate, y-coordinate].
	 * @throws 			NullPointerException when the argument farmers is null
	 */
	protected void setFarmers(HashMap<String, Integer[]> farmers) throws NullPointerException{
		Objects.requireNonNull(farmers);
		farmersOnMap = new HashMap<String, Integer[]>(farmers);
	}
	
	/**
	 * Takes in a string, which is the name of the map and assigns it to the private attribute, {@code mapName}.
	 * 
	 * @param name		the name of the map
	 * @throws 			NullPointerException when the argument name is null
	 */
	protected void setMapName(String name) throws NullPointerException{
		Objects.requireNonNull(name);
		mapName = name;
	}
	
	/**
	 * Getter method that allows the other classes to access the private attribute, {@code map}.
	 * 
	 * @return an unmodifiable list of strings that represents the map terrain
	 * @throws NullPointerException	when {@code map} is null
	 */
	protected List<String> getMapString() throws NullPointerException{
		Objects.requireNonNull(map);
		return Collections.unmodifiableList(map);
	}
	
	/**
	 * Getter method that allows the other classes to access the private attribute, {@code humansOnMap}.
	 * 
	 * @return an unmodifiable list of strings that represent the human names
	 * @throws NullPointerException	when {@code humansOnMap} is null
	 */
	protected List<String> getHumans() throws NullPointerException{
		Objects.requireNonNull(humansOnMap);
		return Collections.unmodifiableList(humansOnMap);
	}
	
	/**
	 * Getter method that allows the other classes to access the private attribute, {@code zombiesOnMap}.
	 * 
	 * @return an unmodifiable Map object that contains all the zombies' names and their location coordinates
	 * @throws NullPointerException	when {@code zombiesOnMap} is null
	 */
	protected Map<String, Integer[]> getZombies() throws NullPointerException{
		Objects.requireNonNull(zombiesOnMap);
		return Collections.unmodifiableMap(zombiesOnMap);
	}
	
	/**
	 * Getter method that allows the other classes to access the private attribute, {@code farmersOnMap}.
	 * 
	 * @return an unmodifiable Map object that contains all the farmers' names and their location coordinates
	 * @throws NullPointerException	when {@code farmersOnMap} is null
	 */
	protected Map<String, Integer[]> getFarmers() throws NullPointerException{
		Objects.requireNonNull(farmersOnMap);
		return Collections.unmodifiableMap(farmersOnMap);
	}
	
	/**
	 * Getter method that allows the other classes to access the private attribute, {@code mapName}.
	 * 
	 * @return the name of the map
	 * @throws NullPointerException when {@code mapName} is null
	 */
	protected String getMapName() throws NullPointerException{
		Objects.requireNonNull(mapName);
		return mapName;
	}

}

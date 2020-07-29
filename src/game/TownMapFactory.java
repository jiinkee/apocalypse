package game;

import java.util.HashMap;

/**
 * Factory for creating town map.
 * Defines the terrain of town map and the actors which appear on it.
 * 
 * @author Kee Pei Jiin
 * @author Tan Wei Li
 */
public class TownMapFactory extends ZombieGameMapFactory{
	
	/**
	 * Constructor.
	 * Call setter methods in ZombieGameMapFactory class to define the map size, terrain and all the actors on town map.
	 */
	protected TownMapFactory() {
		// name of the map.
		super.setMapName("town");
		
		// map in the town level (80X20)
		String[] mapString = new String[] {
				"................................................................................",
				"..............................#############.....................................",
				"..............................#...........#.....................................",
				"....................###########...........#############.........................",
				"....................#.........#...........#...........#.........................",
				"....................#.........#...........#...........#.........................",
				"...........+....+...#.........#######.#####...........#.........................",
				"..............+..+.+#.................................#.........................",
				"...........+....+.....................................#.........................",
				"..............+..+.+#.................................#.........................",
				"...........+....+...#.................................#.........................",
				"..............+...+.#.................................#..+..+...................",
				"....................#.................................#+...+....................",
				"....................#....................................+..+...................",
				"....................#.................................#+...+....................",
				"....................###################################..+..+...................",
				"................................................................................",
				"................................................................................",
				"................................................................................",
				"................................................................................",

		};
		super.setMapString(mapString);
		
		// humans on town map
		String[] humans = {"Kaguya", "Chika", "Shirogane","Iino", "Ishigami"};
		super.setHumans(humans);
		
		// zombies on town map
		HashMap<String, Integer[]> zombies = new HashMap<String, Integer[]>();
		zombies.put("Behemoth", new Integer[] {9,1});
		zombies.put("Judges", new Integer[] {72,1});
		zombies.put("Bahamut", new Integer[] {4,10});
		zombies.put("Sin", new Integer[] {10,19});
		zombies.put("Kefka", new Integer[] {60,19});
		zombies.put("Sephiroth", new Integer[] {60,4});
		zombies.put("Gilgamesh", new Integer[] {38,16});
		zombies.put("Cactaur", new Integer[] {35,0});
		super.setZombies(zombies);
		
		// farmers on compound map
		HashMap<String, Integer[]> farmers = new HashMap<String, Integer[]>();
		farmers.put("Miyamura", new Integer[] {31,2});
		super.setFarmers(farmers);
	}
}

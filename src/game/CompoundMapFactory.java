package game;

import java.util.HashMap;

/**
 * Factory for creating compound map.
 * Defines the terrain of compound map and the actors which appear on it.
 * 
 * @author Kee Pei Jiin
 */
public class CompoundMapFactory extends ZombieGameMapFactory{
	
	/**
	 * Constructor.
	 * Call setter methods in ZombieGameMapFactory class to define the map size, terrain and all the actors on compound map.
	 */
	protected CompoundMapFactory() {
		// name of the map
		super.setMapName("compound");
		
		// map in the compound level
		String[] mapString = new String[] {
				"................................................................................",
				"................................................................................",
				"....................................##########..................................",
				"..........................###########........#####..............................",
				"............++...........##......................########.......................",
				"..............++++.......#..............................##......................",
				".............+++...+++...#...............................#......................",
				".........................##..............................##.....................",
				"..........................#...............................#.....................",
				".........................##...............................##....................",
				".........................#...............................##.....................",
				".........................###..............................##....................",
				"...........................####......................######.....................",
				"..............................#########.........####............................",
				"............+++.......................#.........#...............................",
				".............+++++....................#.........#...............................",
				"...............++........................................+++++..................",
				".............+++....................................++++++++....................",
				"............+++.......................................+++.......................",
				"................................................................................",
				".........................................................................++.....",
				"........................................................................++.++...",
				".........................................................................++++...",
				"..........................................................................++....",
				"................................................................................"
		};
		super.setMapString(mapString);
		
		// humans on compound map
		String[] humans = {"Carlton", "May", "Vicente", "Andrea", "Wendy",
				"Elina", "Winter", "Clem", "Jacob", "Jaquelyn"};
		super.setHumans(humans);
		
		// zombies on compound map
		HashMap<String, Integer[]> zombies = new HashMap<String, Integer[]>();
		zombies.put("Groan", new Integer[] {30,20});
		zombies.put("Boo", new Integer[] {30,18});
		zombies.put("Uuuurgh", new Integer[] {10,4});
		zombies.put("Mortalis", new Integer[] {50,18});
		zombies.put("Gaaaah", new Integer[] {1,10});
		zombies.put("Aaargh", new Integer[] {62,12});
		zombies.put("UWU", new Integer[] {5,5});
		zombies.put("OvO", new Integer[] {70,23});
		super.setZombies(zombies);
		
		// farmers on compound map
		HashMap<String, Integer[]> farmers = new HashMap<String, Integer[]>();
		farmers.put("FarmerA", new Integer[] {70,15});
		farmers.put("FarmerB", new Integer[] {47,15});
		super.setFarmers(farmers);
	}
}

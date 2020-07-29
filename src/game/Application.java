package game;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.FancyGroundFactory;
import edu.monash.fit2099.engine.GameMap;

/**
 * The main class for the zombie apocalypse game.
 * 
 * @author Kee Pei Jiin
 * @author Tan Wei Li
 *
 */
public class Application {

	public static void main(String[] args) {
		// try and catch all the exceptions thrown in other classes
		try {
			ZombieWorld world = new ZombieWorld(new Display());
			ZombieGameMapFactory compound = new CompoundMapFactory();
			ZombieGameMapFactory town = new TownMapFactory();
			
			FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Fence(), new Tree());
			ZombieGameMap compoundMap = new ZombieGameMap(groundFactory, compound.getMapString(), compound.getMapName());
			ZombieGameMap townMap = new ZombieGameMap(groundFactory, town.getMapString(), town.getMapName());
			
			world.addGameMap(compoundMap);
			world.addGameMap(townMap);
			
			// add Player onto compound map
			Actor player = new Player("Player", '@', 100);
			world.addPlayer(player, compoundMap.at(42, 15));
			
			// add Actors and Items onto compound map
			addHumansOntoMap(compoundMap, compound.getHumans());
			addZombiesOntoMap(compoundMap, compound.getZombies());
			addFarmersOntoMap(compoundMap, compound.getFarmers());
			compoundMap.at(74, 20).addItem(new Plank());
			compoundMap.at(41, 15).addItem(new Shotgun());
			compoundMap.at(43, 16).addItem(new SniperRifle());
			compoundMap.at(45, 20).addItem(new AmmunitionBox());
			compoundMap.at(44, 10).addItem(new AmmunitionBox());

			// add Actors and Items onto town map
			addHumansOntoMap(townMap, town.getHumans());
			addZombiesOntoMap(townMap, town.getZombies());
			addFarmersOntoMap(townMap, town.getFarmers());
			townMap.at(45, 17).addItem(new Shotgun());
			townMap.at(6, 11).addItem(new Shotgun());
			townMap.at(51, 17).addItem(new SniperRifle());
			townMap.at(50, 18).addItem(new AmmunitionBox());
			townMap.at(19, 5).addItem(new AmmunitionBox());
			
			// add Vehicles onto compound and town map
			compoundMap.at(43, 17).addItem(new Vehicle(townMap));
			townMap.at(43,17).addItem(new Vehicle(compoundMap));
			
			world.run();
			
		} catch (NullPointerException NPE){
			NPE.printStackTrace();
		} catch (IllegalArgumentException EAE) {
			EAE.printStackTrace();
		} catch (IllegalStateException ESE){
			ESE.printStackTrace();
		} catch (Exception E) {
			E.printStackTrace();
		} 
	}
	
	/**
	 * Creates and add Human instances onto the game map.
	 * 
	 * @param gameMap	A map in the Zombie game world
	 * @param humans	A list of names of the humans that appear on {@code gameMap}
	 * @throws 			NullPointerException when the argument gameMap or humans is null
	 */
	private static void addHumansOntoMap(GameMap gameMap, List<String> humans) throws NullPointerException{
		Objects.requireNonNull(gameMap);
		Objects.requireNonNull(humans);
		
		int x, y;
		for (String name : humans) {
			do {
				x = (int) Math.floor(Math.random() * 20.0 + 30.0);
				y = (int) Math.floor(Math.random() * 7.0 + 5.0);
			} 
			while (gameMap.at(x, y).containsAnActor());
			gameMap.at(x,  y).addActor(new Human(name));	
		}
	}
	
	/**
	 * Creates and add Zombie instances onto the game map.
	 * 
	 * @param gameMap	A map in the Zombie game world
	 * @param zombies	A HashMap that contains the names and location coordinates of all the zombies in the {@code gameMap}
	 * @throws 			NullPointerException when the argument gameMap or zombies is null
	 */
	private static void addZombiesOntoMap(GameMap gameMap, Map<String, Integer[]> zombies) throws NullPointerException{
		Objects.requireNonNull(gameMap);
		Objects.requireNonNull(zombies);
		
		for (String name: zombies.keySet()) {
			int x = zombies.get(name)[0];
			int y = zombies.get(name)[1];
			gameMap.at(x,  y).addActor(new Zombie(name));
		}
	}
	
	/**
	 * Creates and add Farmer instances onto the game map.
	 * 
	 * @param gameMap	A map in the Zombie game world
	 * @param farmers	A HashMap that contains the names and location coordinates of all the farmers in the {@code gameMap}
	 * @throws 			NullPointerException when the argument gameMap or farmers is null
	 */
	private static void addFarmersOntoMap(GameMap gameMap, Map<String, Integer[]> farmers) throws NullPointerException{
		Objects.requireNonNull(gameMap);
		Objects.requireNonNull(farmers);
		
		for (String name: farmers.keySet()) {
			int x = farmers.get(name)[0];
			int y = farmers.get(name)[1];
			gameMap.at(x,  y).addActor(new Farmer(name));
		}
	}
}

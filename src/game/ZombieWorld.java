package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.World;

/**
 * Class that represents the Zombie game world.
 * 
 * @author Kee Pei Jiin
 */
public class ZombieWorld extends World{
	/**Boolean flag that is used to indicate whether the player has died*/
	private Boolean playerDead = false;
	/**Boolean flag that is used to indicate whether there's any human (excluding Player) left in the game*/
	private Boolean gotHuman;
	/**Boolean flag that is used to indicate whether there's any Zombie or Mambo Marie left in the game*/
	private Boolean gotZombie;
	/**A Priestess instance. Currently, there is only one priestess in the whole game world, that is, Mambo Marie*/
	private Priestess mamboMarie;
	
	/**
	 * Constructor.
	 * 
	 * @param display 					the Display that will display the game world.
	 * @throws NullPointerException		when display is null
	 */
	public ZombieWorld(Display display) throws NullPointerException{
		super(display);
	}
	
	/**
	 * Run the game.
	 *
	 * On each iteration the gameloop does the following: 
	 * - add/remove Mambo Marie depending on whether Mambo Marie has already appeared in map and Mambo Marie's alive status
	 * - displays the player's map
	 * - processes the actions of every Actor in the game, regardless of map
	 *
	 * We could either only process the actors on the current map, which would make
	 * time stop on the other maps, or we could process all the actors. We chose to
	 * process all the actors.
	 *
	 * @throws IllegalStateException if the player doesn't exist
	 */
	@Override
	public void run() {
		if (player == null)
			throw new IllegalStateException();
		
		mamboMarie = new Priestess();

		// initialize the last action map to nothing actions;
		for (Actor actor : actorLocations) {
			lastActionMap.put(actor, new DoNothingAction());
		}

		// This loop is basically the whole game
		while (stillRunning()) {
			GameMap playersMap = actorLocations.locationOf(player).map();
			playersMap.draw(display);

			// Process all the actors.
			for (Actor actor : actorLocations) {
				if (stillRunning())
					processActorTurn(actor);
			}
			
			// Tick over all the maps. For the map stuff.
			for (GameMap gameMap : gameMaps) {
				gameMap.tick();
			}
			
			// only attempt to spawn Mambo Marie when she did not appear in any map and alive
			if (!actorLocations.contains(mamboMarie) & !mamboMarie.hasCapability(ZombieCapability.DEAD) & mamboMarie.spawn()) {
				// add mamboMarie to the map
				Location mamboLocation = mamboMarie.spawnLocation(playersMap);
				playersMap.addActor(mamboMarie, mamboLocation);
				
			} else if (actorLocations.contains(mamboMarie) & mamboMarie.getAppearTurn() == mamboMarie.getVanishInterval()) {
				GameMap mamboMap = actorLocations.locationOf(mamboMarie).map();
				// should remove mamboMarie from the map she is on and not playersMap because player and Mambo Marie can be on different maps
				mamboMap.removeActor(mamboMarie);
				mamboMarie.resetAppearTurn();
			}
		}
		display.println(endGameMessage());
	}
		
	/**
	 * Returns true if the game is still running.
	 * The game is considered to still be running if the player is still around and there're still human in the maps.
	 * The game ends when the player chooses to quit game or when all the zombies including Mambo Marie were killed.
	 *
	 * @return true if game is still running
	 */
	@Override
	protected boolean stillRunning() {
		if (player.hasCapability(ZombieCapability.QUIT_GAME)) {
			return false;
		} else if (!actorLocations.contains(player)) {
			playerDead = true;
			return false;
		} else {
			gotHuman = false;
			gotZombie = false;
			ZombieGameMap compoundMap = findCompoundMap();
			for (Actor actor: compoundMap.getActorList()) {
				// check if there's any Human other than Player alive in the compound map
				if (actor.hasCapability(ZombieCapability.ALIVE) & !actor.hasCapability(ZombieCapability.PLAYER)) {
					gotHuman = true;
				} 
				// check if there's any Zombie/Mambo Marie alive in the compound map
				else if (actor.hasCapability(ZombieCapability.UNDEAD)) {
					gotZombie = true;
				}
			}
		}
		
		// player only wins when there is no Zombie left, and Mambo Marie was killed
		if (!gotZombie & mamboMarie.hasCapability(ZombieCapability.DEAD)) {
			return false;
		}
		if (!gotHuman) {
			return false;
		}		
		return true;
	}
	
	/**
	 * Return a string that can be displayed when the game ends. 
	 * The string returned is based on how the game ends.
	 * If the player is killed or all humans in all maps have been killed, player loses and game is over.
	 * If all zombies in all maps and Mambo Marie are killed, player wins and game is over.
	 *
	 * @return a suitable string to be displayed when the game ends.
	 */
	@Override
	protected String endGameMessage() {
		if (!gotZombie & mamboMarie.hasCapability(ZombieCapability.DEAD)) {
			return "player wins\nGame Over";
		}else if (playerDead || !gotHuman) {
			return "player loses\nGame Over";
		} 
		return "Game Over";	
	}
	
	/**
	 * Finds the compound map object from the gameMaps list.
	 * 
	 * @return object representing the compound map
	 */
	private ZombieGameMap findCompoundMap() {
		ZombieGameMap compoundMap = null;
		for (GameMap map: gameMaps) {
			if (((ZombieGameMap) map).getMapName() == "compound") {
				compoundMap = (ZombieGameMap) map;
			}
		}
		return compoundMap;
	}
}

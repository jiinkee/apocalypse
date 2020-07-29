package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.Menu;

/**
 * A class which represents shooting action which allows player to choose shooting direction
 * from a sub menu and results in area effect damage to more than one target based on specific miss chance.
 * 
 * @author Tan Wei Li
 *
 */
public class ShootingAction extends Action{
	/**a LongRangedWeapon object used in shooting action*/
	private LongRangedWeapon shotgun;
	/**an Exit object used to represent direction of shooting action*/
	private Exit shootingDirection;
	/**a constant integer of 25 which represents the miss chance of shooting action*/
	private static final int SHOOTING_MISS_CHANCE = 25;
	
	/**
	 * Constructor.
	 * Specify the shotgun used in the ShootingAction instance.
	 * 
	 * @param shotgun	a LongRangedWeapon used in the shooting action
	 * @throws 			NullPointerException if shotgun is null
	 */
	public ShootingAction(LongRangedWeapon shotgun) throws NullPointerException {
		Objects.requireNonNull(shotgun);
		this.shotgun = shotgun;
	}

	/**
	 * Constructor.
	 * Specify both the shotgun and shooting direction used in the ShootingAction instance.
	 * 
	 * @param shotgun				a LongRangedWeapon used in the shooting action
	 * @param shootingDirection		the selected shooting direction
	 * @throws 						NullPointerException if shotgun or shootingDirection is null
	 */
	public ShootingAction(LongRangedWeapon shotgun, Exit shootingDirection) throws NullPointerException {
		Objects.requireNonNull(shotgun);
		Objects.requireNonNull(shootingDirection);
		this.shotgun = shotgun;
		this.shootingDirection = shootingDirection;
	}
	
	/**
	 * Get the directions that will form the area affected by the shooting action based
	 * on the shooter current locations and the shooting direction chosen.
	 *
	 * @param allPossibleDirection list of directions possible from shooter's location
	 * @param direction shooting direction chosen by player
	 * @return a list of directions Exits object which represents the directions that will form 
	 * 			the area affected by the shooting action. (eg: if player chose to shoot in North and if the
	 * 			current exits from the player location include North, North West and North East, this method
	 * 			will return a list of Exits which include these 3 exits. Else if the current exits from the 
	 * 			player location do not include North East, this method will return a list of Exits which include
	 * 			North and North West only.)
	 */
	private List<Exit> getAffectedDirections(List<Exit> allPossibleDirection, Exit direction){
		// a list which stores all the default 8 directions in order
		List<String> allDirName = Arrays.asList("North", "North-East", "East", "South-East", "South", "South-West", "West", "North-West");
		// a list used to get the main and adjacent direction
		List<Integer> range = Arrays.asList(-1,0,1);
		
		// get the index of the shooter chosen main direction based on its name in the allDirName list
		int mainDirIndex = allDirName.indexOf(direction.getName());

		// get the name of directions of the possible affected area
		List<String> dirRangeName = new ArrayList<String>();
		for (int i : range) {
			int index = mainDirIndex + i;
			if (index < 0) {
				index = index + allDirName.size();
			} else if (index >= allDirName.size()) {
				index = index - allDirName.size();
			}
			dirRangeName.add(allDirName.get(index));
		}
		
		// filter the name of direction of the actual affected area based on the directions available from shooter's current location 
		List<Exit> finalRangeDir = new ArrayList<Exit>();
		for (Exit dir: allPossibleDirection) {
			if (dirRangeName.contains(dir.getName())) {
				finalRangeDir.add(dir);
			}
		}
		return finalRangeDir;
	}
	
	/**
	 * Get the areas affected by the shooting action performed by shooter in specific direction. 
	 * 
	 * @param affectedDirection List of directions affected by shooting action
	 * @param mainDirection The direction of shooting chosen by shooter
	 * @param actorLocation The shooter current location
	 * @return a list of locations which represents the area being affected by the shooting action.
	 */
	private List<Location> getAffectedArea(List<Exit> affectedDirection, Exit mainDirection, Location actorLocation){
		// get main branch (3 grid in the direction chosen) from actor location
		List<Location> affectedArea = new ArrayList<Location>();
		List<Location> mainBranch = getBranchInDirectionOf(actorLocation, mainDirection, 3);
		
		// get adjacent branch (3 grid in the affected direction based on the main direction) from actor location
		affectedDirection.removeIf(dir -> dir.getName().equals(mainDirection.getName()));
		List<Location> adjacentBranch = new ArrayList<Location>();
		for (Exit dir: affectedDirection) {
			adjacentBranch.addAll(getBranchInDirectionOf(actorLocation, dir, 3));
		}
		
		// get the remaining location in the area between the main branch and adjacent branch
		// by calculating shorter adjacent branch from each location in the main branch
		List<Location> remainingArea = new ArrayList<Location>();
		int branchLength = 2;
		for (Location mainLocation: mainBranch) {
			for (Exit dir: affectedDirection) {
				remainingArea.addAll(getBranchInDirectionOf(mainLocation, dir, branchLength));
			}
			branchLength--;
		}
		
		// add all affected locations found
		affectedArea.addAll(mainBranch);
		affectedArea.addAll(adjacentBranch);
		affectedArea.addAll(remainingArea);
		
		// return the list of affected locations
		return affectedArea;
	}
	
	/**
	 * Get a path of specific length in specific direction starting from the location given (excluded start location).
	 * 
	 * @param startLocation location to start building path from
	 * @param direction direction to extend the path from start location
	 * @param length length of path
	 * @return a list of location which represents the path of specific length in specific direction starting from the 
	 * 			location given (excluded start location).
	 */
	private List<Location> getBranchInDirectionOf(Location startLocation, Exit direction, int length){
		List<Location> branch = new ArrayList<Location>();
		Location currentLocation = startLocation;
		for (int i=0; i<length;i++) {
			Location location = getLocationInDirectionOf(currentLocation, direction);
			if (location == null) {
				break;
			}
			branch.add(location);
			currentLocation = location;	
		}
		return branch;
	}
	
	/**
	 * Get a location in the specific direction from the location given.
	 * 
	 * @param location location used to get another location
	 * @param direction direction used to find another location
	 * @return a location in the specific direction from the location given if there is any, else return null
	 */
	private Location getLocationInDirectionOf(Location location, Exit direction) {
		for (Exit exit: location.getExits()) {
			if (exit.getName().equals(direction.getName())) {
				return exit.getDestination();
			}
		}
		return null;
	}
	
	
	/**
	 * Perform the shooting action.
	 * If the player choose to shoot, a sub menu will be displayed to allow Player to choose shooting direction,
	 * and all actors in the affected area of the shooting action will have chance to be attacked. Each time
	 * shooting, a bullet in the shotgun will be used up.
	 * 
	 * @see 			Action#execute(Actor, GameMap)
	 * @param actor 	The actor performing the action.
	 * @param map 		The map the actor is on.
	 * @return 			A suitable description to display in the UI
	 * @throws 			NullPointerException if actor or map argument is null
	 */
	@Override
	public String execute(Actor actor, GameMap map) throws NullPointerException{
		Actions actions = new Actions();
		Objects.requireNonNull(actor);
		Objects.requireNonNull(map);
		
		// create shooting action with each possible direction and append into Actions list
		Location actorLocation = map.locationOf(actor);
		List<Exit> possibleDirection = actorLocation.getExits();
		for (Exit direction : possibleDirection) {
			actions.add(new ShootingAction(this.shotgun, direction));
		}
		
		// show the shootingAction list in submenu created using menu
		Menu subMenu = new Menu();
		ShootingAction chosenShootingAction = (ShootingAction) subMenu.showMenu(actor, actions, new Display());
		
		// get the area affected by shooting action in the direction chosen
		Exit choosenDir = chosenShootingAction.shootingDirection;
		this.shootingDirection = choosenDir;
		List<Exit> affectedDir = chosenShootingAction.getAffectedDirections(possibleDirection, choosenDir);
		List<Location> affectedArea = chosenShootingAction.getAffectedArea(affectedDir, choosenDir, actorLocation);
		
		// create different attack action for actors in the affected area
		Actions attackActions = new Actions();
		for (Location location: affectedArea) {
			if (location.containsAnActor()) {
				Actor affectedActor = location.getActor();
				if (affectedActor.hasCapability(ZombieCapability.ALIVE)) {
					attackActions.add(new ZombieAttackAction(affectedActor, this.shotgun, SHOOTING_MISS_CHANCE));
				} else if (affectedActor.hasCapability(ZombieCapability.UNDEAD) || affectedActor.hasCapability(ZombieCapability.PRIESTESS)) {
					attackActions.add(new HumanAttackAction(affectedActor, this.shotgun, SHOOTING_MISS_CHANCE));
				}
			}
		}
		
		// shoot one time with the shotgun
		String resultString = "";
		resultString = this.shotgun.shoot();
		
		// execute the attackAction to each actor in the affected area
		resultString += "\n" + actor + "'s " + this.menuDescription(actor);
		for (Action attackAction: attackActions) {
			resultString += "\n" + attackAction.execute(actor, map);
		}
		
		return resultString;

	}

	/**
	 * Show general description of shooting action if the shooting direction is not yet chosen,
	 * else also include the shooting direction in description.
	 * 
	 * @see 			Action#menuDescription(Actor)
	 * @param actor		The actor performing the action.
	 * @return 			A string, e.g. "Player shoots in the direction of North"
	 * @throws			NullPointerException if actor is null
	 */
	public String menuDescription(Actor actor) throws NullPointerException {
		Objects.requireNonNull(actor);
		if (this.shootingDirection == null) {
			return actor + " uses " + this.shotgun;
		} else {
			return actor + " " + this.shotgun.verb() + " in direction of " + this.shootingDirection.getName();
		}
	}
	

}

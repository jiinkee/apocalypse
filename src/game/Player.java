package game;

import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.Menu;

/**
 * Class representing the Player.
 * 
 * @author Kee Pei Jiin
 * @author Tan Wei Li
 */
public class Player extends Human {

	private Menu menu = new Menu();

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(new String(name), displayChar, hitPoints);
		addCapability(ZombieCapability.PLAYER);
		addCapability(ZombieCapability.HARVEST);
	}

	/**
	 * Select and return an action to perform on the current turn. 
	 * Check if player is concentrating at his target in current turn and add suitable
	 * actions into menu to be chosen by player to perform for current turn.
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return the Action to be performed
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		
		if (this.aiming) {
			// if player takes other actions while aiming at target, set to indicate player lose concentration at target
			if (lastAction.getNextActions() == null) {
				this.aiming = false;
			} else {
				// if player is aiming at target, add AimingAction and/or SnippingAction based on last action taken if there is any
				actions.add(lastAction.getNextActions());
			}
		}
		
		// loop through items in inventory and get the allowable actions for items
		List<Item> items = this.getInventory();
		for (Item item: items) {
			actions.add(item.getAllowableActions(this, map));
		}
		Location here = map.locationOf(this);
		if (here.getGround().hasCapability(ZombieCapability.HARVEST)){
			actions.add(new HarvestAction(here.x(), here.y(), map));
		}
		// add quitGameAction into the actions list
		actions.add(new QuitGameAction());
		
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		
		return menu.showMenu(this, actions, display);
	}
	
	/**
	 * Do some damage to the Player.
	 *
	 * The player lose its concentration to target when being hurt.
	 *
	 * @param points number of hitpoints to deduct.
	 */
	public void hurt(int points) {
		super.hurt(points);
		this.aiming = false;
	}
	
}


package game;

import java.util.Objects;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * A class which represents action for crafting zombie arm or leg into club or mace.
 * 
 * @author Tan Wei Li
 */
public class CraftWeaponAction extends Action {
	/** Item to be crafted.*/
	private Item item;

	/**
	 * Constructor.
	 * Create a CraftWeaponAction with the specified item to be crafted
	 * 
	 * @param item Item to be crafted
	 * @throws NullPointerException if item argument is null
	 */
	public CraftWeaponAction(Item item) throws NullPointerException {
		Objects.requireNonNull(item);
		this.item = item;
	}
	
	/**
	 * Remove the item from the actor inventory and craft the item into
	 * different weapon based on its capability and add the weapon 
	 * back into actor inventory.
	 * 
	 * @param actor The actor performing the crafting action.
	 * @param map The map the actor is on.
	 * @return a description of what happened that can be displayed to the user.
	 * @throws NullPointerException if actor or map argument is null
	 */
	@Override
	public String execute(Actor actor, GameMap map) throws NullPointerException {
		Objects.requireNonNull(actor);
		Objects.requireNonNull(map);
		
		// remove item from actor inventory
		actor.removeItemFromInventory(item);
		
		String result = menuDescription(actor);
		
		// check if the item has capability to be crafted into club or mace
		Item newWeapon = null;
		if (item.hasCapability(ZombieCapability.CRAFTABLE_INTO_CLUB) ) {
			newWeapon = new Club();
		} else if (item.hasCapability(ZombieCapability.CRAFTABLE_INTO_MACE)) {
			newWeapon = new Mace();
		}
	
		// add new weapon into actor inventory
		if (newWeapon != null) {
			result += " into " + newWeapon;
			actor.addItemToInventory(newWeapon);
		}
		
		return result;
	}

	/**
	 * A string describing the crafting action for displaying in the UI menu.
	 *
	 * @param actor The actor performing the action.
	 * @return a String which describes the crafting action
	 * @throws NullPointerException if actor is null
	 */
	@Override
	public String menuDescription(Actor actor) throws NullPointerException {
		Objects.requireNonNull(actor);

		return actor + " crafts " + item;
	}
	
	
}

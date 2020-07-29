package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * A class which represents the limb that drops off from the Zombie
 *
 * @author Tan Wei Li
 */
public class Limb extends PortableItem {
	
	/**
	 * Create a limb object by passing the specified name and displayChar
	 * argument into PortableItem constructor and add the specified 
	 * capability for the limb
	 * 
	 * @param name the name of this Item
	 * @param displayChar the character to use to represent this item if it is on the ground
	 * @param capability the Capability to add to the limb
	 */
	public Limb(String name, char displayChar, Enum<?> capability) {
		super(new String(name), displayChar);
		addCapability(capability);
	}
	
	/**
	 * Return a list consisting of CraftWeaponAction only if the actor is Player and has
	 * the limb in his inventory. This is to make sure only Player can
	 * perform CraftWeaponAction on the limb and the limb must be picked up before 
	 * it is able to be crafted.
	 * 
	 * @return an unmodifiable list of Action
	 */
	public List<Action> getAllowableActions(Actor actor, GameMap map) {
		List<Action> action = new ArrayList<Action>();
		if (this.isInPlayerInventory(actor, map)) {
			action.add(new CraftWeaponAction(this));
			return Collections.unmodifiableList(action);
		}
		return Collections.unmodifiableList(action);
	}
		
}

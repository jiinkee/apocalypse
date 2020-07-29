package game;

import java.util.List;
import java.util.Objects;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Menu;

/**
 * A class which represents action for an actor to use a sniper rifle, by providing a sub menu
 * for actor to choose a target from the actor's current map and after choosing a target,
 * provide another sub menu for actor to choose action to perform on the target, either
 * aiming at target or shoot at target.
 * This action is only available if the actor is not focusing at a target.
 * 
 * @author Tan Wei Li
 *
 */
public class UseSniperAction extends Action {
	/**An Actor which acts as target of UseSniperAction*/
	private Actor target;
	/**a LongRangedWeapon used in UseSniperAction*/
	private LongRangedWeapon sniperRifle;
	/**an Action which represents the action chosen in the sub menu to perform on the target*/
	private Action lastSubmenuAction;

	/**
	 * Constructor.
	 * Specify the sniperRifle used in the UseSniperAction instance.
	 * 
	 * @param sniperRifle	a LongRangedWeapon used in the action
	 * @throws 				NullPointerException if sniperRifle is null
	 */
	public UseSniperAction(LongRangedWeapon sniperRifle) throws NullPointerException {
		Objects.requireNonNull(sniperRifle);
		this.sniperRifle = sniperRifle;
	}
	
	/**
	 * Constructor.
	 * Specify the sniperRifle used and the target in the UseSniperAction instance.
	 * 
	 * @param sniperRifle	a LongRangedWeapon used in the action
	 * @param target		actor that is the target of the sniper
	 * @throws NullPointerException if sniperRifle or target is null
	 */
	public UseSniperAction(LongRangedWeapon sniperRifle, Actor target) throws NullPointerException {
		Objects.requireNonNull(sniperRifle);
		Objects.requireNonNull(target);
		this.sniperRifle = sniperRifle;
		this.target = target;
	}

	/**
	 * Perform the UseSniperAction.
	 * Show a sub menu consists of all the other actos in the actor's current map for the
	 * actor to choose a target. Once a target is chosen, show another sub menu which 
	 * provides options for the actor to choose action to perform on the target (eg: aim at target/
	 * snipe at target). Once an action is chosen, the action will be executed and description
	 * string describing this process and the consequence of the action will be returned.
	 * 
	 * @see 			Action#execute(Actor, GameMap)
	 * @param actor 	The actor performing the action.
	 * @param map 		The map the actor is on.
	 * @return 			A suitable description to display in the UI
	 * @throws 			NullPointerException if actor or map argument is null
	 */
	@Override
	public String execute(Actor actor, GameMap map) throws NullPointerException {
		Actions actions = new Actions();
		Objects.requireNonNull(actor);
		Objects.requireNonNull(map);
		
		// create UseSniperAction with each possible actor excluding the actor performing the action
		// and append into Actions list
		List<Actor> possibleTargets = ((ZombieGameMap) map).getActorList();
		for (Actor possibleTarget: possibleTargets) {
			if (!(possibleTarget.hasCapability(ZombieCapability.PLAYER))) {
				actions.add(new UseSniperAction(this.sniperRifle, possibleTarget));
			}
		}
		
		// show the UseSniperAction list in submenu created using menu
		Menu subMenu = new Menu();
		Display display = new Display();
		UseSniperAction chosenUseSniperAction = (UseSniperAction) subMenu.showMenu(actor, actions, display);
		
		// get the target chosen by Player in the UseSniperAction
		Actor target = chosenUseSniperAction.target;
		
		// add AimingAction and SnippingAction to the target into another sub menu
		Actions actionsOnTarget = new Actions();
		actionsOnTarget.add(new AimingAction(target, this.sniperRifle));
		actionsOnTarget.add(new SnippingAction(target, this.sniperRifle));
		Action chosenActionOnTarget = subMenu.showMenu(actor, actionsOnTarget, display);
		
		// store the action chosen in the sub menu to keep track of the last action taken
		this.lastSubmenuAction = chosenActionOnTarget;
		
		// form a string which describe the actions
		String resultString = this.menuDescription(actor);
		resultString += "\n" + chosenActionOnTarget.execute(actor, map);
		
		return resultString;
	}

	/**
	 * Show general description of UseSniperAction. (eg: Player uses sniper rifle).
	 * Also show the target of the action if it is specified.
	 * 
	 * @see 			Action#menuDescription(Actor)
	 * @param actor 	The actor performing the action.
	 * @return 			A string, e.g. "Player uses sniper rifle on Zombie"
	 * @throws 			NullPointerException if actor is null
	 */
	@Override
	public String menuDescription(Actor actor)  throws NullPointerException {
		Objects.requireNonNull(actor);
		if (this.target == null) {
			return actor + " uses " + this.sniperRifle;
		} else {
			return actor + " uses " + this.sniperRifle + " on " + this.target;
		}
	}
	

	/**
	 * Return the next actions of the last action chosen from the sub menu created in
	 * UseSniperAction.
	 * @return Actions which consist of a list of next Action of the last action 
	 * chosen from the sub menu created in UseSniperAction.
	 */
	public Actions getNextActions() {
		if (this.lastSubmenuAction != null) {
			return lastSubmenuAction.getNextActions();
		}
		return null;
	}
	
}

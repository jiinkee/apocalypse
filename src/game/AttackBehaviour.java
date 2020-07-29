package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;

/**
 * A class that generates an AttackAction if the current Actor is standing
 * next to an Actor that they can attack.
 * 
 * @author ram
 * @author Tan Wei Li
 *
 */
public class AttackBehaviour implements Behaviour {
	/**The team (i.e. ZombieCapability) of the actor's target*/
	private ZombieCapability attackableTeam;
	
	/**
	 * Constructor.
	 * 
	 * Sets the team (i.e. ZombieCapability) that the owner of this
	 * Behaviour is allowed to attack.
	 * 
	 * @param attackableTeam Team descriptor for ZombieActors that can be attacked
	 * @throws NullPointerException if attackableTeam argument is null
	 */
	public AttackBehaviour(ZombieCapability attackableTeam) throws NullPointerException {
		Objects.requireNonNull(attackableTeam);
		this.attackableTeam = attackableTeam;
	}

	/**
	 * Returns an AttackAction that attacks an adjacent attackable Actor.
	 * Actors are attackable if their ZombieCapability matches the 
	 * "undeadness status" set 
	 * 
	 * @throws NullPointerException if actor or map argument is null
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) throws NullPointerException {
		Objects.requireNonNull(actor);
		Objects.requireNonNull(map);
		
		List<Exit> exits = new ArrayList<Exit>(map.locationOf(actor).getExits());
		Collections.shuffle(exits);
		
		for (Exit e: exits) {
			if (!(e.getDestination().containsAnActor()))
				continue;
			if (e.getDestination().getActor().hasCapability(attackableTeam)) {
				if (attackableTeam.equals(ZombieCapability.UNDEAD)) {
					return new HumanAttackAction(e.getDestination().getActor());
				} else {
					return new ZombieAttackAction(e.getDestination().getActor());
				}	
			}
		}
		return null;
	}
	

}

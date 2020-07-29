package game;

import java.util.Arrays;
import java.util.List;

/**
 * Class representing an ordinary human.
 * 
 * @author ram
 * @author Kee Pei Jiin
 */
public class Human extends ZombieActor {
	/**
	 * Creates a Human instance object using super() constructor and 
	 * add a Behaviours object consist of a list of Behaviour 
	 * (AttackBehaviour, EatingBehaviour and WanderBehaviour)  
	 * to protected behaviours attribute in its parent class which 
	 * is the ZombieActor class.
	 * 
	 * @param name the human's display name
	 */
	public Human(String name) {
		super(name, 'H', 50, ZombieCapability.ALIVE);
		addHumanBehaviours();
	}
	
	/**
	 * The protected constructor can be used to create subtypes
	 * of Human, such as the Player
	 * 
	 * @param name the human's display name
	 * @param displayChar character that will represent the Human in the map 
	 * @param hitPoints amount of damage that the Human can take before it dies
	 */
	protected Human(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints, ZombieCapability.ALIVE);
		addHumanBehaviours();	
	}
	
	/**
	 * Add all Human Behaviour into behaviours.
	 */
	private void addHumanBehaviours() {
		behaviours.add(new Behaviours(Arrays.asList(new Behaviour[] {
				new AttackBehaviour(ZombieCapability.UNDEAD),
				new EatingBehaviour(),
				new WanderBehaviour()
		})));
	}

	/**
	 * Insert a list of new Behaviour at specific positions of the existing Human behaviours.
	 * 
	 * @param index				position at which newBehaviours will be inserted
	 * @param newBehaviours		a List of Behaviours to be inserted into the existing Human behaviours
	 */
	protected void extendHumanBehaviours(int index, List<Behaviour> newBehaviours) {
		behaviours.add(index, newBehaviours);	
	}

}

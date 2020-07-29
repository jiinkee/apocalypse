package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * A tree that starts as a sapling and grows into a large tree.
 * 
 * @author ram
 * @author Kee Pei Jiin
 *
 */
public class Tree extends Ground {
	/**Keeps track of the number of turns since the Tree has been created*/
	private int age = 0;
	
	/**
	 * Constructor.
	 * 
	 * Each tree has three stages of life: sapling, small tree and large tree.
	 */
	public Tree() {
		super('+');
	}
	
	/**
	 * Tree experiences the passage of time.
	 * If the age of Tree reaches 10, it grows from sapling to small tree.
	 * If the age of Tree reaches 20, it grows from small tree to large tree.
	 */
	@Override
	public void tick(Location location) {
		super.tick(location);
		
		age ++;
		if (age == 10) {
			displayChar = 't';
		}
		if (age == 20) {
			displayChar = 'T';
		}
	}
}

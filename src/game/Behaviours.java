package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * A thin wrapper for <code>java.util.ArrayList&lt;Behaviour&gt;</code> that does not allow nulls to be added.
 * 
 * @author Tan Wei Li
 * @author Kee Pei Jiin
 *
 */
public class Behaviours implements Iterable<Behaviour> {
	
	/**
	 * An ArrayList of Behaviours objects
	 */
	private ArrayList<Behaviour> behaviours = new ArrayList<Behaviour>();

	/**
	 * Constructs an empty list.
	 */
	public Behaviours() {
	}
	
	/**
	 * Constructs a collection containing a list of (non-null) Behaviour.
	 * 
	 * @param behaviours the list of behaviour to add
	 * 
	 * @throws NullPointerException if behaviours is null
	 */
	public Behaviours(List<Behaviour> behaviours) throws NullPointerException {
		add(behaviours);
	}

	
	/**
	 * Appends the contents of another Behaviours list to this one.
	 * 
	 * @param behaviours the Behaviours to append
	 * 
	 * @throws NullPointerException if behaviours is null
	 */
	public void add(Behaviours behaviours) throws NullPointerException {
		Objects.requireNonNull(behaviours);
		
		for(Behaviour behaviour : behaviours) {
			add(behaviour);
		}
	}
	
	/**
	 * Appends the contents of any List&lt;Behaviour&gt; to this one.
	 * 
	 * This overload allows the use of an unmodifiableList to prevent privacy leaks.
	 * @param behaviours the List&lt;Behaviour&gt; to append
	 * 
	 * @throws NullPointerException if behaviours is null
	 */
	public void add(List<Behaviour> behaviours) throws NullPointerException {
		Objects.requireNonNull(behaviours);

		for(Behaviour behaviour : behaviours) {
			add(behaviour);
		}
	}
	
	/**
	 * Insert the behaviours in a List&lt;Behaviour&gt; at a specified position into this Behaviours.
	 * 
	 * @param index							position where newBehaviours should be inserted
	 * @param newBehaviours					the List&lt;Behaviour&gt; to be inserted to an existing Behaviours object
	 * @throws NullPointerException			if newBehaviours is null
	 * @throws IllegalArgumentException		if index given is out of range
	 */
	public void add(int index, List<Behaviour> newBehaviours) throws NullPointerException, IllegalArgumentException {
		Objects.requireNonNull(newBehaviours);
		Objects.requireNonNull(index);
		
		if (index > behaviours.size() || index < 0) {
			throw new IllegalArgumentException();
		}
		
		int j = index;
		for (int i=index; i < newBehaviours.size(); i++) {
			behaviours.add(i, newBehaviours.get(j));
			j++;
		}
	}

	/**
	 * Appends a single Behaviour to this collection, if it is non-null.  If it is null, then it is ignored.
	 * 
	 * @param behaviour the Behaviour to append
	 * @return true unconditionally
	 * @throws NullPointerException if behaviour argument is null
	 */
	public boolean add(Behaviour behaviour) throws NullPointerException {
		Objects.requireNonNull(behaviour);

		if (behaviour != null) {
			behaviours.add(behaviour);
		}
		return true;
	}

	/**
	 * Returns an Iterator for the underlying collection.
	 * 
	 * Implementing this method means that Behaviours implements the Iterable interface, which allows
	 * you to use it in a foreach, e.g. <code>for (Behaviour b: behaviours) {
	 *    ...
	 *    </code>
	 * 
	 * @return an iterator
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Behaviour> iterator() {
		return Collections.unmodifiableList(behaviours).iterator();
	}

	/**
	 * Remove the first occurrence of a Behaviour from the collection, if it is present.  
	 * If it is not present, the list is unchanged.
	 * 
	 * @param behaviour the Behaviour to remove
	 * @throws NullPointerException if behaviour is null
	 */
	public void remove(Behaviour behaviour) throws NullPointerException {
		Objects.requireNonNull(behaviour);

		behaviours.removeIf(b -> b.getClass() == behaviour.getClass());
	}
	
	/**
	 * Remove the first occurrence of each Behaviour in behaviours from the collection, if it is present.  
	 * If it is not present, the list is unchanged.
	 * 
	 * @param behaviours a list of behaviours to remove
	 * @throws NullPointerException if behaviours is null
	 */
	public void remove(Behaviours behaviours) throws NullPointerException {
		Objects.requireNonNull(behaviours);

		for(Behaviour behaviour : behaviours) {
			remove(behaviour);
		}
	}
	
	/**
	 * Check if the same instance class of behaviour exists in behaviours from the collection.
	 * 
	 * @param behaviour A behaviour to search in behaviours list
	 * @return True if same instance class of behaviour exists in behaviours from the collection, 
	 * else return false.
	 * @throws NullPointerException if behaviour is null
	 */
	public boolean contains(Behaviour behaviour) throws NullPointerException {
		Objects.requireNonNull(behaviour);

		for (Behaviour b: behaviours) {
			if (b.getClass() == behaviour.getClass()) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Check if at least one of the behaviour in the behaviours arguments exists in 
	 * behaviours from the collection.
	 * 
	 * @param behaviours A list of behaviour 
	 * @return True if at least one of the behaviour in behaviours list arguments has
	 * same instance class of one of the behaviour in behaviours from the collection, 
	 * else return false.
	 * @throws NullPointerException if behaviours is null
	 */
	public Boolean hasBehaviour(Behaviours behaviours) throws NullPointerException {
		Objects.requireNonNull(behaviours);

		Boolean has = false;
		for(Behaviour behaviour : behaviours) {
			has = has | contains(behaviour);
		}
		return has;
	}
}

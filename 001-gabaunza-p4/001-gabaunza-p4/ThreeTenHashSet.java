import java.util.Collection;
import java.util.Set;
import java.util.Iterator;

//You need to add javadocs to this class.
//You need to submit this file for grading.
//If you don't submit this for grading we will use
//a vanialla version which doesn't have javadocs.
//This will mean that your code won't pass the style checker.

//Remember that for interface methods with existing documentation
//(such as the java.util.Set interface), the documentation is already
//written, you just need to resuse that (don't copy-and-paste it,
//use inheritdoc).
/**
 * This class is the hashset which implements the set interface. The only difference is that it hashes its entries whenever inserting a new element into the set.
 * @param <E> The generic variable for the elements it holds.
 */
class ThreeTenHashSet<E> implements Set<E> {
	//********************************************************************************
	//   YOU MAY, BUT DON'T NEED TO EDIT THINGS IN THIS SECTION
	// These are some methods we didn't write for you, but you could write.
	// if you need/want them for building your graph. We will not test
	// (or grade) these methods.
	//********************************************************************************
	/**
	 * {@inheritDoc}
	 */
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	/**
	 * {@inheritDoc}
	 */
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	/**
	 * {@inheritDoc}
	 */
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}
	/**
	 * {@inheritDoc}
	 */
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}
	/**
	 * {@inheritDoc}
	 */
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object o) {
		throw new UnsupportedOperationException();
	}
	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		throw new UnsupportedOperationException();
	}
	
	//********************************************************************************
	//   DO NOT EDIT ANYTHING BELOW THIS LINE (except to add the JavaDocs)
	// We will grade these methods to make sure they still work, so don't break them.
	//********************************************************************************
	/**
	 * This is the map that contains all of the key-value pairs.
	 */
	private ThreeTenHashMap<E,E> storage = new ThreeTenHashMap<>(5);
	/**
	 * This method adds a new element to the collection.
	 * @param e a generic object that will be added to the collection.
	 * @return A boolean based on whether it was successfully added or not.
	 */
	public boolean add(E e) {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return storage.put(e, e) == null;
	}
	/**
	 * This method will clear all of the elements in the collection and just start over with a new collection that has no elements in it.
	 */
	public void clear() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		storage = new ThreeTenHashMap<>(5);
	}
	/**
	 * This method will go through the collection and see if the specified object is in the collection or not.
	 * @param o the object that is being added to the collection.
	 * @return A boolean based on whether the specified object is in the collection or not.
	 */
	public boolean contains(Object o) {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return storage.get(o) != null;
	}
	/**
	 * Will check the collection to see if it has any elements.
	 * @return A boolean based on whether the collection is empty or not.
	 */
	public boolean isEmpty() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return size() == 0;
	}
	/**
	 * This method will remove the specified object from the collection.
	 * @param o The specified object that teh user wishes to have removed from the collection.
	 * @return A boolean based on whether the specified ovject was successfully removed or not.
	 */
	public boolean remove(Object o) {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return storage.remove(o) != null;
	}
	/**
	 * Will return an int representing the number of elements that are currently in the collection.
	 * @return An int showing the number of elements that are currently in the collection.
	 */
	public int size() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return storage.size();
	}
	/**
	 * Will return an Array of the elements in the collection.
	 * @return The array filled with Objects.
	 */
	public Object[] toArray() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		ThreeTenHashMap.TableEntry[] arr = storage.toArray();
		Object[] ret = new Object[arr.length];
		for(int i = 0; i < arr.length; i++) {
			ret[i] = arr[i].key;
		}
		return ret;
	}
	/**
	 * Will return the string representation of the collection.
	 * @return A string representing the collection and the elements in it.
	 */
	public String toString(){
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return storage.toString();
	}
	/**
	 * Thie method will create and return an iterator for this collection.
	 * @return An iterator that works for this collection.
	 */
	public Iterator<E> iterator() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return new Iterator<E>() {
			private Object[] values = toArray();
			private int currentLoc = 0;
			
			@SuppressWarnings("unchecked")
			public E next() {
				return (E) values[currentLoc++];
			}
			
			public boolean hasNext() {
				return currentLoc != values.length;
			}
		};
	}
}
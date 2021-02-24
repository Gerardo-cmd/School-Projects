import java.util.Map;
import java.util.Set;

import java.util.Collection; //for returning in the values() function only

//If you uncomment the import to ArrayList below, the grader will manually
//look to make sure you are not using it anywhere else... if you use it
//somewhere else you will get 0pts on the entire project (not a joke).

//uncomment the line below ONLY if you are implementing values().
//import java.util.ArrayList; //for returning in the values() function only
/**
 * This is the hashmap class. It implements the map interface.
 * @param <K> The generic varibale for the key attribute.
 * @param <V> The generic variable for the value attribute.
 */
class ThreeTenHashMap<K,V> implements Map<K,V> {
	//********************************************************************************
	//   DO NOT EDIT ANYTHING IN THIS SECTION (except to add the JavaDocs)
	//********************************************************************************
	/**
	 * This is the array of Nodes that will be used in this class.
	 */
	//you must use this storage for the hash table
	//and you may not alter this variable's name, type, etc.
	private Node<K,V>[] storage;
	/**
	 * This int represents he number of elements that are currently in the table.
	 */
	//you must use to track the current number of elements
	//and you may not alter this variable's name, type, etc.
	private int numElements = 0;
	
	//********************************************************************************
	//   YOUR CODE GOES IN THIS SECTION
	//********************************************************************************
	/**
	 * This variable will keep track of what the original size of the storage is.
	 */
	private int originalSize;
	/**
	 * This is the constructor for this class It takes in the size which will provide the number of "slots" available.
	 * @param size An int representing the number of slots in the table.
	 */
	@SuppressWarnings("unchecked")
	public ThreeTenHashMap(int size) {
		//Create a hash table where the size of the storage is
		//the provided size (number of "slots" in the table)
		//You may assume size is >= 1
		
		//Remember... if you want an array of ClassWithGeneric<V>, the following format ___SHOULD NOT___ be used:
		//         ClassWithGeneric<V>[] items = (ClassWithGeneric<V>[]) new Object[10];
		//instead, use this format:
		//         ClassWithGeneric<V>[] items = (ClassWithGeneric<V>[]) new ClassWithGeneric[10];
		this.storage = (Node<K,V>[]) new Node[size];
		this.originalSize = size;
	}
	/**
	 * This method will clear the whole table and it's elements and return to the original size it had when it was created.
	 */
	@SuppressWarnings("unchecked")
	public void clear() {
		this.storage = (Node<K,V>[]) new Node[this.originalSize];
		return;
	}
	/**
	 * This method will check to see if the table currently is empty or not.
	 * @return boolean That will display "true" if it is empty or "false" if it is not empty.
	 */
	public boolean isEmpty() {
		return (this.numElements == 0);
	}
	/**
	 * This method will tell how many total "slots" are in the table currently.
	 * @return An int representing the number of total "slots" in the table.
	 */
	public int getSlots() {
		//return how many "slots" are in the table
		//O(1)
		return this.storage.length;
	}
	/**
	 * This method will return the number of elements that are currently in the table.
	 * @return An int that represents the number of elements currently in the table.
	 */
	public int size() {
		//return the number of elements in the table
		//O(1)
		return this.numElements;
	}
	/**
	 * This method will take a key and use the map to return the value associated with the key.
	 * @param key The key in the map that has a value associated with it.
	 * @return The value that is associated with the key that was given.
	 */
	public V get(Object key) {
		//Given a key, return the value from the table.
		
		//If the value is not in the table, return null.
		
		//Worst case: O(n), Average case: O(1)
		int hashID = Math.abs(key.hashCode());
		hashID = hashID % (storage.length);
		if (hashID < 0){ //If it's still negative cause it's equal to the value of Integer.MIN_VALUE, it will multiply it by negative one to turn it positive.
			hashID *= -1;
		}
		Node<K,V> current;
		if (storage[hashID] == null){//Checks to make sure there is at least a node in the slot.
			return null;
		}
		current = storage[hashID];
		while (current != null){//Will traverse through all the nodes until it finds the key or it reaches the last node.
			if (current.entry.key.equals(key)){
				return current.entry.value;
			}
			current = current.next;
		}
		return null; //Key could not be found.
	}
	/**
	 * Returns a set of the keys that are in this map.
	 * @return The set that holds the keys in this map.
	 */
	public Set<K> keySet() {
		//O(n+m) or better, where n is the size and m is the
		//number of slots
		
		//Hint: you aren't allowed to import
		//anything, but a ThreeTenHashSet is a Set
		ThreeTenHashSet<K> keysSet = new ThreeTenHashSet<K>();
		Node<K,V> current;
		for (int i = 0; i < this.storage.length; i++){
			current = storage[i];//Sets the currentnode based on which slot it is in.
			while(current != null){//Will traverse through the list in the slot and add all the keys to the set.
				keysSet.add(current.entry.key);
				current = current.next;
			}
		}
		return keysSet;
	}
	/**
	 * This will return the given key, and value associated with it, from the map.
	 * @param key The object that will be removed from the map.
	 * @return the key, and therefore the value as well, that was just removed from the map.
	 */
	public V remove(Object key) {
		//Remove the given key (and associated value)
		//from the table. Return the value removed.		
		//If the value is not in the table, return null.
		
		//Hint: Remember there are no tombstones for
		//separate chaining! Don't leave empty nodes!
		
		//Worst case: O(n), Average case: O(1)
		int hashID = Math.abs(key.hashCode());
		hashID = hashID % (storage.length);
		if (hashID < 0){ //If it's still negative cause it's equal to the value of Integer.MIN_VALUE, it will multiply it by negative one to turn it positive.
			hashID *= -1;
		}
		Node<K,V> temp;
		Node<K,V> current;
		if (storage[hashID] == null){//Checks to make sure there's even a node.
			return null;
		}
		if (storage[hashID].entry.key.equals(key)){//We will then check the first node.
			temp = storage[hashID];
			storage[hashID] = storage[hashID].next;
			this.numElements -= 1;
			return temp.entry.value;
		}
		current = storage[hashID];
		while (current.next != null){//And now we will traverse through the rest of the nodes
			if (current.next.entry.key.equals(key)){
				temp = current.next;
				current.next = temp.next;
				temp.next = null;
				this.numElements -= 1;
				return temp.entry.value;
			}
			current = current.next;
		}
				
		return null; //If the key was not found we just return null.
	}
	/**
	 * This will associate the specified value with the specified key in this map.
	 * @param key The key that the user wishes to associate with the value.
	 * @param value The value that is associated with the key.
	 * @return The value that is associated with the specified key in this operation.
	 */
	private V putNoExpand(K key, V value) {
		//Place value v at the location of key k.
		//Use separate chaining if that location is in use.
		//You may assume both k and v will not be null.
		//This method does NOT handle issues with the load,
		//that is handled by put(K,V) in the provided section
		//The return value is the same as specified by put()
		//(see the Map interface).
		
		//Hint 1: Make a TableEntry to store in storage
		//and use the absolute value of k.hashCode() for
		//the location of the entry.
		
		//Here's something you may want to know about Math's
		//absolute value method:
		//	"If the argument is equal to the value of Integer.MIN_VALUE,
		//  the most negative representable int value, the result is
		//  that same value, which is negative." -Oracle Docs
		
		//Hint 2: Remember that you're dealing with an array
		//of linked lists in this part of the project, not
		//an array of table entries.
		
		//If the key already exists in the table
		//replace the current value with v.
		
		//If the key does not exist in the table, add
		//the new node to the _end_ of the linked list.
		
		//This method is used by the provided put() and
		//rehash() methods, so if those aren't working,
		//look here!
		
		//Worst case: O(n) where n is the number
		//of items in the list, NOT O(m) where m
		//is the number of slots, and NOT O(n+m)
		TableEntry<K,V> entryNewNode = new TableEntry(key, value);
		int hashID = Math.abs(key.hashCode());
		hashID = hashID % (this.storage.length);
		if (hashID < 0){ //If it's still negative cause it's equal to the value of Integer.MIN_VALUE, it will multiply it by negative one to turn it positive.
			hashID = 0;
		}
		Node<K,V> currentNode;
		if (storage[hashID] == null) {
			storage[hashID] = new Node<K,V>(entryNewNode);
			this.numElements += 1;
			return null;
		}
		currentNode = storage[hashID];
		while (currentNode != null){//Will traverse through the whole list of nodes to find the key and then assign it the new value
			if (currentNode.entry.key.equals(key)){
				currentNode.entry.value = value;
				return currentNode.entry.value;
			}
			if (currentNode.next == null){//If it reaches the last node and still hasn't found it...
				break;
			}
			currentNode = currentNode.next;
		}
		currentNode.next = new Node<K,V>(entryNewNode);//Then it will create a new node at the end.
		this.numElements += 1;
		return null;
	}
	
	//--------------------------------------------------------
	// testing code goes here... edit this as much as you want!
	//--------------------------------------------------------
	/**
	 * This is the main method where there are tests.
	 * @param args The array of strings that the user can type in when running the program
	 */
	public static void main(String[] args) {
		//main method for testing, edit as much as you want
		ThreeTenHashMap<String,String> st1 = new ThreeTenHashMap<>(10);
		ThreeTenHashMap<String,Integer> st2 = new ThreeTenHashMap<>(5);
		
		st1.put("a","apple");
		st1.put("b","banana");
		st1.put("banana","b");
		st1.put("b","butter");

		if(st1.toString().equals("a:apple\nbanana:b\nb:butter") && st1.toStringDebug().equals("[0]: null\n[1]: null\n[2]: null\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: [a:apple]->[banana:b]->null\n[8]: [b:butter]->null\n[9]: null")) {
			System.out.println("Yay 1");
		}

		if(st1.getSlots() == 10 && st1.size() == 3 && st1.get("a").equals("apple")) {
			System.out.println("Yay 2");
		}
		
		st2.rehash(1);
		st2.put("a",1);
		st2.put("b",2);
		
		if(st2.toString().equals("b:2\na:1") && st2.toStringDebug().equals("[0]: [b:2]->null\n[1]: [a:1]->null")
			&& st2.put("e",3) == null && st2.put("y",4) == null &&
			st2.toString().equals("a:1\ne:3\ny:4\nb:2") && st2.toStringDebug().equals("[0]: null\n[1]: [a:1]->[e:3]->[y:4]->null\n[2]: [b:2]->null\n[3]: null")) {
			System.out.println("Yay 3");
		}
		
		if(st2.remove("e").equals(3) && st2.rehash(8) == true &&
			st2.size() == 3 && st2.getSlots() == 8 &&
			st2.toString().equals("a:1\ny:4\nb:2") && st2.toStringDebug().equals("[0]: null\n[1]: [a:1]->[y:4]->null\n[2]: [b:2]->null\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: null")) {
			System.out.println("Yay 4");
		}
		
		ThreeTenHashMap<String,String> st3 = new ThreeTenHashMap<>(2);
		st3.put("a","a");
		st3.remove("a");
		
		if(st3.toString().equals("") && st3.toStringDebug().equals("[0]: null\n[1]: null")) {
			st3.put("a","a");
			if(st3.toString().equals("a:a") && st3.toStringDebug().equals("[0]: null\n[1]: [a:a]->null")) {
				System.out.println("Yay 5");
			}
		}
		
		//This is NOT all the testing you need... several methods are not
		//being tested here! Some method return types aren't being tested
		//either. You need to write your own tests!
		
		//Also, try this and see if it works:
		ThreeTenHashMap<Integer,Integer> st4 = new ThreeTenHashMap<>(10);
		st4.put(Integer.MIN_VALUE, Integer.MIN_VALUE);
		System.out.println(st4.toStringDebug());
	}
	
	//********************************************************************************
	//   YOU MAY, BUT DON'T NEED TO EDIT THINGS IN THIS SECTION
	// These are some methods we didn't write for you, but you could write,
	// if you need/want them for building your graph. We will not test
	// (or grade) these methods.
	//********************************************************************************
	/**
	 * {@inheritDoc}
	 */
	public Collection<V> values() {
		throw new UnsupportedOperationException();
	}
	/**
	 * {@inheritDoc}
	 */
	public void	putAll(Map<? extends K,? extends V> m) {
		throw new UnsupportedOperationException();
	}
	/**
	 * {@inheritDoc}
	 */
	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();
	}
	/**
	 * This is one of the methods that is not used. 
	 * @return A set with the elements consisting of maps
	 */
	public Set<Map.Entry<K,V>> entrySet() {
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
	/**
	 * {@inheritDoc}
	 */
	public boolean containsKey(Object key) {
		throw new UnsupportedOperationException();
	}
	
	//********************************************************************************
	//   DO NOT EDIT ANYTHING BELOW THIS LINE (except to add the JavaDocs)
	// We will check these things to make sure they still work, so don't break them.
	//********************************************************************************
	
	//THIS CLASS IS PROVIDED, DO NOT CHANGE IT
	/**
	 * This represents the node that will have the TableEntry which has the key and associated value.
	 * @param <K> The generic variable for the key attribute.
	 * @param <V> The generic variable for the value attribute.
	 */
	public static class Node<K,V> {
		/**
		 * This is the table entry that holds the key and value associated with that key.
		 */
		public TableEntry<K,V> entry;
		/**
		 * This is the next node.
		 */
		public Node<K,V> next;
		/**
		 * This is a constructor for this class. It takes a TableEntry which will serve as the entry.
		 * @param entry A TableEntry that has the key and value.
		 */
		public Node(TableEntry<K,V> entry) {
			this.entry = entry;
		}
		/**
		 * This is another constructor for this class. It takes the TableEntry and the next Node.
		 * @param entry A TableEntry that has the key and value.
		 * @param next The next Node.
		 */
		public Node(TableEntry<K,V> entry, Node<K,V> next) {
			this(entry);
			this.next = next;
		}
		/**
		 * This method will return the Node into a string.
		 * @return A string that is the representation of the Node.
		 */
		public String toString() {
			return "[" + entry.toString() + "]->";
		}
	}
	
	//THIS CLASS IS PROVIDED, DO NOT CHANGE IT
	/**
	 * This class represents the element hat holds the key and value associated with that key.
	 * @param <K> The generic variable for the key attribute.
	 * @param <V> The generic variable for the value attribute.
	 */
	public static class TableEntry<K,V> {
		/**
		 * This is they key that has a value associated with it.
		 */
		public K key;
		/**
		 * This is the value that is associated with they key.
		 */
		public V value;
		/**
		 * This is the constructor for this class. It will take the key and value parameters.
		 * @param key The key that the user wishes to instantiate.
		 * @param value The value that the user wishes the key to be associated with.
		 */
		public TableEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		/**
		 * This method will return the string representation of the key and value.
		 * @return The string that represents the key and value.
		 */
		public String toString() {
			return key.toString()+":"+value.toString();
		}
	}
	/**
	 * This method will return an array with the all of the Table Entries as the elements.
	 * @return An array of Table Entries.
	 */
	public TableEntry[] toArray(){
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		
		TableEntry[] collection = new TableEntry[this.numElements];
		int index = 0;
		for(int i = 0; i < storage.length; i++) {
			if(storage[i] != null) {
				Node<K,V> curr = storage[i];
				while(curr != null) {
					collection[index] = curr.entry;
					index++;
					curr = curr.next;
				}
			}
		}
		return collection;		
	}
	/**
	 * This method will return the string that represents the whole table with all of the table entries form every node in every slot.
	 * @return A string that represents the hash table.
	 */
	public String toString() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < storage.length; i++) {
			Node<K,V> curr = storage[i];
			if(curr == null) continue;
			
			while(curr != null) {
				s.append(curr.entry.toString());
				s.append("\n");
				curr = curr.next;
			}
		}
		return s.toString().trim();
	}
	/**
	 * This method will return the string that represents the hash table along with the nodes in each slot.
	 * @return The true finalized string of the hash table.
	 */
	public String toStringDebug() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < storage.length; i++) {
			Node<K,V> curr = storage[i];
			
			s.append("[" + i + "]: ");
			while(curr != null) {
				s.append(curr.toString());
				curr = curr.next;
			}
			s.append("null\n");
		}
		return s.toString().trim();
	}
	/**
	 * This method will be called if the load factor gets too big. it will double the size of the storage and reallocate all of the nodes appropriately.
	 * @param size The size of the current storage.
	 * @return A boolean on whether the hashtable was successfully rehashed or not.
	 */
	@SuppressWarnings("unchecked")
	public boolean rehash(int size) {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		
		if(size < 1) return false;
		
		Node<K,V>[] oldTable = storage;
		storage = (Node<K,V>[]) new Node[size];
		numElements = 0;
		
		for(Node<K,V> node : oldTable) {
			while(node != null) {
				putNoExpand(node.entry.key, node.entry.value);
				node = node.next;
			}
		}
		
		return true;
	}
	/**
	 * This method will call the putNoExpand() to actually insert the new table entry, and then check the load factor to see if it should be rehashed.
	 * @param key The key that the user wishes to put into the hash table
	 * @param value The value that the user wishes to be associated with the key.
	 * @return The value that was just inserted with the key into the hash table.
	 */
	public V put(K key, V value) {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		
		V ret = putNoExpand(key, value);
		while((numElements/(double)storage.length) >= 2) {
			rehash(storage.length*2);
		}
		return ret;
	}
}
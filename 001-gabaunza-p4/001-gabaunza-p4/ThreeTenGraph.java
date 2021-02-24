import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;

import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.graph.util.EdgeType;

import org.apache.commons.collections15.Factory;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collection;

//You may use your hash map and hash set if you'd like
//or you may import the java.util versions.
//The interface  is the same for both, so the code you
//write here (in ThreeTenGraph) should be the same for
//either one!

//Uncomment the following lines if you want to use the java.util version
import java.util.HashMap; //or use ThreeTenHashMap!
import java.util.HashSet; //or use ThreeTenHashSet!
	
import java.util.NoSuchElementException;
/**
 * This class represents the graph. has two generic variables for the vertices and edges and implements the Graph and Undirected Graph interfaces.
 * @param <V> The generic variable for the vertex.
 * @param <E> The generic variable for the edge.
 */
class ThreeTenGraph<V extends ThreeTenGraphComponent, E extends ThreeTenGraphComponent>
	implements Graph<V, E>, UndirectedGraph<V, E> {
	
	//********************************************************************************
	//   YOUR CODE GOES IN THIS SECTION
	//********************************************************************************
	
	//**************** IMPORTANT WARNING ****************
	//Due to Java complexities with bounded genics that it would be difficult to explain here,
	//if you want an array of V[] or E[], the following format ___SHOULD NOT___ be used:
	//         V[] items = (V[]) new Object[10];
	//instead, use this format:
	//         V[] items = (V[]) new ThreeTenGraphComponent[10];
	/**
	 * This is a private helper class. It will only hold the edge and the vertext that it connects to. 
	 * @param <E> The generic varibale for the edge
	 * @param <V> The generic variable for the vertex it connects to.
	 */
	private static class EdgeEntry<E,V> {
		/**
		 * This is they key that has a value associated with it.
		 */
		private E edge;
		/**
		 * This is the value that is associated with they key.
		 */
		private V connectedVertex;
		/**
		 * This is the constructor for this class. It will take the key and value parameters.
		 * @param newEdge The key that the user wishes to instantiate.
		 * @param otherVertex The value that the user wishes the key to be associated with.
		 */
		private EdgeEntry(E newEdge, V otherVertex) {
			this.edge = newEdge;
			this.connectedVertex = otherVertex;
		}
	}
	/**
	 * This is a private helper class. It is a node that has a vertex and keeps track of the edges that are assigned to it.
	 * @param <V> The generic variable for the vertex.
	 * @param <E> The generic variable for the edge.
	 */
	private class Nodes<V,E> {
		/**
		 * Ever Node wil have one vertex. It's useful to think of the node as the vertex.
		 */
		private V vertex;

		/**
		 * Each node will have an arraylist of edges that are assigned to it. The array list will also contain the vertices that each edge connects to.
		 */
		private ArrayList<EdgeEntry<E,V>> edges;

		/**
		 * The constructor for the Nodes class. It takes a vertex and creates a node to pace the vertex in.
		 * @param newVertex The new vertex that the user wishes to be created.
		 */
		private Nodes(V newVertex){
			this.vertex = newVertex;
			this.edges = new ArrayList<EdgeEntry<E,V>>();
		}

		/**
		 * This will add a new edge, as well as the vertex it connects to, to the arraylst of edges.
		 * @param newEdge The new edge the user wishes to insert
		 * @param vertexItConnectsTo The vertex that the new edge will connect this vertex to.
		 */
		private void addEdge(E newEdge, V vertexItConnectsTo){
			EdgeEntry<E,V> newEntry = new EdgeEntry<E,V>(newEdge, vertexItConnectsTo);
			edges.add(newEntry);
			return;
		}
		/**
		 * This method will determine whether the specified edge is connected to this vertex or not.
		 * @param edge The edge that the user wishes to see if it is connected to this vertex or not.
		 * @return A boolean based on whether the edge is connected to this vertex or not.
		 */
		private boolean containsEdge(E edge){
			for (int i = 0; i < edges.size(); i++){
				if (edges.get(i) == null){
					continue;
				}
				if (edges.get(i).edge.equals(edge)){
					return true;
				}
			}
			return false;
		}
		/**
		 * Will return a collection of all the edges that are connected to this vertex.
		 * @return A collection of all the edges that are connected to this vertex.
		 */
		private Collection<E> getAllEdges(){
			Collection<E> allEdgesInVertex = new ThreeTenHashSet<E>();
			for (int i = 0; i < this.edges.size(); i++){
				allEdgesInVertex.add(edges.get(i).edge);
			}
			return allEdgesInVertex;
		}
		/**
		 * Will return the number of edges that are connected to this vertex.
		 * @return An int representing the number of edges connected to this vertex.
		 */
		private int numOfEdges(){
			return edges.size();
		}
	}
	/**
	 * This will keep trak of all the vertices and  edges that are attached to the specified vertex.
	 */
	public ArrayList<Nodes<V,E>> vertices;

	/**
	 * Creates a new graph. Initializing all appropriate instance variables.
	 */
	@SuppressWarnings("unchecked")
	public ThreeTenGraph() {
		vertices = new ArrayList<Nodes<V,E>>();
	}
	
	/**
	 * Returns a view of all edges in this graph. In general, this
	 * obeys the Collection contract, and therefore makes no guarantees 
	 * about the ordering of the vertices within the set.
	 * @return a Collection view of all edges in this graph
	 */
	public Collection<E> getEdges() {
		Collection<E> allEdges = new ThreeTenHashSet<E>();
		for (int i = 0; i < this.vertices.size(); i++){
			for (int j = 0; j < vertices.get(i).edges.size(); j++){
				allEdges.add(vertices.get(i).edges.get(j).edge);
			}
		}
		return allEdges;
	}
	
	/**
	 * Returns a view of all vertices in this graph. In general, this
	 * obeys the Collection contract, and therefore makes no guarantees 
	 * about the ordering of the vertices within the set.
	 * @return a Collection view of all vertices in this graph
	 */
	public Collection<V> getVertices() {
		Collection<V> allVertices = new ThreeTenHashSet<V>();
		for (int i = 0; i < vertices.size(); i++){
			allVertices.add(vertices.get(i).vertex);
		}
		return allVertices;
	}
	
	/**
	 * Returns the number of edges in this graph.
	 * @return the number of edges in this graph
	 */
	public int getEdgeCount() {
		Collection<E> allEdges = getEdges();
		return allEdges.size();
	}
	
	/**
	 * Returns the number of vertices in this graph.
	 * @return the number of vertices in this graph
	 */
	public int getVertexCount() {
		return this.vertices.size();
	}
	/**
	 * Returns the collection of vertices in this graph which are connected to edge.
	 * Note that for some graph types there are guarantees about the size of this collection
	 * (i.e., some graphs contain edges that have exactly two endpoints, which may or may 
	 * not be distinct).  Implementations for those graph types may provide alternate methods 
	 * that provide more convenient access to the vertices.
	 * @param edge the edge whose incident vertices are to be returned
	 * @return  the collection of vertices which are connected to edge, or null if edge is not present
	 */
	public Collection<V> getIncidentVertices(E edge) {
		Collection<V> twoVertices = new ThreeTenHashSet<V>();
		for (int i = 0; i < this.vertices.size(); i++){
			if (vertices.get(i).containsEdge(edge)){
				twoVertices.add(vertices.get(i).vertex);
				for (int j = 0; j < vertices.get(i).edges.size(); j++){
					if (vertices.get(i).edges.get(j).equals(edge)){
						twoVertices.add(vertices.get(i).edges.get(j).connectedVertex);
					}
				}
			}
		}
		if (twoVertices.isEmpty()){
			return null;
		}
		return twoVertices;
	}

	/**
	 * Returns the collection of vertices which are connected to vertex
	 * via any edges in this graph.
	 * If vertex is connected to itself with a self-loop, then 
	 * it will be included in the collection returned.
	 * @param vertex the vertex whose neighbors are to be returned
	 * @return  the collection of vertices which are connected to vertex, or null if vertex is not present
	 */
	public Collection<V> getNeighbors(V vertex) {
		Collection<V> neighborVertices = new ThreeTenHashSet<V>();
		int vertexIndex = -1;
		for (int i = 0; i < vertices.size(); i++){
			if (vertices.get(i).vertex.equals(vertex)){
				vertexIndex = i;
				break;
			}
		}
		if (vertexIndex == -1){
			return null;
		}
		for (int i = 0; i < vertices.get(vertexIndex).edges.size(); i++){
			neighborVertices.add(vertices.get(vertexIndex).edges.get(i).connectedVertex);
		}
		return neighborVertices;
	}
	
	/**
	 * Returns the collection of edges in this graph which are connected to vertex.
	 * @param vertex the vertex whose incident edges are to be returned
	 * @return  the collection of edges which are connected to vertex, or null if vertex is not present
	 */
	public Collection<E> getIncidentEdges(V vertex) {
		Collection<E> incidentEdges;
		int vertexIndex = -1;
		for (int i = 0; i < vertices.size(); i++){
			if (vertices.get(i).vertex.equals(vertex)){
				vertexIndex = i;
				break;
			}
		}
		if (vertexIndex == -1){
			return null;
		}
		incidentEdges = vertices.get(vertexIndex).getAllEdges();
		return incidentEdges;
	}
	
	/**
	 * Returns an edge that connects v1 to v2.
	 * If this edge is not uniquely
	 * defined (that is, if the graph contains more than one edge connecting 
	 * v1 to v2), any of these edges 
	 * may be returned.  findEdgeSet(v1, v2) may be 
	 * used to return all such edges.
	 * Returns null if either of the following is true:
	 * <ul>
	 * <li/>v1 is not connected to v2
	 * <li/>either v1 or v2 are not present in this graph
	 * </ul>
	 * 
	 * <p><b>Note</b>: for purposes of this method, v1 is only considered to be connected to
	 * v2 via a given <i>directed</i> edge e if
	 * v1 == e.getSource() && v2 == e.getDest() evaluates to true.
	 * (v1 and v2 are connected by an undirected edge u if 
	 * u is incident to both v1 and v2.)
	 * @param v1 One of the vertices that the edge will connect to.
	 * @param v2 The other vertex that the edge will connect to.
	 * @return  an edge that connects v1 to v2, or null if no such edge exists (or either vertex is not present)
	 * @see Hypergraph#findEdgeSet(Object, Object) 
	 */
	public E findEdge(V v1, V v2) {
		boolean containsV1 = false;
		boolean containsV2 = false;
		int indexV1 = -1;
		for (int i = 0; i < vertices.size(); i++){
			if (vertices.get(i).vertex.equals(v1)){
				indexV1 = i;
				containsV1 = true;
			}
			if (vertices.get(i).vertex.equals(v2)){
				containsV2 = true;
			}
		}
		if (!containsV1 || !containsV2){
			return null;
		}
		if (indexV1 == -1){
			return null;
		}
		else {
			for (int j = 0; j < vertices.get(indexV1).edges.size(); j++){
				if (vertices.get(indexV1).edges.get(j).connectedVertex.equals(v2)){
					return vertices.get(indexV1).edges.get(j).edge;
				}
			}
			return null;
		}
	}

	/**
	 * Adds edge e to this graph such that it connects 
	 * vertex v1 to v2. 
	 * If this graph does not contain v1, v2, 
	 * or both, implementations may choose to either silently add 
	 * the vertices to the graph or throw an IllegalArgumentException.
	 * If this graph assigns edge types to its edges, the edge type of
	 * e will be the default for this graph.
	 * See Hypergraph.addEdge() for a listing of possible reasons
	 * for failure. In addition, this should fail if the vertices or edge
	 * violates any given restrictions (such as invalid IDs).
	 * Equivalent to addEdge(e, new Pair(v1, v2)).
	 * @param e the edge to be added
	 * @param v1 the first vertex to be connected
	 * @param v2 the second vertex to be connected
	 * @return true if the add is successful, false otherwise
	 * @see Hypergraph#addEdge(Object, Collection)
	 * @see #addEdge(Object, Object, Object, EdgeType)
	 */
	public boolean addEdge(E e, V v1, V v2) {
		int indexV1 = -1;
		int indexV2 = -1;
		for (int i = 0; i < vertices.size(); i++){
			if (vertices.get(i) == null){//Makes sure there is a node there.
				continue;
			}
			if (vertices.get(i).containsEdge(e)){//Checks to make sure it doesn't already exist.
				return false;
			}
			if (vertices.get(i).vertex.equals(v1)){
				indexV1 = i;
				for (int k = 0; k < vertices.get(indexV1).edges.size(); k++){
					if (vertices.get(indexV1).edges.get(k) == null){
						continue;
					}
					if (vertices.get(indexV1).edges.get(k).connectedVertex.equals(v2)){
						return false;
					}
				}
				
			}
			if (vertices.get(i).vertex.equals(v2)){
				indexV2 = i;
			}
		}
		if (v1.equals(v2)){
			vertices.get(indexV1).addEdge(e, v1);
			return true;
		}
		if (indexV1 == -1 && indexV2 == -1){
			Nodes<V,E> newNode1 = new Nodes<V,E>(v1);
			Nodes<V,E> newNode2 = new Nodes<V,E>(v2);
			vertices.add(newNode1);
			vertices.add(newNode2);
			for (int i = 0; i < vertices.size(); i++){
				if (vertices.get(i) == null){
					continue;
				}
				if (vertices.get(i).equals(v1)){
					indexV1 = i;
				}
				if (vertices.get(i).equals(v2)){
					indexV2 = i;
				}
			}
			vertices.get(indexV1).addEdge(e, v2);
			vertices.get(indexV2).addEdge(e, v1);
			return true;
		}
		else if (indexV1 != -1 && indexV2 == -1){
			if (vertices.get(indexV1).containsEdge(e)){
				return false;
			}
			Nodes<V,E> newNode2 = new Nodes<V,E>(v2);
			vertices.add(newNode2);
			for (int i = 0; i < vertices.size(); i++){
				if (vertices.get(i) == null){
					continue;
				}
				if (vertices.get(i).equals(v2)){
					indexV2 = i;
				}
			}
			vertices.get(indexV1).addEdge(e, v2);
			vertices.get(indexV2).addEdge(e, v1);
			return true;
		}
		else if (indexV1 == -1 && indexV2 != -1){
			if (vertices.get(indexV2).containsEdge(e)){
				return false;
			}
			Nodes<V,E> newNode1 = new Nodes<V,E>(v1);
			vertices.add(newNode1);
			for (int i = 0; i < vertices.size(); i++){
				if (vertices.get(i) == null){
					continue;
				}
				if (vertices.get(i).equals(v1)){
					indexV1 = i;
				}
			}
			vertices.get(indexV1).addEdge(e, v2);
			vertices.get(indexV2).addEdge(e, v1);
			return true;
		}
		else {
			if (vertices.get(indexV1).containsEdge(e)){
				return false;
			}
			if (vertices.get(indexV2).containsEdge(e)){
				return false;
			}
			vertices.get(indexV1).addEdge(e, v2);
			vertices.get(indexV2).addEdge(e, v1);
			return true;
		}
	}
	
	/**
	 * Adds vertex to this graph.
	 * Fails if vertex is null or already in the graph.
	 * Also fails if the vertex violates and constraints given in
	 * the project (such as ID restrictions).
	 * 
	 * @param vertex	the vertex to add
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if vertex is null
	 */
	public boolean addVertex(V vertex) {
		if (vertex == null){
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < vertices.size(); i++){
			if (vertices.get(i) == null){
				continue;
			}
			if (vertices.get(i).vertex.equals(vertex)){
				return false;
			}
		}
		Nodes<V,E> newNode = new Nodes<V,E>(vertex);
		vertices.add(newNode);
		return true;
	}

	/**
	 * Removes edge from this graph.
	 * Fails if edge is null, or is otherwise not an element of this graph.
	 * 
	 * @param edge the edge to remove
	 * @return true if the removal is successful, false otherwise
	 */
	public boolean removeEdge(E edge) {
		V otherVertex = null;
		int otherVertexIndex = -1;
		V v1 = null;
		int v1Index = -1;
		int v1EdgeIndex = -1;
		int otherVertexEdgeIndex = -1;
		boolean condition = false;
		boolean condition1 = false;
		for (int i = 0; i < vertices.size(); i++){//Will traverse until it finds the wanted edge...
			if (vertices.get(i) == null){
				continue;
			}
			for (int j = 0; j < vertices.get(i).edges.size(); j++){
				if (vertices.get(i).edges.get(j).edge.equals(edge)){//And markes the vertex and the other vertex.
					v1 = vertices.get(i).vertex;
					v1Index = i;
					v1EdgeIndex = j;
					otherVertex = vertices.get(i).edges.get(j).connectedVertex;
					condition = true;
					break;
				}
			}
			if (condition){
				break;
			}
		}
		if (v1 == null){
			return false;
		}
		if (otherVertex == null){
			return false;
		}
		if (v1EdgeIndex == -1){
			return false;
		}
		for (int i = 0; i < vertices.size(); i++){//Then it will look for the other vertex...
			if (vertices.get(i) == null){
				continue;
			}
			if (vertices.get(i).vertex.equals(otherVertex)){
				otherVertexIndex = i;
				for (int j = 0; j < vertices.get(i).edges.size(); j++){
					if (vertices.get(i).edges.get(j) == null){
						continue;
					}
					if (vertices.get(i).edges.get(j).edge.equals(edge)){
						otherVertexEdgeIndex = j;
						condition1 = true;
						break;
					}
				}
			}
			if (condition1){
				break;
			}
		}
		if (otherVertexEdgeIndex == -1){
			return false;
		}
		if (!(condition1 && condition)){
			return false;
		}
		vertices.get(v1Index).edges.remove(v1EdgeIndex);
		vertices.get(otherVertexIndex).edges.remove(otherVertexEdgeIndex);
		return true;		
	}
	
	/**
	 * Removes vertex from this graph.
	 * As a side effect, removes any edges e incident to vertex if the
	 * removal of vertex would cause e to be incident to an illegal
	 * number of vertices.  (Thus, for example, incident hyperedges are not removed, but 
	 * incident edges--which must be connected to a vertex at both endpoints--are removed.)
	 * 
	 * <p>Fails under the following circumstances:
	 * <ul>
	 * <li/>vertex is not an element of this graph
	 * <li/>vertex is null
	 * </ul>
	 * 
	 * @param vertex the vertex to remove
	 * @return true if the removal is successful, false otherwise
	 */
	public boolean removeVertex(V vertex) {
		int originalSize;
		for (int i = 0; i < this.vertices.size(); i++){
			if (vertices.get(i) == null){
				continue;
			}
			if (vertices.get(i).vertex.equals(vertex)){
				originalSize = vertices.get(i).edges.size();
				for (int j = 0; j < originalSize; j++){
					if (vertices.get(i).edges.get(0) == null){
						continue;
					}
					removeEdge(vertices.get(i).edges.get(0).edge);
					vertices.get(i).edges.trimToSize();
					if (vertices.get(i).edges.size() == 0){
						
						break;
					}
					
				}
				vertices.remove(i);
				return true;
			}
		}
		return false;
	}
	
	//--------------------------------------------------------
	// testing code goes here... edit this as much as you want!
	//--------------------------------------------------------
	/**
	 * This will return the string representation of the graph.
	 * @return String that represents the graph.
	 */
	public String toString() {
		//you may edit this to make string representations of your
		//graph for testing
		return super.toString();
	}
	/**
	* This is the main method where there are tests.
	* @param args The array of strings that the user can type in when running the program
	*/
	public static void main(String[] args) {
		//Some example testing code...
		/**
		* A small class just for the purpose of this test.
		*/
		class Person extends ThreeTenGraphComponent {
			public Person(int id) { super(id); }
		}
		/**
		* A small class just for the purpose of this test.
		*/
		class Cat extends ThreeTenGraphComponent {
			public Cat(int id) { super(id); }
		}
		/**
		* A small class just for the purpose of this test.
		*/
		class IntComponent extends ThreeTenGraphComponent {
			public IntComponent(int id) { super(id); }
		}
		
		//constructs a graph
		
		ThreeTenGraph<Person,Cat> graph1 = new ThreeTenGraph<>();
		for(int i = 0; i < 3; i++) {
			graph1.addVertex(new Person(i));
		}
		for(Person p : graph1.getVertices()) {
			graph1.addEdge(new Cat((int)(Math.random()*100)), p, p);
		}
		
		if(graph1.getVertexCount() == 3 && graph1.getEdgeCount() == 3) {
			System.out.println("Yay 1");
		}
		
		//create a set of nodes and edges to test with
		IntComponent[] nodes = {
			new IntComponent(1), 
			new IntComponent(26), 
			new IntComponent(2), 
			new IntComponent(25), 
			new IntComponent(3), 
			new IntComponent(24), 
			new IntComponent(4), 
			new IntComponent(23), 
			new IntComponent(5), 
			new IntComponent(22)
		};
		
		IntComponent[] edges = {
			new IntComponent(10000000), 
			new IntComponent(4), 
			new IntComponent(Integer.MAX_VALUE),
			new IntComponent(Integer.MIN_VALUE), 
			new IntComponent(500), 
			new IntComponent(600000)
		};
		
		//constructs a graph
		ThreeTenGraph<IntComponent,IntComponent> graph2 = new ThreeTenGraph<>();
		for(IntComponent n : nodes) {
			graph2.addVertex(n);
		}
		graph2.addEdge(edges[0],nodes[0],nodes[1]);
		graph2.addEdge(edges[1],nodes[2],nodes[2]);
		graph2.addEdge(edges[2],nodes[3],nodes[4]);
		graph2.addEdge(edges[3],nodes[6],nodes[7]);
		graph2.addEdge(edges[4],nodes[8],nodes[9]);
		graph2.addEdge(edges[5],nodes[9],nodes[0]);
		
		
		if(graph2.containsVertex(new IntComponent(1)) && graph2.containsEdge(new IntComponent(10000000))) {
			System.out.println("Yay 2");
		}
		
		//lot more testing here...
	}
	
	//********************************************************************************
	//   YOU MAY, BUT DON'T NEED TO EDIT THINGS IN THIS SECTION
	// This is a good place to look for "optimizing" your code to be faster.
	//********************************************************************************
	
	/**
	 * Returns true if this graph's vertex collection contains vertex.
	 * Equivalent to getVertices().contains(vertex).
	 * @param vertex the vertex whose presence is being queried
	 * @return true iff this graph contains a vertex vertex
	 */
	public boolean containsVertex(V vertex) {
		return getVertices().contains(vertex);
	}
	
	/**
	 * Returns true if this graph's edge collection contains edge.
	 * Equivalent to getEdges().contains(edge).
	 * @param edge the edge whose presence is being queried
	 * @return true iff this graph contains an edge edge
	 */
	public boolean containsEdge(E edge) {
		return getEdges().contains(edge);
	}

	/**
	* Returns true if vertex and edge 
	* are incident to each other.
	* Equivalent to getIncidentEdges(vertex).contains(edge) and to
	* getIncidentVertices(edge).contains(vertex).
	* @param vertex The vertex that the user wants to see if it has the edge or not.
	* @param edge The specified edge that the user wants to know if it connects to the vertex or not.
	* @return true if vertex and edge are incident to each other
	*/
	public boolean isIncident(V vertex, E edge) {
		return getIncidentEdges(vertex).contains(edge);
	}

	/**
	* Returns true if v1 and v2 share an incident edge.
	* Equivalent to getNeighbors(v1).contains(v2).
	* 
	* @param v1 the first vertex to test
	* @param v2 the second vertex to test
	* @return true if v1 and v2 share an incident edge
	*/
	public boolean isNeighbor(V v1, V v2) {
		return getNeighbors(v1).contains(v2);
	}
	
	/**
	* Returns true if v1 is a predecessor of v2 in this graph.
	* Equivalent to v1.getPredecessors().contains(v2).
	* @param v1 the first vertex to be queried
	* @param v2 the second vertex to be queried
	* @return true if v1 is a predecessor of v2, and false otherwise.
	*/
	public boolean isPredecessor(V v1, V v2) {
		return getPredecessors(v1).contains(v2);
	}
	
	/**
	* Returns true if v1 is a successor of v2 in this graph.
	* Equivalent to v1.getSuccessors().contains(v2).
	* @param v1 the first vertex to be queried
	* @param v2 the second vertex to be queried
	* @return true if v1 is a successor of v2, and false otherwise.
	*/
	public boolean isSuccessor(V v1, V v2) {
		return getSuccessors(v1).contains(v2);
	}
	
	/**
	* Returns the number of edges incident to vertex.  
	* Special cases of interest:
	* <ul>
	* <li/> Incident self-loops are counted once.
	* <li> If there is only one edge that connects this vertex to
	* each of its neighbors (and vice versa), then the value returned 
	* will also be equal to the number of neighbors that this vertex has
	* (that is, the output of getNeighborCount).
	* <li> If the graph is directed, then the value returned will be 
	* the sum of this vertex's indegree (the number of edges whose 
	* destination is this vertex) and its outdegree (the number
	* of edges whose source is this vertex), minus the number of
	* incident self-loops (to avoid double-counting).
	* </ul>
	*
	* <p>Equivalent to getIncidentEdges(vertex).size().
	* 
	* @param vertex the vertex whose degree is to be returned
	* @return the degree of this node
	* @see Hypergraph#getNeighborCount(Object)
	*/
	public int degree(V vertex) {
		return getIncidentEdges(vertex).size();
	}

	/**
	* Returns the number of vertices that are adjacent to vertex
	* (that is, the number of vertices that are incident to edges in vertex's
	* incident edge set).
	* 
	* <p>Equivalent to getNeighbors(vertex).size().
	* @param vertex the vertex whose neighbor count is to be returned
	* @return the number of neighboring vertices
	*/
	public int getNeighborCount(V vertex) {
		return getNeighbors(vertex).size();
	}
	
	/**
	* Returns the number of incoming edges incident to vertex.
	* Equivalent to getInEdges(vertex).size().
	* @param vertex	the vertex whose indegree is to be calculated
	* @return  the number of incoming edges incident to vertex
	*/
	public int inDegree(V vertex) {
		return getInEdges(vertex).size();
	}
	
	/**
	* Returns the number of outgoing edges incident to vertex.
	* Equivalent to getOutEdges(vertex).size().
	* @param vertex	the vertex whose outdegree is to be calculated
	* @return  the number of outgoing edges incident to vertex
	*/
	public int outDegree(V vertex) {
		return getOutEdges(vertex).size();
	}

	/**
	* Returns the number of predecessors that vertex has in this graph.
	* Equivalent to vertex.getPredecessors().size().
	* @param vertex the vertex whose predecessor count is to be returned
	* @return  the number of predecessors that vertex has in this graph
	*/
	public int getPredecessorCount(V vertex) {
		return getPredecessors(vertex).size();
	}
	
	/**
	* Returns the number of successors that vertex has in this graph.
	* Equivalent to vertex.getSuccessors().size().
	* @param vertex the vertex whose successor count is to be returned
	* @return  the number of successors that vertex has in this graph
	*/
	public int getSuccessorCount(V vertex) {
		return getSuccessors(vertex).size();
	}
	
	/**
	* Returns the vertex at the other end of edge from vertex.
	* (That is, returns the vertex incident to edge which is not vertex.)
	* @param vertex the vertex to be queried
	* @param edge the edge to be queried
	* @return the vertex at the other end of edge from vertex
	*/
	public V getOpposite(V vertex, E edge) {
		Pair<V> p = getEndpoints(edge);
		if(p.getFirst().equals(vertex)) {
			return p.getSecond();
		}
		else {
			return p.getFirst();
		}
	}
	
	/**
	* Returns all edges that connects v1 to v2.
	* If this edge is not uniquely
	* defined (that is, if the graph contains more than one edge connecting 
	* v1 to v2), any of these edges 
	* may be returned.  findEdgeSet(v1, v2) may be 
	* used to return all such edges.
	* Returns null if v1 is not connected to v2.
	* <br/>Returns an empty collection if either v1 or v2 are not present in this graph.
	*  
	* <p><b>Note</b>: for purposes of this method, v1 is only considered to be connected to
	* v2 via a given <i>directed</i> edge d if
	* v1 == d.getSource() && v2 == d.getDest() evaluates to true.
	* (v1 and v2 are connected by an undirected edge u if 
	* u is incident to both v1 and v2.)
	* 
	* @param v1 The first of the two vertices that see if they have n edge connecting them.
	* @param v2 The second of the two vertices that see if they have an edge connecting them.
	* @return  a collection containing all edges that connect v1 to v2, or null if either vertex is not present
	* @see Hypergraph#findEdge(Object, Object) 
	*/
	public Collection<E> findEdgeSet(V v1, V v2) {
		E edge = findEdge(v1, v2);
		if(edge == null) {
			return null;
		}
		
		ArrayList<E> ret = new ArrayList<>();
		ret.add(edge);
		return ret;
		
	}
	
	/**
	* Returns true if vertex is the source of edge.
	* Equivalent to getSource(edge).equals(vertex).
	* @param vertex the vertex to be queried
	* @param edge the edge to be queried
	* @return true iff vertex is the source of edge
	*/
	public boolean isSource(V vertex, E edge) {
		return getSource(edge).equals(vertex);
	}
	
	/**
	* Returns true if vertex is the destination of edge.
	* Equivalent to getDest(edge).equals(vertex).
	* @param vertex the vertex to be queried
	* @param edge the edge to be queried
	* @return true iff vertex is the destination of edge
	*/
	public boolean isDest(V vertex, E edge) {
		return getDest(edge).equals(vertex);
	}
	
	/**
	* Adds edge e to this graph such that it connects 
	* vertex v1 to v2.
	* Equivalent to addEdge(e, new Pair(v1, v2)).
	* If this graph does not contain v1, v2, 
	* or both, implementations may choose to either silently add 
	* the vertices to the graph or throw an IllegalArgumentException.
	* If edgeType is not legal for this graph, this method will
	* throw IllegalArgumentException.
	* See Hypergraph.addEdge() for a listing of possible reasons
	* for failure.
	* @param e the edge to be added
	* @param v1 the first vertex to be connected
	* @param v2 the second vertex to be connected
	* @param edgeType the type to be assigned to the edge
	* @return true if the add is successful, false otherwise
	* @see Hypergraph#addEdge(Object, Collection)
	* @see #addEdge(Object, Object, Object)
	*/
	public boolean addEdge(E e, V v1, V v2, EdgeType edgeType) {
		//NOTE: Only undirected edges allowed
		
		if(edgeType == EdgeType.DIRECTED) {
			throw new IllegalArgumentException();
		}
		
		return addEdge(e, v1, v2);
	}
	
	/**
	* Adds edge to this graph.
	* Fails under the following circumstances:
	* <ul>
	* <li/>edge is already an element of the graph 
	* <li/>either edge or vertices is null
	* <li/>vertices has the wrong number of vertices for the graph type
	* <li/>vertices are already connected by another edge in this graph,
	* and this graph does not accept parallel edges.
	* </ul>
	* 
	* @param edge The edge that is being added.
	* @param vertices The vertices that it connects.
	* @return true if the add is successful, and false otherwise
	* @throws IllegalArgumentException if edge or vertices is null, or if a different vertex set in this graph is already connected by edge, or if vertices are not a legal vertex set for edge.
	*/
	@SuppressWarnings("unchecked")
	public boolean addEdge(E edge, Collection<? extends V> vertices) {
		if(edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}
		
		V[] vs = (V[])vertices.toArray();
		return addEdge(edge, vs[0], vs[1]);
	}

	/**
	* Adds edge to this graph with type edge_type.
	* Fails under the following circumstances:
	* <ul>
	* <li/>edge is already an element of the graph 
	* <li/>either edge or vertices is null
	* <li/>vertices has the wrong number of vertices for the graph type
	* <li/>vertices are already connected by another edge in this graph,
	* and this graph does not accept parallel edges
	* <li/>edge_type is not legal for this graph
	* </ul>
	* 
	* @param edge The edge that will be added to the graph.
	* @param vertices The vertices it connects.
	* @param edgeType The type of edge that is being passed in.
	* @return true if the add is successful, and false otherwise
	* @throws IllegalArgumentException if edge or vertices is null, or if a different vertex set in this graph is already connected by edge, or if vertices are not a legal vertex set for edge 
	*/
	@SuppressWarnings("unchecked")
	public boolean addEdge(E edge, Collection<? extends V> vertices, EdgeType edgeType) {
		if(edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}
		
		V[] vs = (V[])vertices.toArray();
		return addEdge(edge, vs[0], vs[1], edgeType);
	}
	
	/**
	* Returns the number of edges of type edge_type in this graph.
	* @param edgeType the type of edge for which the count is to be returned
	* @return the number of edges of type edge_type in this graph
	*/
	public int getEdgeCount(EdgeType edgeType) {
		if(edgeType == EdgeType.UNDIRECTED) {
			return getEdgeCount();
		}
		return 0;
	}
	
	/**
	* Returns the collection of edges in this graph which are of type edge_type.
	* @param edgeType the type of edges to be returned
	* @return the collection of edges which are of type edge_type, or null if the graph does not accept edges of this type
	* @see EdgeType
	*/
	public Collection<E> getEdges(EdgeType edgeType) {
		if(edgeType == EdgeType.UNDIRECTED) {
			return getEdges();
		}
		return null;
	}
	
	/**
	* Returns the number of vertices that are incident to edge.
	* For hyperedges, this can be any nonnegative integer; for edges this
	* must be 2 (or 1 if self-loops are permitted). 
	* 
	* <p>Equivalent to getIncidentVertices(edge).size().
	* @param edge the edge whose incident vertex count is to be returned
	* @return the number of vertices that are incident to edge.
	*/
	public int getIncidentCount(E edge) {
		return getIncidentVertices(edge).size();
	}
	
	/**
	* If directed_edge is a directed edge in this graph, returns the source; 
	* otherwise returns null. 
	* The source of a directed edge d is defined to be the vertex for which  
	* d is an outgoing edge.
	* directed_edge is guaranteed to be a directed edge if 
	* its EdgeType is DIRECTED. 
	* @param directedEdge This is the edge that will be checked.
	* @return  the source of directed_edge if it is a directed edge in this graph, or null otherwise
	*/
	public V getSource(E directedEdge) {
		return null;
	}

	/**
	* If directed_edge is a directed edge in this graph, returns the destination; 
	* otherwise returns null. 
	* The destination of a directed edge d is defined to be the vertex 
	* incident to d for which  
	* d is an incoming edge.
	* directed_edge is guaranteed to be a directed edge if 
	* its EdgeType is DIRECTED. 
	* @param directedEdge This is the edge that will be checked.
	* @return  the destination of directed_edge if it is a directed edge in this graph, or null otherwise
	*/
	public V getDest(E directedEdge) {
		return null;
	}

	/**
	* Returns a Collection view of the predecessors of vertex 
	* in this graph.  A predecessor of vertex is defined as a vertex v 
	* which is connected to 
	* vertex by an edge e, where e is an outgoing edge of 
	* v and an incoming edge of vertex.
	* @param vertex	the vertex whose predecessors are to be returned
	* @return  a Collection view of the predecessors of vertex in this graph
	*/
	public Collection<V> getPredecessors(V vertex) {
		return getNeighbors(vertex);
	}
	
	/**
	* Returns a Collection view of the successors of vertex 
	* in this graph.  A successor of vertex is defined as a vertex v 
	* which is connected to 
	* vertex by an edge e, where e is an incoming edge of 
	* v and an outgoing edge of vertex.
	* @param vertex	the vertex whose predecessors are to be returned
	* @return  a Collection view of the successors of vertex in this graph
	*/
	public Collection<V> getSuccessors(V vertex) {
		return getNeighbors(vertex);
	}
	
	/**
	* Returns a Collection view of the incoming edges incident to vertex
	* in this graph.
	* @param vertex	the vertex whose incoming edges are to be returned
	* @return  a Collection view of the incoming edges incident to vertex in this graph
	*/
	public Collection<E> getInEdges(V vertex) {
		return getIncidentEdges(vertex);
	}
	/**
	* Returns a Collection view of the outgoing edges incident to vertex
	* in this graph.
	* @param vertex	the vertex whose outgoing edges are to be returned
	* @return a Collection view of the outgoing edges incident to vertex in this graph
	*/
	public Collection<E> getOutEdges(V vertex) {
		return getIncidentEdges(vertex);
	}

	/**
	* Returns the endpoints of edge as a Pair that hold elements of generic variable V.
	* @param edge the edge whose endpoints are to be returned
	* @return the endpoints (incident vertices) of edge
	*/
	@SuppressWarnings("unchecked")
	public Pair<V> getEndpoints(E edge) {
		Object[] ends = getIncidentVertices(edge).toArray();
		return new Pair<>((V)ends[0],(V)ends[1]);
	}
	
	//********************************************************************************
	//   DO NOT EDIT ANYTHING BELOW THIS LINE (except to edit/fix the JavaDocs)
	//********************************************************************************
	
	/**
	* Returns a {@code Factory} that creates an instance of this graph type.
	* @param <V> the vertex type for the graph factory
	* @param <E> the edge type for the graph factory
	* @return (@code factory) that creates an instance of this graph type.
	*/
	public static <V extends ThreeTenGraphComponent, E extends ThreeTenGraphComponent> Factory<Graph<V,E>> getFactory() { 
		return new Factory<Graph<V,E>> () {
			public Graph<V,E> create() {
				return new ThreeTenGraph<>();
			}
		};
	}
	/**
	* Returns a {@code Factory} that creates an instance of this graph type.
	* @param <V> the vertex type for the graph factory
	* @param <E> the edge type for the graph factory
	* @return (@code factory) that creates an instance of this graph type.
	*/
	public static <V extends ThreeTenGraphComponent, E extends ThreeTenGraphComponent> Factory<UndirectedGraph<V,E>> getUndirectedFactory() { 
		return new Factory<UndirectedGraph<V,E>> () {
			public UndirectedGraph<V,E> create() {
				return new ThreeTenGraph<>();
			}
		};
	}
	
	/**
	* Returns the edge type of edge in this graph.
	* @param edge The edge that the user wishes to know the type of.
	* @return the EdgeType of edge, or null if edge has no defined type
	*/
	public EdgeType getEdgeType(E edge) {
		return EdgeType.UNDIRECTED;
	}
	
	/**
	* Returns the default edge type for this graph.
	* @return the default edge type for this graph
	*/
	public EdgeType getDefaultEdgeType() {
		return EdgeType.UNDIRECTED;
	}
}

package main.java.com.ist.rc;
import java.util.HashMap;

import edu.uci.ics.jung.graph.Graph;

/**
	*  Check EdgeFactory interface file before.
	*
	*  Specific EdgexFactory Interface implementation:
	*    Edges are Edge class
	*	 graph has to be subclass of Graph
	*	 The edges are simple with 2 vertexes only
	*	
	*  You need to specify the type of Vertex to add when instantiating.
	*  The type of vertex to add must match the vertex type of the Graph
	* 
	*  The Edge produced is also as simple as it can be.
	*
	*  IMPORTANT: the map is only supposed to be used when creating the graph. 
	*  Using this map after the initial creation of the graph might end in 
	*  incoherent results. 
	*
	*  The only reason it's used is because the JUNG Graph collection of edges
	*  we can retrieve is too slow. But that's the one which should be used
	*  with the graph after creating it.
	*
	*  If you want a graph with a faster data structure, we recommend creating another
	*  subclass of Graph with the convinient structures and overriding the necessary methods.
	*
	*
**/

public class GeneralEdgeFactory<V ,G extends Graph<V,Edge>> implements EdgeFactory<V,Edge,G>{
	HashMap<VertexGroup,Edge> map = new HashMap<VertexGroup,Edge>(); 

	public void addEdge(G graph, GraphFileParser.ParserInfo info, V[] vertexes){
		Edge edge = new Edge();
		if(graph.addEdge(edge,vertexes[0],vertexes[1])){
			map.put(new VertexGroup<V>(vertexes[0],vertexes[1]),edge);		
		}
	}

	class VertexGroup<V>{ //this is specific of this class. Just a way to organize the map
		public V v1;
		public V v2;
		public VertexGroup(V _v1, V _v2){
			v1 = _v1;
			v2 = _v2;
		}

		public boolean equals(Object obj){
			if(obj instanceof VertexGroup){
				VertexGroup v = (VertexGroup) obj;
					return v.v1.equals(v1) == v.v2.equals(v2);
			}
			return false;
		}
	}
}

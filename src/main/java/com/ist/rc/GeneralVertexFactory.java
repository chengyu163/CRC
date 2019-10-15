package com.ist.rc;
import java.util.HashMap;
import edu.uci.ics.jung.graph.Graph;

/**
	*  Check VertexFactory interface file before.
	*
	*  Specific VertexFactory Interface implementation:
	*    Vertexes are Vertex class
	*	 graph has to be subclass of Graph
	*
	*  It uses a map not to repeat vertexes (JUNG graph)
	*  doesn't use equals of vertexes to stop this.
	*
	*  addVertex adds a number vToAdd vertexes to graph G. 
	*  All the necessary information needed to create those vertexes
	*  should be in "GraphFileParser.ParserInfo info" argument.
	*
	* IMPORTANT: the map is only supposed to be used when creating the graph. 
	* Using this map after the initial creation of the graph might end in 
	* incoherent results. 
	*
	* The only reason it's used is because the JUNG Graph collection of vertexes
	* we can retrieve is too slow. But that's the one which should be used
	* with the graph after creating it.
	*
	* If you want a graph with a faster data structure, we recommend creating another
	* subclass of Graph with the convinient structures and overriding the necessary methods.
	*
	* 
**/

public class GeneralVertexFactory<G extends Graph<Vertex,?>> implements VertexFactory<Vertex,G>{

	HashMap<String,Vertex> map = new HashMap<String,Vertex>(); 
	public Vertex createVertex(String id){
		Vertex v = new Vertex(id);
		map.put(id,v);
		return v;
	}

	public Vertex[] addVertex(G graph, GraphFileParser.ParserInfo info){
		Vertex[] ret = new Vertex[info.vertex.length]; 
		int retN = 0;
		for( int i = 0; i< info.vertex.length; i++){
			String vertexName = info.vertex[i].toString();
			if(!map.containsKey(vertexName)){
				Vertex v = createVertex(vertexName);
				graph.addVertex(v);
				ret[retN++] = v;
			}
			else{ 
				ret[retN++] = map.get(vertexName);
			}
		}
		return ret;
		
	}
}

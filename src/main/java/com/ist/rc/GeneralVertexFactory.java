package com.ist.rc;
import java.util.Map;
import java.util.HashMap;
import edu.uci.ics.jung.graph.Graph;

/**
*  VertexFactory that uses Vertex class. 
*  map holds information about which vertex were already created
*  Keep in mind that Graph contains a vertex general collection
*  (which its use would be slower than a map).  
*
*  addVertex adds a number vToAdd vertexes to graph G. 
*  All the necessary information needed to create those vertexes
*  should be in "GraphFileParser.ParserInfo info" argument.
*
*  Avoid using createVertex method caressly.
*  Normal behaviour only need addVertex.
*
*
* IMPORTANT: the map is only supposed to be use when creating the graph. 
* Using this map after the initial creation of the graph might end in 
* incoherent results. 
**/

public class GeneralVertexFactory<G extends Graph<Vertex,?>> implements VertexFactory<Vertex,G>{

	HashMap<String,Vertex> map = new HashMap(); //it could be a map, but adding compare to allow it as a tree is useless... 
	public Vertex createVertex(String id){
		Vertex v = new Vertex(id);
		map.put(id,v);
		return v;
	}


	public Vertex[] addVertex(G graph, GraphFileParser.ParserInfo info){
		Vertex[] ret = new Vertex[2]; //NOTE HELP!
		int retN = 0;
		for( int i = 0; i< 2; i++){
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

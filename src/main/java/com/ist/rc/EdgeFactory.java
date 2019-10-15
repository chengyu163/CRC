package com.ist.rc;

import edu.uci.ics.jung.graph.Graph;

/**
*  EdgeFactory interface.
*
*  You should define your concrete EdgeFactory and 
*  define the specific edge class it will produce.
*
*  Concrete implementations of EdgeFactory don't
*  need to specify the type of Vertex. You can delegate
*  that definition upon the creation of the concrete
*  instance of that EdgeFactory.
*
*  For a concrete example check GeneralVertexFactory
*/


public interface EdgeFactory<V,E,G>{
	public void addEdge(G graph, GraphFileParser.ParserInfo info, V[] vertexes);
}

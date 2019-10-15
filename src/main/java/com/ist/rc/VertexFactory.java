package com.ist.rc;

import edu.uci.ics.jung.graph.Graph;

/**
*  VertexFactory interface.
*
*/


public interface VertexFactory<V,G>{
	public Vertex[] addVertex(G graph, GraphFileParser.ParserInfo info);
}

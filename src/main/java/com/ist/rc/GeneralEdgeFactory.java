package com.ist.rc;
import java.util.Map;
import java.util.HashMap;
import edu.uci.ics.jung.graph.Graph;

/**
*	This EdgeFactory uses Class Edge.
*	You need to specify the type of Vertex to add.
*	The type of vertex to add must match the vertex type of the Graph
* 
*	This is a simple EdgeFactory that produces edges between two vertex
*	The Edge produced is also as simple as it can be.
*
*	This factory also keeps the edges. This is useful since Jung
*   has no  good mechanism to find edges. To do that, we'd have to ask
*	for the whole list of edges and iterate over it.
*
*
*  
**/

public class GeneralEdgeFactory<V extends Vertex,G extends Graph<V,Edge>> implements EdgeFactory<V,Edge,G>{
	HashMap<VertexGroup,Edge> map = new HashMap(); //Note: it can't be treemap without compare. 

	public void addEdge(G graph, GraphFileParser.ParserInfo info, V[] vertexes){
		Edge edge = new Edge();
		if(graph.addEdge(edge,vertexes[0],vertexes[1])){
			map.put(new VertexGroup(vertexes[0],vertexes[1]),edge);		
		}
	}

	class VertexGroup<V>{
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

package main.java.com.ist.rc;

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
*
*  This interface should only need information from parsing,
*  the vertexes created by another factory which implements
*  the interface VertexFactpry and the relevant instance
*  of the graph
*
*  For a concrete example check GeneralVertexFactory
*/


public interface EdgeFactory<V,E,G>{
	public void addEdge(G graph, GraphFileParser.ParserInfo info, V[] vertexes);
}

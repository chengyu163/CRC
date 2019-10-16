package main.java.com.ist.rc;

/**
*  VertexFactory interface.
*
*  You should define your concrete VertexFactory and 
*  define the specific vertex class it will produce.
* 
*  This class is only supposed to need info from the parsing
*  using GraphFileParser class and the graph instance.
*   
*  For a concrete example check GeneralVertexFactory
*/

public interface VertexFactory<V,G>{
	public V[] addVertex(G graph, GraphFileParser.ParserInfo info);
}

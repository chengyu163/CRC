package main.java.com.ist.rc;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.Graph;
import org.apache.commons.collections15.Factory;
//import org.apache.commons.collections4.functors.*;
import edu.uci.ics.jung.algorithms.generators.random.*;
import java.lang.Class;
import java.lang.reflect.Constructor;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
//import com.google.common.base.Supplier; //Jung uses common base and not java util
/*
* DON'T MIND THIS FILE. 
*/


public class Main {
    public static void main( String[] args )
    {

    	System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");
     

   



        EdgeFactory gef = new GeneralEdgeFactory<Vertex,UndirectedGraph<Vertex,Edge>>();
        VertexFactory gvf = new GeneralVertexFactory<UndirectedSparseGraph<Vertex,Edge>>();


        UndirectedSparseGraph g = new UndirectedSparseGraph<Vertex,Edge>();
        GraphFileParser<Vertex,Edge,UndirectedSparseGraph> gfp = new GraphFileParser<Vertex,Edge,UndirectedSparseGraph>
        (gvf,
        gef,
         g);

/*
        UndirectedSparseGraph g = new UndirectedSparseGraph<Vertex,Edge>();
        GraphFileParser<Vertex,Edge,UndirectedSparseGraph> gfp = new GraphFileParser<Vertex,Edge,UndirectedSparseGraph>
        (new GeneralVertexFactory<UndirectedSparseGraph<Vertex,Edge>>(),
         new GeneralEdgeFactory<Vertex,UndirectedSparseGraph<Vertex,Edge>>(), 
         g);
*/
        String format = "  edge\n  [\n    source V\n    target V\n    value I\n  ]\n";


        gfp.createGraph(format,"lesmis.gml",false);
        System.out.println(g.getVertexCount());
        System.out.println(g.getEdgeCount());
        System.out.println("£££££££32££££££££\n"+g.toString());
    }
}    


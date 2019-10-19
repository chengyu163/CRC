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
*/


public class Main {
    public static void main( String[] args )
    {     
        //Create EdgeFactory
        EdgeFactory gef = new GeneralEdgeFactory<Vertex,UndirectedGraph<Vertex,Edge>>();
        //Create VertexFactory
        VertexFactory gvf = new GeneralVertexFactory<UndirectedSparseGraph<Vertex,Edge>>();
        //graph
        UndirectedSparseGraph g = new UndirectedSparseGraph<Vertex,Edge>();
        //Parser!
        GraphFileParser<Vertex,Edge,UndirectedSparseGraph> gfp = new GraphFileParser<Vertex,Edge,UndirectedSparseGraph>
        (gvf,gef,g);


        String format = "[V]|[V]\n";
        gfp.createGraph(format,"facebook_combined.txt",false);
        GraphAnalysis<Vertex,Edge> k = new GraphAnalysis(g);

        BRBGraph vis = new BRBGraph<Vertex,Edge>(g,k);
        vis.setVisible(true);

    
    }
}    


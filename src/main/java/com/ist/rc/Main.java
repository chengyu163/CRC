package com.ist.rc;
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

        UndirectedSparseGraph g = new UndirectedSparseGraph<Vertex,Edge>();
        GraphFileParser<UndirectedSparseGraph> gfp = new GraphFileParser<UndirectedSparseGraph>(new GeneralVertexFactory<UndirectedSparseGraph<Vertex,Edge>>(),
         new GeneralEdgeFactory<Vertex,UndirectedSparseGraph<Vertex,Edge>>(), g);

        gfp.createGraph("V       V\n","gset.txt",false);

        System.out.println(g.getVertexCount());
        


    }
}



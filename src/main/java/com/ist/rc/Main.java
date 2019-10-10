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
//import com.google.common.base.Supplier; //Jung uses common base and not java util
/*
* DON'T MIND THIS FILE. 
*/


public class Main {
    public static void main( String[] args )
    {
    	System.out.println("it's working\n\n\n\n\n\n");
    	Graph<Integer, Integer> g = new UndirectedSparseGraph<Integer,Integer>();
    	Factory<UndirectedGraph<Integer,Integer>> g2 = new Factory<UndirectedGraph<Integer,Integer>>(){ 
    		public UndirectedSparseGraph<Integer,Integer>create(){ 
    			return new UndirectedSparseGraph<Integer,Integer>();
    		}
    	};
    	
        Factory<Integer> k = new Factory<Integer>() {
    		int count;
    		@Override
    		public Integer create() {
    			return count++;
    		}
    	};

    	ErdosRenyiGenerator<Integer,Integer> ergenertor= new ErdosRenyiGenerator<Integer,Integer>(g2,
    		k,k,10,0.5);
 
    }
}



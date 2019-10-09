package com.ist.rc;
import edu.uci.ics.jung.graph.Hypergraph;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
/*
* Concentrate the main metrics (Jung has them dispersed 
* and some not completely done.) in one class. As well, as
* some other utility functions we might find convinient.
*/
public class GraphAnalysis<V,E> {

	Hypergraph g;
	ArrayList<Integer> degreeDistribution = null;
	public GraphAnalysis(Hypergraph graph){
		g = graph;
	}

    public double averageDegree(){
    	if(averageDegree == null)
    		averageDegree = 2*g.getEdgeCount()/g.getVertexCount();
    	return averageDegree;

    }

    /*Degree Distribution absolute format*/
    public ArrayList degreeDistribution(){
    	if(degreeDistribution == null){
    		ArrayList list = new ArrayList(g.getVertices());
    		ArrayList degrees = new ArrayList(g.getVertexCount());
   			for(int i=0; i<list.size(); i++){
    			degrees.set(g.getNeighborCount(list.get(i)),degrees.get(i)++);
    		}
    	}
    	return degreeDistribution;
    }

    public double averagePathLength(){
    	return 0.0;
    }

    public double clusteringCoefficient(){
    	return 0.0;
    }

    /*Suppose to put up a graphic or something about the degree distribution*/
    public static void plotDegreeDistribution(){
    	return;
    }

}
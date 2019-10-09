package com.ist.rc;
import edu.uci.ics.jung.graph.Hypergraph;
import java.util.ArrayList;

/*
* Concentrate the main metrics (Jung has them dispersed 
* and some not completely done.) in one class. As well, as
* some other utility functions we might find convinient.
*/
public class GraphAnalysis {

	Hypergraph g;

	public GraphAnalysis(Hypergraph graph){
		g = graph;
	}

    public double averageDegree(){
    	return 0.0;
    }

    /*Doesn't need to be arrayList*/
    public ArrayList degreeDistribution(){
    	return null;
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
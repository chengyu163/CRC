package main.java.com.ist.rc;
import java.util.ArrayList;
import java.util.Map;

import edu.uci.ics.jung.graph.Graph;
/*
* Concentrate the main metrics (Jung has them dispersed 
* and some not completely done.) in one class. As well, as
* some other utility functions we might find convinient.
*
* Note: It is assumed who uses this class knows V and E classes
* from graph g. s
*/
public class GraphAnalysis<V,E>{

	Graph<Integer, Integer> g;
	ArrayList<Integer> degreeDistribution = null;
	double apl = -1;
	Map<V,Double> clusteringCoefficients = null;
	double averageDegree = -1;
	public GraphAnalysis(Graph<Integer, Integer> graph){
		g = graph;
	}

    public double averageDegree(){
    	if(averageDegree == -1)
    		averageDegree = 2*g.getEdgeCount()/g.getVertexCount();
    	return averageDegree;

    }

    /*Degree Distribution absolute format*/
    public ArrayList<Integer> degreeDistribution(){
    	if(degreeDistribution == null){
    		ArrayList<Integer> list = new ArrayList<Integer>(g.getVertices());
    		ArrayList<Integer> degrees = new ArrayList<Integer>(g.getVertexCount());
    		for( int i=0; i<degrees.size(); i++){ //Note:I don't like this for.
    			degrees.set(i,0);
    		}
   			for(int i=0; i<list.size(); i++){
   				int incDegree= degrees.get(i) + 1; 
    			degrees.set(g.getNeighborCount(list.get(i)),incDegree);
    		}
    	}
    	return degreeDistribution;
    }

    /* NOTE: diameter should also be a cool thing to calculate */
    public double averagePathLength(){
    /*	use DistanceStatistics.averageDistances(g)? */
    	return 0.0;
    }

    public double clusteringCoefficient(){
	/* use Metrics.clusteringCoefficients(g)? */
		return 0.0; 
    }

    /*Suppose to put up a graphic or something about the degree distribution*/
    public static void plotDegreeDistribution(){
    	return;
    }

}
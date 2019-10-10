package main.java.com.ist.rc;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.metrics.Metrics;
import edu.uci.ics.jung.algorithms.shortestpath.DistanceStatistics;
import edu.uci.ics.jung.algorithms.shortestpath.UnweightedShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Graphs;
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
	List<Integer> degreeDistribution = null;
	double apl = -1;
	Map<Integer,Double> clusteringCoefficients = null;
	double averageClusteringCoefficient = 0;
	double averageDegree = -1;
	//averagePathLength
	public GraphAnalysis(Graph<Integer, Integer> graph){
		g = graph;
		degreeDistribution = degreeDistribution();
		clusteringCoefficients = clusteringCoefficient();
		averageClusteringCoefficient = averageClusteringCoefficient();
		averageDegree = averageDegree();
	}

    private double averageDegree() {
		return degreeDistribution.stream()
				.mapToInt(Integer::intValue).average().getAsDouble();
	}

	public double getAverageDegree(){
    	if(averageDegree == -1)
    		averageDegree = 2*g.getEdgeCount()/g.getVertexCount();
    	return averageDegree;

    }

    /*Degree Distribution absolute format*/
    public List<Integer> degreeDistribution(){
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
    public Map<Integer, Integer> averagePathLength(){
    /*	use DistanceStatistics.averageDistances(g)? */
    	Map<Integer, Integer> distances = (Map<Integer, Integer>) DistanceStatistics.averageDistances(g, new UnweightedShortestPath<Integer, Integer>(g));
    	//dunno if it s corret, gonna study more and do it later
    	return distances;
    }

    public Map<Integer, Double> clusteringCoefficient(){
	/* use Metrics.clusteringCoefficients(g)? */
    	return Metrics.clusteringCoefficients(g);
    	
    }
    
    public double averageClusteringCoefficient() {
    	int count = 0;
    	for (Entry<Integer, Double> entry: clusteringCoefficients.entrySet()) {
			count+=entry.getValue();
		}
		return count/clusteringCoefficients.size(); 
    }

    /*Suppose to put up a graphic or something about the degree distribution*/
    public static void plotDegreeDistribution(){
    	return;
    }

}
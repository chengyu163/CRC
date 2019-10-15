package main.java.com.ist.rc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import edu.uci.ics.jung.algorithms.metrics.Metrics;
import edu.uci.ics.jung.algorithms.shortestpath.DistanceStatistics;
import edu.uci.ics.jung.algorithms.shortestpath.UnweightedShortestPath;
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
	List<Integer> degreeDistribution = null;
	double apl = -1;
	Map<Integer,Double> clusteringCoefficients = null;
	double averageClusteringCoefficient = 0;
	double averageDegree = -1;
	//averagePathLength
	//
	Map<Integer, MutableInt> mapNumNodeWithDegree = null;
	public GraphAnalysis(Graph<Integer, Integer> graph){
		g = graph;
	}

    private Map<Integer, MutableInt> MappingNumNodeWithDegree() {
    	Map<Integer, MutableInt> map = new HashMap<Integer, MutableInt>();
		for (Integer degree: degreeDistribution) {
			if(map.containsKey(degree)) 
				map.get(degree).increment();
			 else
				 map.put(degree, new MutableInt());
		}
		return map;
	}

/*	private double averageDegree() {
		return degreeDistribution.stream().mapToInt(Integer::intValue).average().getAsDouble();
	}*/

	public double getAverageDegree(){
    	if(averageDegree == -1)
    		averageDegree = 2*g.getEdgeCount()/g.getVertexCount();
    	return averageDegree;
    }

    /*Degree Distribution absolute format*/
    public List<Integer> degreeDistribution(){
    	return g.getVertices().stream().map(v -> g.getNeighborCount(v)).collect(Collectors.toList());
    
    }

    /* NOTE: diameter should also be a cool thing to calculate */
    public Map<Integer, Integer> averagePathLength(){
    /*	use DistanceStatistics.averageDistances(g)? */
    	Map<Integer, Integer> distances = 
        (Map<Integer, Integer>) DistanceStatistics.averageDistances(g, new UnweightedShortestPath<Integer, Integer>(g));
    	//dunno if it s corret, gonna study more and do it later
    	return distances;
    }

    public Map<Integer, Double> clusteringCoefficient(){
    	return Metrics.clusteringCoefficients(g);
    }
    
    public double averageClusteringCoefficient() {
    	double count = 0;
    	for (Entry<Integer, Double> entry: clusteringCoefficients.entrySet()) {
			count+=entry.getValue();
		}
		return count/clusteringCoefficients.size(); 
    }

    /*Suppose to put up a graphic or something about the degree distribution*/
    public static void plotDegreeDistribution(){
    	return ;
    }

}
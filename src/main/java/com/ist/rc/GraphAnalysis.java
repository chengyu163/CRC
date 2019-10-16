package main.java.com.ist.rc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections15.Transformer;
import org.omg.PortableInterceptor.TRANSPORT_RETRY;

import edu.uci.ics.jung.algorithms.metrics.Metrics;
import edu.uci.ics.jung.algorithms.shortestpath.Distance;
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
	double averagePathLength = 0;
	Map<Integer, MutableInt> mapNumNodeWithDegree = null;
	List<Map<Integer, Number>> listOfShortesPathOfEachVertex = null;
	public GraphAnalysis(Graph<Integer, Integer> graph){
		g = graph;
		degreeDistribution = degreeDistribution();
		clusteringCoefficients = clusteringCoefficient();
		averageClusteringCoefficient = averageClusteringCoefficient();
		averageDegree = averageDegree();
		mapNumNodeWithDegree = MappingNumNodeWithDegree();
		listOfShortesPathOfEachVertex = listOfShortesPathOfEachVertex();
		averagePathLength = averagePathLength();
	}

	public Graph<Integer, Integer> getG() {
		return g;
	}

	public void setG(Graph<Integer, Integer> g) {
		this.g = g;
	}

	public List<Integer> getDegreeDistribution() {
		return degreeDistribution;
	}

	public void setDegreeDistribution(List<Integer> degreeDistribution) {
		this.degreeDistribution = degreeDistribution;
	}

	public double getApl() {
		return apl;
	}

	public void setApl(double apl) {
		this.apl = apl;
	}

	public Map<Integer, Double> getClusteringCoefficients() {
		return clusteringCoefficients;
	}

	public void setClusteringCoefficients(Map<Integer, Double> clusteringCoefficients) {
		this.clusteringCoefficients = clusteringCoefficients;
	}

	public double getAverageClusteringCoefficient() {
		return averageClusteringCoefficient;
	}

	public void setAverageClusteringCoefficient(double averageClusteringCoefficient) {
		this.averageClusteringCoefficient = averageClusteringCoefficient;
	}

	public Map<Integer, MutableInt> getMapNumNodeWithDegree() {
		return mapNumNodeWithDegree;
	}

	public void setMapNumNodeWithDegree(Map<Integer, MutableInt> mapNumNodeWithDegree) {
		this.mapNumNodeWithDegree = mapNumNodeWithDegree;
	}

	public void setAverageDegree(double averageDegree) {
		this.averageDegree = averageDegree;
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

    private double averageDegree() {
		return degreeDistribution.stream().mapToInt(Integer::intValue).average().getAsDouble();
	}

	public double getAverageDegree(){
    	if(averageDegree == -1)
    		averageDegree = 2*g.getEdgeCount()/g.getVertexCount();
    	return averageDegree;
    }

    /*Degree Distribution absolute format*/
    public List<Integer> degreeDistribution(){
    	return g.getVertices().stream().map(v -> g.getNeighborCount(v)).collect(Collectors.toList());
    }

    public List<Map<Integer, Number>> listOfShortesPathOfEachVertex(){
    	UnweightedShortestPath<Integer, Integer> f = new UnweightedShortestPath<Integer, Integer>(g);
    	List<Map<Integer, Number>> listOfShortesPathOfEachVertex = new ArrayList<>();
    	for (Integer vertex: g.getVertices()) {
			listOfShortesPathOfEachVertex.add(f.getDistanceMap(vertex.intValue()));
		}
    	return listOfShortesPathOfEachVertex;
    }
    
    private double averagePathLength() {
    	double  totalLength =0;
    	for (Map<Integer, Number> shortestPathLength : listOfShortesPathOfEachVertex) {
			totalLength += shortestPathLength.values().stream().mapToDouble(value-> value.doubleValue()).sum();
		}
    	return totalLength/(g.getVertexCount()*(g.getVertexCount()-1));
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
    

}
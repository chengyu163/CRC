package main.java.com.ist.rc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import edu.uci.ics.jung.algorithms.metrics.Metrics;
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

	private Graph<V,E> g;
	private List<Integer> degreeDistribution = null;
	private Map<V,Double> clusteringCoefficients = null;
	private double averageClusteringCoefficient = -1;
	private double averageDegree = -1;
	private double averagePathLength = -1;
	private Map<Integer, MutableInt> mapNumNodeWithDegree = null;
	private List<Map<V, Number>> listOfShortesPathOfEachVertex = null;
	private int higherDegree = 0;
	private int lowestDegree = 0;

	public GraphAnalysis(Graph<V,E> graph){
		g = graph;
	}

	public Graph<V,E> getG() {
		return g;
	}

	public List<Integer> getDegreeDistribution() {
		if(degreeDistribution == null){
			degreeDistribution = degreeDistribution();
		}
		return degreeDistribution;
	}

	public Map<V, Double> getClusteringCoefficients() {
		if(clusteringCoefficients == null){
			clusteringCoefficients = clusteringCoefficient();
		}
		return clusteringCoefficients;
	}

	public double getAverageClusteringCoefficient() {
		if(averageClusteringCoefficient == -1){
			averageClusteringCoefficient = averageClusteringCoefficient();
		}
		return averageClusteringCoefficient;
	}

	public double getAverageDegree(){
    	if(averageDegree == -1)
    		averageDegree = 2*g.getEdgeCount()/g.getVertexCount();
    	return averageDegree;
    }

    public double getAveragePathLength(){
		if(averagePathLength == -1){
			averagePathLength = averagePathLength();
		}
		return averagePathLength;
	}

    public Map<Integer, MutableInt> getMapNumNodeWithDegree() {
		if(mapNumNodeWithDegree == null){
			mapNumNodeWithDegree = MappingNumNodeWithDegree();
		}
		return mapNumNodeWithDegree;
	}

	public List<Map<V, Number>> getListOfShortesPathOfEachVertex(){
		if(listOfShortesPathOfEachVertex == null){
			listOfShortesPathOfEachVertex = listOfShortesPathOfEachVertex();
		}
		return listOfShortesPathOfEachVertex;
	}

	//getMapNumNode will call MappingNumNodeWithDegree if it was never called
	//that method calculates higher and lowest degrees
	public int getHighestDegree(){
		getMapNumNodeWithDegree();
		return higherDegree;
	}

	public int getLowestDegree(){
		getMapNumNodeWithDegree();
		return lowestDegree;
	}

	/**					 **\ 
	*  INTERNAL OPERATIONS *
	\**                  **/
	private Map<Integer, MutableInt> MappingNumNodeWithDegree() {
    	Map<Integer, MutableInt> map = new HashMap<Integer, MutableInt>();
		for (Integer degree: getDegreeDistribution()) {
			if(map.containsKey(degree)) 
				map.get(degree).increment();
			 else{
				 map.put(degree, new MutableInt());
			 }
			 int value = map.get(degree).get();
			 if(value  > higherDegree){
			 	higherDegree = value;
			 }

			 if(value < lowestDegree){
			 	lowestDegree = value;
			 }
		}
		return map;
	}

    private double averageDegree() {
		return degreeDistribution.stream().mapToInt(Integer::intValue).average().getAsDouble();
	}

    /*Degree Distribution absolute format*/
    public List<Integer> degreeDistribution(){
    	return g.getVertices().stream().map(v -> g.getNeighborCount(v)).collect(Collectors.toList());
    }

    private List<Map<V, Number>> listOfShortesPathOfEachVertex(){
    	UnweightedShortestPath<V, E> f = new UnweightedShortestPath<V, E>(g);
    	List<Map<V,Number>> listOfShortesPathOfEachVertex = new ArrayList<>();
    	for (V vertex: g.getVertices()) {
			listOfShortesPathOfEachVertex.add(f.getDistanceMap(vertex));
		}
    	return listOfShortesPathOfEachVertex;
    }
    
    private double averagePathLength() {
    	double  totalLength =0;
    	for (Map<V, Number> shortestPathLength : getListOfShortesPathOfEachVertex()) {
			totalLength += shortestPathLength.values().stream().mapToDouble(value-> value.doubleValue()).sum();
		}
    	return totalLength/(g.getVertexCount()*(g.getVertexCount()-1));
  	}


    private Map<V, Double> clusteringCoefficient(){
    	return Metrics.clusteringCoefficients(g);
    }
    
    private double averageClusteringCoefficient() {
    	double count = 0;
    	for (Entry<V, Double> entry: getClusteringCoefficients().entrySet()) {
			count+=entry.getValue();
		}
		return count/getClusteringCoefficients().size(); 
    }
    
    public void print(){
    	//System.out.println("Degree Distribution:" + getDegreeDistribution());
    //	System.out.println("clusteringCoefficients" + getClusteringCoefficients());
    	//System.out.println("averageClusteringCoefficient" + getAverageClusteringCoefficient());
    //	System.out.println("averageDegree" + getAverageDegree());
    	//System.out.println("averagePathLength" + getAveragePathLength());
    	//System.out.println("mapNumNodeWithDegree" + getMapNumNodeWithDegree());
    //	System.out.println("listOfShortesPathOfEachVertex" + getListOfShortesPathOfEachVertex());
    }

}


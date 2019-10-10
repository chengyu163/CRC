package main.java.com.ist.rc;

import edu.uci.ics.jung.algorithms.generators.random.BarabasiAlbertGenerator;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections15.Factory;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;

public class test {


public static void main(String[] args) {	
	


	Set<Integer> seedVertices = new HashSet<Integer>();
	for (int i = 0; i < 10; i++) {
		seedVertices.add(i);
	}
	Factory<Integer> vertexFactory = new Factory<Integer>() {
		int count;
		@Override
		public Integer create() {
			// TODO Auto-generated method stub
			return count++;
		}
	};
	Factory<Integer> edgeFactory = new Factory<Integer>() {
		int count;
		@Override
		public Integer create() {
			// TODO Auto-generated method stub
			return count++;
		}
	};
	Factory<Graph<Integer, Integer>> graphFactory = SparseGraph.getFactory();
	BarabasiAlbertGenerator<Integer, Integer> bag = new BarabasiAlbertGenerator<Integer, Integer>(graphFactory, vertexFactory, edgeFactory, 4, 4, 0, seedVertices);
	
	Graph<Integer, Integer> graph =bag.create();
	bag.evolveGraph(100);
	
}
}

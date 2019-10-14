package main.java.com.ist.rc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.jung.graph.Graph;

public class DataToGraph {
	
	Graph<Integer, Integer> graph = null;
	
	public DataToGraph(File file) {
		graph = DataToGraph(file);
	}

	private Graph<Integer, Integer> DataToGraph(File file) {
		List<Integer> listVertex = new ArrayList<Integer>();
		List<Integer> listEdge = new ArrayList<Integer>();
		
	}
}

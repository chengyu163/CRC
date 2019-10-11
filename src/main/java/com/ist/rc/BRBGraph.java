package main.java.com.ist.rc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.JFrame;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.algorithms.generators.random.BarabasiAlbertGenerator;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;

public class BRBGraph 
{
    public static void main(String[] args) 
    {   
                createAndShowGUI();
 
    }

    private static void createAndShowGUI()
    {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Graph<Integer, Integer> graph = createBRBGraph();

        Dimension size = new Dimension(800,800);
        VisualizationViewer<Integer, Integer> vv = 
            new VisualizationViewer<Integer, Integer>(
                new FRLayout<Integer, Integer>(graph, size));

        f.getContentPane().add(vv);
        f.setSize(size);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

	private static Graph<Integer, Integer> createBRBGraph() {
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
    	return graph;
	}




   
}
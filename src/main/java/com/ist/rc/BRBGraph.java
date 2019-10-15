package main.java.com.ist.rc;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.algorithms.generators.random.BarabasiAlbertGenerator;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;

public class BRBGraph extends JFrame {
	private JPanel mainPanel;
	private JPanel graphPanel;
	private JPanel plotGraphicPanel;
	private JPanel statisticPanel;

	public BRBGraph() {
		
		Graph<Integer, Integer> graph = createBRBGraph();

		    JPanel panel = new JPanel(new GridLayout(2,2));
	        JPanel scrollPane1 = new JPanel();
	        panel.add(scrollPane1,0,0);
	        scrollPane1.add(showGraph(graph));
	         
	        JPanel scrollPane2 = new JPanel();
	        panel.add(scrollPane2,0,1);
	        scrollPane2.add(showStatistics(graph));
	         
	        final JEditorPane textField3 = new JEditorPane();
	        JPanel scrollPane3 = new JPanel();
	        panel.add(scrollPane3);
//	        scrollPane3.add(plotGraphic(graph));
	         
	        final JEditorPane textField4 = new JEditorPane();
	        JPanel scrollPane4 = new JPanel();
	        panel.add(scrollPane4);

	        add(panel);
	        pack();
		
	}

	private Component plotGraphic(Graph<Integer, Integer> graph) {
		// doing this after including API of free Chart
		return null;
	}

	private Component showGraph(Graph<Integer, Integer> graph) {

		Dimension size = new Dimension(700, 700);
		VisualizationViewer<Integer, Integer> vv = new VisualizationViewer<Integer, Integer>(
				new FRLayout<Integer, Integer>(graph, size));
		DefaultModalGraphMouse<Integer, Integer> graphMouse = new DefaultModalGraphMouse<Integer, Integer>();
		vv.setGraphMouse(graphMouse);
		return vv;
	}

	private Component showStatistics(Graph<Integer, Integer> graph) {
		GraphAnalysis<Integer, Integer> graphAnalysis = new GraphAnalysis<Integer, Integer>(graph);
		String label = "<html>AverageDegree ="+graphAnalysis.averageDegree+
				"<br/> AverageClusteringCoefficient ="+ graphAnalysis.averageClusteringCoefficient+"</html>";
		JLabel metrics = new JLabel(label);
		return metrics;
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
		BarabasiAlbertGenerator<Integer, Integer> bag = new BarabasiAlbertGenerator<Integer, Integer>(graphFactory,
				vertexFactory, edgeFactory, 4, 4, 0, seedVertices);
		Graph<Integer, Integer> graph = bag.create();
		bag.evolveGraph(100);
		return graph;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new BRBGraph().setVisible(true);
			}
		});
	}
}
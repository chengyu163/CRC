package main.java.com.ist.rc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.collections15.Factory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import edu.uci.ics.jung.algorithms.generators.random.BarabasiAlbertGenerator;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;

public class BRBGraph extends JFrame {

	/**
	 * 
	 */
	GraphAnalysis graphAnalysis = null;
	private static final long serialVersionUID = 1L;

	public BRBGraph(Graph graph,GraphAnalysis _graphAnalysis) {
			graphAnalysis = _graphAnalysis;		
		    JPanel panel = new JPanel(new GridLayout(2,2));
		    //panel to show the graph
	        JPanel scrollPane1 = new JPanel();
	        panel.add(scrollPane1,0,0);
	        scrollPane1.add(showGraph(graph));
	         
	        //panel to show the statistics
	        JPanel scrollPane2 = new JPanel();
	        panel.add(scrollPane2,0,1);
	        scrollPane2.add(showStatistics(graph));
	         
	        // panel to plot the degree distribution
	        JPanel scrollPane3 = new JPanel();
	        panel.add(scrollPane3);
	        scrollPane3.add(plotGraphicDegreeDistribution(graph));
	         
	        //panel to plot the clustering coefficient
	       /* JPanel scrollPane4 = new JPanel();
	        panel.add(scrollPane4);
	        scrollPane4.add(plotGraphicClusterCoefficient(graph));
			*/
	        add(panel);
	        pack();
		
	}
	

/*
	private Component plotGraphicClusterCoefficient(Graph<Integer, Integer> graph) {
		XYDataset dataset = createDataset1(graph);
        JFreeChart chart = createChart1(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        return chartPanel;
	}
*/
	private JFreeChart createChart1(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Cluster Coefficient", 
                "Node", 
                "Coefficient", 
                dataset, 
                PlotOrientation.VERTICAL,
                true, 
                true, 
                false 
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setBaseShapesVisible(true);
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("CoefficentClustering",
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );

        return chart;
	}
	/*
	* 	This is a problem because vertexes do not have numbers necessarily
	*
	/*private XYDataset createDataset1(Graph<Integer, Integer> graph) {

        XYSeries series = new XYSeries(""); 
		GraphAnalysis<Integer, Integer> graphAnalysis = new GraphAnalysis<Integer, Integer>(graph);
		Map<Integer, Double> clustercoefficient = graphAnalysis.getClusteringCoefficients();
		for (Entry<Integer, Double> coefficient : clustercoefficient.entrySet()) {
			series.add(coefficient.getKey().intValue(), coefficient.getValue().doubleValue());
		}
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
	}
	*/
	private Component plotGraphicDegreeDistribution(Graph<Integer, Integer> graph) {
		XYDataset dataset = createDataset(graph);
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        return chartPanel;
	}

	private XYDataset createDataset(Graph<Integer, Integer> graph) {

        XYSeries series = new XYSeries(""); 
		GraphAnalysis<Integer, Integer> graphAnalysis = new GraphAnalysis<Integer, Integer>(graph);
		Map<Integer, MutableInt> degreeDistribution = graphAnalysis.getMapNumNodeWithDegree();
		for (Entry<Integer, MutableInt> degree : degreeDistribution.entrySet()) {
			series.add(degree.getKey().intValue(), (double)degree.getValue().get()/graph.getEdgeCount());
		}
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Degree Distribution", 
                "Degree", 
                "P(x)", 
                dataset, 
                PlotOrientation.VERTICAL,
                true, 
                true, 
                false 
        );

        XYPlot plot = chart.getXYPlot();
        LogAxis yAxis = new LogAxis("P(X)");
        plot.setRangeAxis(yAxis);
        LogAxis xAxis = new LogAxis("Degree");
        plot.setDomainAxis(xAxis);

        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setBaseShapesVisible(true);
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("DegreeDistribution",
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );

        return chart;

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
		String label = "<html>AverageDegree ="+graphAnalysis.getAverageDegree()+
				"<br/> AverageClusteringCoefficient ="+ graphAnalysis.getAverageClusteringCoefficient()+"<br/>"
						+ "AveragePathLength ="+graphAnalysis.getAveragePathLength()+"</html>";
		JLabel metrics = new JLabel(label);
		return metrics;
	}

/*	private static Graph<Integer, Integer> createBRBGraph(String[] args) {
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
				vertexFactory, edgeFactory, 5, 5, 0, seedVertices);
		Graph<Integer, Integer> graph = bag.create();
			bag.evolveGraph(500);
		return graph;
	}

//	public static void main(String[] args) {
//		BRBGraph vis = new BRBGraph();
//		vis.setVisible(true);
//	} */
}
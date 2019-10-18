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
import org.apache.commons.collections15.Transformer;
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
import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;

import java.awt.Paint;
import java.awt.Color;
public class BRBGraph<V,E> extends JFrame {

	/**
	 * 
	 */
	GraphAnalysis<V,E> graphAnalysis = null;
	Graph<V,E> graph = null;
	private static final long serialVersionUID = 1L;

	public BRBGraph(Graph<V,E> _graph,GraphAnalysis<V,E> _graphAnalysis) {
			graph = _graph;
			graphAnalysis = _graphAnalysis;		
		    JPanel panel = new JPanel(new GridLayout(2,2));
		    //panel to show the graph
	        JPanel scrollPane1 = new JPanel();
	        panel.add(scrollPane1,0,0);
	        scrollPane1.add(showGraph());
	         
	        //panel to show the statistics
	        JPanel scrollPane2 = new JPanel();
	        panel.add(scrollPane2,0,1);
	        scrollPane2.add(showStatistics());
	         
	        // panel to plot the degree distribution
	        JPanel scrollPane3 = new JPanel();
	        panel.add(scrollPane3);
	        scrollPane3.add(plotGraphicDegreeDistribution());
	         
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
	private Component plotGraphicDegreeDistribution() {
		XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        return chartPanel;
	}

	private XYDataset createDataset() {

        XYSeries series = new XYSeries(""); 
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

	private Component showGraph() {

		Dimension size = new Dimension(700, 700);
		VisualizationViewer<V, E> vv = new VisualizationViewer<V, E>(
			new FRLayout<V, E>(graph, size));
		DefaultModalGraphMouse<V, E> graphMouse = new DefaultModalGraphMouse<V, E>();
		vv.setGraphMouse(graphMouse);
		Transformer<V,Paint> transformer = new Transformer<V, Paint>() {
      @Override public Paint transform(V arg0) { 
      		//float y = (graph.getNeighborCount(arg0)- graphAnalysis.getLowestDegree())/(graphAnalysis.getHighestDegree() - graphAnalysis.getLowestDegree());
      		int y = 25;
      		Vertex i = (Vertex) arg0;
      		double r = (graph.getNeighborCount(arg0)- graphAnalysis.getLowestDegree());
      		r = r/(graphAnalysis.getHighestDegree() - graphAnalysis.getLowestDegree());
      		float j = 1 - (float) r;

      		return hslColor((float)0.66,(float)1,j);
       }
    };
		vv.getRenderContext().setVertexDrawPaintTransformer(transformer);
        vv.getRenderContext().setVertexFillPaintTransformer(transformer);
		return vv;
	}

	private Component showStatistics() {
		String label = "<html>AverageDegree ="+graphAnalysis.getAverageDegree()+
				"<br/> AverageClusteringCoefficient ="+ graphAnalysis.getAverageClusteringCoefficient()+"<br/>"
						+ "AveragePathLength ="+graphAnalysis.getAveragePathLength()+"</html>";
		JLabel metrics = new JLabel(label);
		return metrics;
	}


	/* 
	* These two next functions were adapted from
	* https://stackoverflow.com/questions/2997656/how-can-i-use-the-hsl-colorspace-in-java
	* Author: https://stackoverflow.com/users/519654/xtempore
	* Attribution format required by StackOverflow: https://stackoverflow.blog/2009/06/25/attribution-required/
	*/
    public Color hslColor(float h, float s, float l) {
	    float q, p, r, g, b;

	    if (s == 0) {
	        r = g = b = l; // achromatic
	    } else {
	        q = l < 0.5 ? (l * (1 + s)) : (l + s - l * s);
	        p = 2 * l - q;
	        r = hue2rgb(p, q, h + 1.0f / 3);
	        g = hue2rgb(p, q, h);
	        b = hue2rgb(p, q, h - 1.0f / 3);
	    }
	    return new Color(Math.round(r * 255), Math.round(g * 255), Math.round(b * 255));
    }
	private float hue2rgb(float p, float q, float h) {
	    if (h < 0) {
	        h += 1;
	    }

	    if (h > 1) {
	        h -= 1;
	    }

	    if (6 * h < 1) {
	        return p + ((q - p) * 6 * h);
	    }

	    if (2 * h < 1) {
	        return q;
	    }

	    if (3 * h < 2) {
	        return p + ((q - p) * 6 * ((2.0f / 3.0f) - h));
	    }

	    return p;
	}
}
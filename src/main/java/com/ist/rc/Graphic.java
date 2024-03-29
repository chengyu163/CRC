//package main.java.com.ist.rc;
//
//
// import org.jfree.*;
//
//public class XYSeriesDemo extends ApplicationFrame {
//
///**
// * A demonstration application showing an XY series containing a null value.
// *
// * @param title  the frame title.
// */
//public XYSeriesDemo(final String title) {
//
//    super(title);
//    final XYSeries series = new XYSeries("Random Data");
//    series.add(1.0, 500.2);
//    series.add(5.0, 694.1);
//    series.add(4.0, 100.0);
//    series.add(12.5, 734.4);
//    series.add(17.3, 453.2);
//    series.add(21.2, 500.2);
//    series.add(21.9, null);
//    series.add(25.6, 734.4);
//    series.add(30.0, 453.2);
//    final XYSeriesCollection data = new XYSeriesCollection(series);
//    final JFreeChart chart = ChartFactory.createXYLineChart(
//        "XY Series Demo",
//        "X", 
//        "Y", 
//        data,
//        PlotOrientation.VERTICAL,
//        true,
//        true,
//        false
//    );
//
//    final ChartPanel chartPanel = new ChartPanel(chart);
//    chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
//    setContentPane(chartPanel);
//
//}
//
//// ****************************************************************************
//// * JFREECHART DEVELOPER GUIDE                                               *
//// * The JFreeChart Developer Guide, written by David Gilbert, is available   *
//// * to purchase from Object Refinery Limited:                                *
//// *                                                                          *
//// * http://www.object-refinery.com/jfreechart/guide.html                     *
//// *                                                                          *
//// * Sales are used to provide funding for the JFreeChart project - please    * 
////
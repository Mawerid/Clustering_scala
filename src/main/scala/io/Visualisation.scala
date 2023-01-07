package io

//import io.displayPlot
//import com.cibo.evilplot.plot._
//import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
//import com.cibo.evilplot.numeric.Point

object Visualisation extends App {
//
//  val data = Seq.tabulate(100) { i =>
//    Point(i.toDouble, scala.util.Random.nextDouble())
//  }
//  displayPlot(ScatterPlot(data)
//    .xAxis()
//    .yAxis()
//    .frame()
//    .xLabel("x")
//    .yLabel("y")
//    .render())

//  /**
//   * generate plot from 2 dimension data
//   *
//   * @param data   data
//   * @param xLabel label of x axes
//   * @param yLabel label of y axes
//   * @param title  main title of plot
//   * @param legend legend of plot
//   * @return png plot as ByteArray
//   */
//  def drawScatterToByteArray(data: List[List[List[Double]]], xLabel: String, yLabel: String, title: String, legend: String): Array[Byte] = {
//
//    val df = data.map(lst => lst.head -> lst(1))
//
//    val plot = xyplot((df, List(point()), InLegend(legend)))(
//      par(
//        main = title,
//        xlab = xLabel,
//        ylab = yLabel
//      )
//    )
//
//    val fig0 = xyplot(
//      df("Sepal.Length", "Sepal.Width", "spec")
//    )(
//      par(extraLegend = spec2Num.toSeq.map(x =>
//        x._1 -> PointLegend(
//          shape = Shape.rectangle(0, 0, 1, 1),
//          color = DiscreteColors(spec2Num.size)(x._2)
//        )
//      ),
//        xlab = "Sepal.Length",
//        ylab = "Sepal.Width",
//        main = "Iris data")
//    )
//
//    val builtPlot = plot.build
//
//    pngToByteArray(
//      builtPlot,
//      width = 2000)
//  }


}

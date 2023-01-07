package io

import org.nspl._
import org.nspl.awtrenderer._

object Visualisation {

  // Вроде как теперь все работает
  def drawScatterToByteArray(data: List[List[Double]], xLabel: String, yLabel: String, title: String, legend: String): Array[Byte] = {

    val df = data.map(lst => lst.head -> lst(1))

    val plot = xyplot((df, List(point()), InLegend(legend)))(
      par(
        main = title,
        xlab = xLabel,
        ylab = yLabel
      )
    )

    val builtPlot = plot.build
//    implicit val renderer: Renderer[Elems2[XYPlotArea, Legend], JavaRC] = {
//      Renderer(builtPlot, new JavaRC())
//    }

    pngToByteArray(
      builtPlot,
      width = 2000)//(renderer)
  }
}

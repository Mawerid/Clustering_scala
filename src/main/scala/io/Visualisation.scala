package io

import org.nspl._
import org.nspl.awtrenderer._

object Visualisation {

  /**
   *
   * @param data
   * @param xLabel
   * @param yLabel
   * @param title
   * @param legend
   * @return
   */
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

    pngToByteArray(
      builtPlot,
      width = 2000)
  }
}

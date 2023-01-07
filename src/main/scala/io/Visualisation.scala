package io

import org.nspl._
import org.nspl.awtrenderer._

object Visualisation {

  /**
   * generate plot from 2 dimension data
   * @param data data
   * @param xLabel label of x axes
   * @param yLabel label of y axes
   * @param title main title of plot
   * @param legend legend of plot
   * @return png plot as ByteArray
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

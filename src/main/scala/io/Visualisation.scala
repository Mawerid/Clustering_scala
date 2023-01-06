package io

import org.nspl._
import org.nspl.awtrenderer._

object Visualisation {

  // Git'у не нравится почему-то, хотя все работает
//  def drawScatterToByteArray(data: List[List[Double]], xLabel: String, yLabel: String, title: String): Array[Byte] = {
//
//    val df = data.map(lst => lst.head -> lst(1))
//
//    val plot = xyplot(df)(
//      par(
//        main = title,
//        xlab = xLabel,
//        ylab = yLabel
//      )
//    )
//
//    pngToByteArray(
//      plot.build,
//      width = 2000)
//  }
}

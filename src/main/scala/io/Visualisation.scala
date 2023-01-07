package io

import smile.data.DataFrame

import java.awt.Color._
import smile.plot._
import smile.plot.swing._
import smile.plot.Render._

object Visualisation extends App {

  def prepareToPlot(data: List[List[Double]], clusters: List[List[List[Double]]]): DataFrame = {
    val names: Seq[String] = data.head.indices.map(i => s"coordinate $i") :+ "class"
    val transData = data.map(_.toArray).toArray

    val tmp = data
      .map(point => clusters.indices
        .map(i => if (clusters(i)
          .contains(point)) i.toDouble + 1d))
      .map(it => it
        .find(_.toString != "()")
        .get)
      .map(_.toString.toDouble).toArray

    DataFrame.of(transData.zip(tmp).map(it => it._1 :+ it._2), names: _*)
  }

  def draw2DPlot(df: DataFrame): Unit = {
    val plotBefore = plot(df, "coordinate 0", "coordinate 1", "coordinate 2", "class", '*')
    plotBefore.setAxisLabels("X", "Y", "Z")
    show(plotBefore)
  }

}

package io

import smile.data.DataFrame

import java.awt.Color._
import smile.plot._
import smile.plot.swing._
import smile.plot.Render._

object Visualisation extends App {

  def prepareToPlot(data: List[List[Double]], clusters: List[List[List[Double]]])
  : DataFrame = {
    val names: Seq[String] = data
      .head
      .indices
      .map(i => s"coordinate $i") :+ "class"

    val transData = data.map(_.toArray).toArray

    val tmp = data
      .map(point => clusters.indices
        .map(i => if (clusters(i)
          .contains(point)) i.toDouble + 1d))
      .map(it => it
        .find(_.toString != "()")
        .get)
      .map(_.toString.toDouble).toArray

    DataFrame
      .of(transData
        .zip(tmp)
        .map(it => it._1 :+ it._2), names: _*)
  }

  def draw2DPlotRaw(df: DataFrame
                    , coor1: String = "coordinate 0"
                    , coor2: String = "coordinate 1"
                    , xLabel: String = "X"
                    , yLabel: String = "Y"
                    , title: String = "Title")
  : Unit = {
    val plotB = plot(df, coor1, coor2, '*', RED)
    plotB.setAxisLabels(xLabel, yLabel)
    plotB.setLegendVisible(true)
    plotB.setTitle(title)
    show(plotB)
  }

  def draw2DPlot(df: DataFrame
                 , coor1: String = "coordinate 0"
                 , coor2: String = "coordinate 1"
                 , xLabel: String = "X"
                 , yLabel: String = "Y"
                 , title: String = "Title")
  : Unit = {
    val plotB = plot(df, coor1, coor2, "class", '*')
    plotB.setAxisLabels(xLabel, yLabel)
    plotB.setLegendVisible(true)
    plotB.setTitle(title)
    show(plotB)
  }

  def draw3DPlot(df: DataFrame
                 , coor1: String = "coordinate 0"
                 , coor2: String = "coordinate 1"
                 , coor3: String = "coordinate 2"
                 , xLabel: String = "X"
                 , yLabel: String = "Y"
                 , zLabel: String = "Z"
                 , title: String = "Title")
  : Unit = {
    val plotB = plot(df, coor1, coor2, coor3, "class", '*')
    plotB.setAxisLabels(xLabel, yLabel, zLabel)
    plotB.setLegendVisible(true)
    plotB.setTitle(title)
    show(plotB)
  }

}

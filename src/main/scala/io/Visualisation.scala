package io

import smile.data.DataFrame
import java.awt.Color._
import smile.plot._
import smile.plot.swing._
import smile.plot.Render._
import scala.io.StdIn.readLine

object Visualisation extends App {

  /**
   * Make DataFrame from our data
   *
   * @param data     raw data
   * @param clusters clusters
   * @return DataFrame with all categorised data
   */
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

  /**
   * Draw 2D plot without any categories
   *
   * @param df    dataframe with data
   * @param coor1 coordinate to show
   * @param coor2 coordinate to show
   *              //   * @param xLabel label of X axes
   *              //   * @param yLabel label of Y axes
   * @param title title of plot
   */
  def draw2DPlotRaw(df: DataFrame
                    , coor1: String = "coordinate 0"
                    , coor2: String = "coordinate 1"
                    //                    , xLabel: String = "X"
                    //                    , yLabel: String = "Y"
                    , title: String = "Title")
  : Unit = {
    val plotB = plot(df, coor1, coor2, '*', RED)
    //    plotB.setAxisLabels(xLabel, yLabel)
    plotB.setLegendVisible(true)
    plotB.setTitle(title)
    show(plotB)
  }

  /**
   * Draw 2D plot with categories
   *
   * @param df    dataframe with data
   * @param coor1 coordinate to show
   * @param coor2 coordinate to show
   *              //   * @param xLabel label of X axes
   *              //   * @param yLabel label of Y axes
   * @param title title of plot
   */
  def draw2DPlot(df: DataFrame
                 , coor1: String = "coordinate 0"
                 , coor2: String = "coordinate 1"
                 //                 , xLabel: String = "X"
                 //                 , yLabel: String = "Y"
                 , title: String = "Title")
  : Unit = {
    val plotB = plot(df, coor1, coor2, "class", '*')
    //    plotB.setAxisLabels(xLabel, yLabel)
    plotB.setLegendVisible(true)
    plotB.setTitle(title)
    show(plotB)
  }

  /**
   * Draw 3D plot with categories
   *
   * @param df    dataframe with data
   * @param coor1 coordinate to show
   * @param coor2 coordinate to show
   * @param coor3 coordinate to show
   *              //   * @param xLabel label of X axes
   *              //   * @param yLabel label of Y axes
   *              //   * @param zLabel label of Z axes
   * @param title title of plot
   */
  def draw3DPlot(df: DataFrame
                 , coor1: String = "coordinate 0"
                 , coor2: String = "coordinate 1"
                 , coor3: String = "coordinate 2"
                 //                 , xLabel: String = "X"
                 //                 , yLabel: String = "Y"
                 //                 , zLabel: String = "Z"
                 , title: String = "Title")
  : Unit = {
    val plotB = plot(df, coor1, coor2, coor3, "class", '*')
    //    plotB.setAxisLabels(xLabel, yLabel, zLabel)
    plotB.setLegendVisible(true)
    plotB.setTitle(title)
    show(plotB)
  }

  /**
   * Draw plot from input data
   *
   * @param data     raw data
   * @param clusters data after clustering
   */
  def drawing(data: List[List[Double]], clusters: List[List[List[Double]]]): Unit = {
    val df = prepareToPlot(data, clusters)
    println(df)
    println(df.summary())

    data.head.size match {
      case 1 => println("We cannot draw plot in 1 dimension :(")
      case 2 => draw2DPlot(df)
      case 3 => draw3DPlot(df)
      case _ => choosePlotting(df)
    }

    print("Do you want other plots? (Y/N) -> ")
    val answer = readLine()
    if (answer == "Y" || answer == "y") choosePlotting(df)
  }

  /**
   * Func to make plots from DataFrame
   *
   * @param df DataFrame
   */
  def choosePlotting(df: DataFrame): Unit = {
    print("Plot 2D or 3D? -> ")
    val choose = readLine()
    if (choose == "3D" || choose == "3" || choose == "3d") {

      print("Enter the values for X axes: -> ")
      var xAxes = ""
      var flag = false
      while (!flag) {
        xAxes = readLine()
        flag = df.names().contains(xAxes)
        if (!flag) println("No such column. Try again")
      }

      print("Enter the values for Y axes: -> ")
      var yAxes = ""
      flag = false
      while (!flag) {
        yAxes = readLine()
        flag = df.names().contains(yAxes)
        if (!flag) println("No such column. Try again")
      }

      print("Enter the values for Z axes: -> ")
      var zAxes = ""
      flag = false
      while (!flag) {
        zAxes = readLine()
        flag = df.names().contains(zAxes)
        if (!flag) println("No such column. Try again")
      }

      println("Enter the title of plot: -> ")
      val title = readLine()
      draw3DPlot(df, xAxes, yAxes, zAxes, title)

    } else {

      print("Enter the values for X axes: -> ")
      var xAxes = ""
      var flag = false
      while (!flag) {
        xAxes = readLine()
        flag = df.names().contains(xAxes)
        if (!flag) println("No such column. Try again")
      }

      print("Enter the values for Y axes: -> ")
      var yAxes = ""
      flag = false
      while (!flag) {
        yAxes = readLine()
        flag = df.names().contains(yAxes)
        if (!flag) println("No such column. Try again")
      }

      println("Enter the title of plot: -> ")
      val title = readLine()
      draw2DPlot(df, xAxes, yAxes, title)

    }
  }
}

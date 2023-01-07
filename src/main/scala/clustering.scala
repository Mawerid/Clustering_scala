import io.Input._
import algorithm.KMeans._
import smile.data.DataFrame
import smile.read
//import com.cibo.evilplot.plot.renderers.PointRenderer

//import scala.annotation.tailrec
//import scala.reflect.runtime.universe.Try
//import io.Visualisation._
//import io.Output._
//import scala.util.Random

//import io.displayPlot
//import com.cibo.evilplot.plot._
//import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
//import com.cibo.evilplot.numeric.Point

import scala.math._
import java.awt.Color._
import smile.plot._
import smile.plot.swing._
import smile.plot.Render._

object clustering {

  def main(args: Array[String]): Unit = {

    //Кол-во доступных ядер для распараллеливания
    val countProcessor = java.lang.Runtime.getRuntime.availableProcessors - 1

    println(countProcessor)

    //Чтение данных
    val path = "data/input/clusters_2_dim_2.csv"
    val data = readFullCSV(path)

    val clusterNum = 2
    val clusters = KMeans(data, clusterNum)

    println(clusters.size)
    clusters.foreach(it => println(it.size))

    val names: Seq[String] = (0 until clusterNum).map(i => s"Coordinate $i")
    val transData = data.map(_.toArray).toArray
    val df = DataFrame.of(transData, names: _*)
//    val tmp = DataFrame.

    println(df)

    val canvas = plot(df, "Coordinate 0", "Coordinate 1", "", '*')
    canvas.setAxisLabels("X", "Y")
    show(canvas)

    //    val heart = -314 to 314 map { i =>
    //      val t = i / 100.0
    //      val x = 16 * pow(sin(t), 3)
    //      val y = 13 * cos(t) - 5 * cos(2 * t) - 2 * cos(3 * t) - cos(4 * t)
    //      Array(x, y)
    //    }
    //    show(line(heart.toArray, color = RED))


    //    //Визуализация данных
    //    val scatter = drawScatterToByteArray(data
    //      , "X"
    //      , "Y"
    //      , "Clustering data (2 clusters, 2 dimensions)"
    //      , "Some data")
    //    val filename = "data/output/pic.png"
    //    saveScatterPlot(scatter, filename)
    //
    //    val scatterCluster =
  }

}

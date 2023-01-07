import io.Input._
import algorithm.KMeans._
import io.Visualisation._
import io.Output._

import java.util.concurrent.{ExecutorService, Executors}
import smile.data.DataFrame

import java.awt.Color._
import smile.plot._
import smile.plot.swing._
import smile.plot.Render._

object clustering {

  def main(args: Array[String]): Unit = {

    // Кол-во доступных ядер для распараллеливания
    val countThread = 4 //java.lang.Runtime.getRuntime.availableProcessors - 1

    // Чтение данных
    val path = "data/input/clusters_3_dim_3.csv"
    val data = readFullCSV(path)
    val clusterNum = 3

    // Запуск расчетов
    val pool = Executors.newFixedThreadPool(countThread)

    val clusters = KMeans(data, clusterNum, 0.00001, pool, countThread)

    println(clusters.size)
    clusters.foreach(it => println(it.size))

    pool.shutdown()

    // Визуализация данных
    val names: Seq[String] = data.head.indices.map(i => s"coordinate $i") :+ "class"
    val transData = data.map(_.toArray).toArray

    val tmp = data
      .map(point => (0 until clusterNum)
        .map(i => if (clusters(i)
          .contains(point)) i.toDouble + 1d))
      .map(it => it
        .find(_.toString != "()")
        .get)
      .map(_.toString.toDouble).toArray

    val df = DataFrame.of(transData.zip(tmp).map(it => it._1 :+ it._2), names: _*)
    println(df)

    val plotBefore = plot(df, "coordinate 0", "coordinate 1", "coordinate 2","class", '*')
    plotBefore.setAxisLabels("X", "Y", "Z")
    show(plotBefore)
  }

}

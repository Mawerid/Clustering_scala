import io.Input._
import io.Visualisation._
import io.Output._

import algorithm.KMeans._
import algorithm.SequentialJoins._

import java.util.concurrent.{ExecutorService, Executors}

object clustering {

  def main(args: Array[String]): Unit = {

    // Кол-во доступных ядер для распараллеливания
    val countThread: Int = java.lang.Runtime.getRuntime.availableProcessors / 2

    // Чтение данных
    val path = "data/input/clusters_3_dim_3.csv"
    val data = readFullCSV(path)
    val clusterNum = 3

    // Запуск расчетов
    val pool = Executors.newFixedThreadPool(countThread)

    val clusters = kMeans(data, clusterNum, 0.00001, pool, countThread)
//    val clusters = sequentialJoins(data, clusterNum, pool)

    pool.shutdown()

    // Визуализация данных
    println(clusters.size)
    clusters.foreach(it => println(it.size))

    val df = prepareToPlot(data, clusters)
    println(df)

    draw3DPlot(df)
  }

}

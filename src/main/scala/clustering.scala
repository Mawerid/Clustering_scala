import io.Input._
import io.Visualisation._
import io.Output._

import algorithm.KMeans._
import algorithm.SequentialJoins._

import java.util.concurrent.{Executors}

object clustering {

  def main(args: Array[String]): Unit = {

    // Кол-во доступных ядер для распараллеливания
    val countThread: Int = java.lang.Runtime.getRuntime.availableProcessors / 2

    // Чтение данных
    val path = "data/input/clusters_2_dim_2.csv"
    val data = readFullCSV(path)
    val clusterNum = 2

    // Запуск расчетов
    val pool = Executors.newFixedThreadPool(countThread)

//    val clusters = kMeans(data, clusterNum, 0.00001, pool, countThread)
    val clusters = sequentialJoins(data, clusterNum, pool)

    pool.shutdown()

    // Визуализация данных
    println(clusters.size)
    clusters.foreach(it => println(it.size))

    val df = prepareToPlot(data, clusters)
    println(df)


    data.head.size match {
      case 1 => println("We cannot draw plot in 1 dimension only")
      case 2 => draw2DPlot(df)
      case 3 => draw3DPlot(df)
      case _ => {

      }
    }

    // Предсказание значений


    // Сохранение данных
  }

}

import io.Input._
import io.Visualisation._
import io.Output._

import algorithm.KMeans._
import algorithm.SequentialJoins._
import algorithm.AffinityPropagation._
import scala.io.StdIn.readLine


import java.util.concurrent.Executors

object clustering {

  def main(args: Array[String]): Unit = {

    val tmp = intro()
    val method = tmp._2

    // Кол-во доступных ядер для распараллеливания
    val countThread: Int = java.lang.Runtime.getRuntime.availableProcessors / 2

    // Чтение данных
    val path = tmp._1
    val data = readFullCSV(path)

    // Запуск расчетов
    val pool = Executors.newFixedThreadPool(countThread)

    val clusters = method match {
      case "1" =>
        print("Please, enter the number of clusters: -> ")
        val clusterNum = readLine().toInt
        kMeans(data, clusterNum, 0.00001, pool, countThread)
      case "2" =>
        print("Please, enter the number of clusters: -> ")
        val clusterNum = readLine().toInt
        sequentialJoins(data, clusterNum, pool)
//      case "3" => affinityPropagation(data, pool)
    }

    pool.shutdown()

    // Визуализация данных
    summary(clusters)
    drawing(data, clusters)

    // Предсказание значений
//    makePredictions(clusters)

    // Сохранение данных
    saving(clusters)
  }

}

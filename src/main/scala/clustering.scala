import io.Input._
import algorithm.KMeans._
import io.Visualisation._
import io.Output._
import java.util.concurrent.{ExecutorService, Executors}

object clustering {

  def main(args: Array[String]): Unit = {

    //Кол-во доступных ядер для распараллеливания
    val countProcessor = 4//java.lang.Runtime.getRuntime.availableProcessors - 1

    println(countProcessor)

    //Чтение данных
    val path = "data/input/clusters_2_dim_2.csv"
    val data = readFullCSV(path)
    val pool = Executors.newFixedThreadPool(countProcessor)

    val clusters = KMeans(data, 2, 0.00001, pool, countProcessor)

    println(clusters.size)
    clusters.foreach(it => println(it.size))

    pool.shutdown()

    //Визуализация данных
//    val scatter = drawScatterToByteArray(data
//      , "X"
//      , "Y"
//      , "Clustering data (2 clusters, 2 dimensions)"
//      , "Some data")
//    val filename = "data/output/pic.png"
//    saveScatterPlot(scatter, filename)
  }

}

import io.Input._
import io.Visualisation._
import io.Output._

object clustering {

  def main(args: Array[String]): Unit = {

    //Кол-во доступных ядер для распараллеливания
    val countProcessor = java.lang.Runtime.getRuntime.availableProcessors - 1

    println(countProcessor)

    //Чтение данных
    val path = "data/input/clusters_2_dim_2.csv"
    val data = readFullCSV(path)

    //Визуализация данных
    val scatter = drawScatterToByteArray(data
      , "X"
      , "Y"
      , "Clustering data (2 clusters, 2 dimentions)"
      ,"Some data")
    val filename = "data/output/pic.png"
    saveScatterPlot(scatter, filename)


  }

}

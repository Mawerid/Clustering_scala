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

    val lst1 = List(2d, 3d, 4d)
    val lst2 = List(2d, 3d, 4d)
    val dist = lst1.zip(lst2).map(it => Math.pow(it._1 - it._2, 2)).sum

    //Визуализация данных
    val scatter = drawScatterToByteArray(data
      , "X"
      , "Y"
      , "Clustering data (2 clusters, 2 dimensions)"
      ,"Some data")
    val filename = "data/output/pic.png"
    saveScatterPlot(scatter, filename)


  }

}

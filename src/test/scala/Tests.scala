import io.Input._

import algorithm.KMeans._
import algorithm.SequentialJoins._
import algorithm.AffinityPropagation._

import java.util.concurrent.Executors

object Tests extends App {

  val path: List[String] = List("data/input/clusters_2_dim_2.csv", "data/input/clusters_2_dim_3.csv"
    , "data/input/clusters_3_dim_1.csv", "data/input/clusters_3_dim_3.csv")

  val data: List[List[List[Double]]] = path.map(readFullCSV)
  val countThread: Int = java.lang.Runtime.getRuntime.availableProcessors - 3
  val clustersNum: List[Int] = List(2, 2, 3, 3)

  data.zip(clustersNum).map(iter => {
    println()
    println("++++++++++++++++++++++++++++++++++++++++++")
    println(s"Work with [ ${data.indexOf(iter._1) + 1} ] testing file with size ${iter._1.size}")
    println("++++++++++++++++++++++++++++++++++++++++++")
    (1 to countThread).map(i => {
      val pool = Executors.newFixedThreadPool(i)
      print(s"Started calculations by $i threads. ")
      print("Method: K-Means. ")
      val time = (0 until 20).map(_ => {
        val start = System.nanoTime
        val clusters = kMeans(iter._1, iter._2, 0.00001, pool, i)
        System.nanoTime - start
      }).toList
      pool.shutdown()
      println(s"It takes ${time.sum / 1e9d / 20} seconds.")
//      println("++++++++++++++++++++++++++++++++++++++++++")
//      summary(clusters)
      println("==========================================")
    })
  })

  data.zip(clustersNum).map(iter => {
    println()
    println("++++++++++++++++++++++++++++++++++++++++++")
    println(s"Work with [ ${data.indexOf(iter._1) + 1} ] testing file with size ${iter._1.size}")
    println("++++++++++++++++++++++++++++++++++++++++++")
    (1 to countThread).map(i => {
      val pool = Executors.newFixedThreadPool(i)
      print(s"Started calculations by $i threads. ")
      print("Method: Sequential Joins. ")
      val time = (0 until  1).map(_ => {
        val start = System.nanoTime
        val clusters = sequentialJoins(iter._1, iter._2, pool)
        System.nanoTime - start
      }).toList
      pool.shutdown()
      println(s"It takes ${time.sum / 1e9d / 1} seconds.")
//      println("++++++++++++++++++++++++++++++++++++++++++")
//      summary(clusters)
      println("==========================================")
    })
  })

}

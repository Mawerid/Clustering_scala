package algorithm

import functions.Distance._
import functions.RandomCenters._
import functions.UpdateCenters._
import java.util.concurrent.{ExecutorService, Executors}
import scala.annotation.tailrec

object SequentialJoins {

  def findMin(distances: List[List[Double]]): (Int, Int) = {
    val min: Double = distances.map(_.filterNot(_ == 0.0).min).filterNot(_ == 0.0).min

    val tmp: List[Int] = distances.map(_.indexOf(min))
    val indI: Int = tmp.max
    val indJ: Int = tmp.indexOf(indI)

    if (indI > indJ) (indJ, indI)
    else (indI, indJ)
  }

  def sequentialJoins(data: List[List[Double]]
                      , clustersNum: Int
                      , pool: ExecutorService)
  : List[List[List[Double]]] = {

    val clustersStart: List[List[List[Double]]] = data.map(point => List(point))
    var distancesVar: List[List[Double]] = List[List[Double]]()

    def runnable(centers: List[List[Double]], currCenter: List[Double]) = new Runnable {
      override def run(): Unit = this.synchronized {
        distancesVar = centers.map(point => euclidean(point, currCenter)) :: distancesVar
      }
    }


    @tailrec
    def loop(clusters: List[List[List[Double]]]): List[List[List[Double]]] = {
      if (clusters.size == clustersNum) clusters
      else {
        distancesVar = List[List[Double]]()
        val centers: List[List[Double]] = clusters.map(updateCenter)

        println(clusters.size)


        clusters.indices.foreach(i => pool.execute(runnable(
          centers.slice(0, i) ::: centers.slice(i + 1, centers.length)
          , centers(i))
        ))

        Thread.sleep(100)

        val distances: List[List[Double]] = distancesVar

        println()
        println(distances.map(_.size).sum)

        val indClust = findMin(distances)

        val newClusters =
          (clusters(indClust._1) ::: clusters(indClust._2))::
          clusters.slice(0, indClust._1) :::
            clusters.slice(indClust._1 + 1, indClust._2) :::
            clusters.slice(indClust._2 + 1, clusters.size)

        loop(newClusters)
      }
    }

    loop(clustersStart)
  }
}

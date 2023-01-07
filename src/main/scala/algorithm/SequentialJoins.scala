package algorithm

import functions.Distance._
import functions.RandomCenters._
import functions.UpdateCenters._
import java.util.concurrent.{ExecutorService, Executors}
import scala.annotation.tailrec
object SequentialJoins {


  def sequentialJoins(data: List[List[Double]]
             , clustersNum: Int
             , eps: Double = 0.00001
             , pool: ExecutorService
             , threadsNum: Int = 1)
  : List[List[List[Double]]] = {

    var clustersStart: List[List[List[Double]]] = data.map(point => List(point))
    var distancesVar: List[List[Double]] = List[List[Double]]()

    def runnable(centers: List[List[Double]], currCenter: List[Double]) = new Runnable {
      override def run(): Unit = {
        distancesVar = centers.map(point => euclidean(point, currCenter)) :: distancesVar
      }
    }



    @tailrec
    def loop(clusters: List[List[List[Double]]]): List[List[List[Double]]] = {
      if (clusters.size == clustersNum) clusters
      else {
        val centers: List[List[Double]] = clusters.map(updateCenter)

        clusters.indices.foreach(i => pool.execute(runnable(
          centers.slice(0, i) ::: centers.slice(i + 1, centers.length)
          , centers(i))
        ))

        val distances: List[List[Double]] = distancesVar

        //val minimumVal: Double = distances.map(point1 => )
        List(List(List(0D)))
      }
    }

  }
}

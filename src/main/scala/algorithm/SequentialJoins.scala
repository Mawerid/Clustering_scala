package algorithm

import functions.Distance._
import functions.RandomCenters._
import java.util.concurrent.{ExecutorService, Executors}
import scala.annotation.tailrec
object SequentialJoins {

  def findMin(distances: List[List[Double]]): (Int, Int) = {
    val min: Double = distances.map(_.min).min

    val indI: Int = distances.map(_.indexOf(min)).max
    val indJ: Int = distances(indI).indexOf(min)

    (indI, indJ)
  }


  def sequentialJoins(data: List[List[Double]]
             , clustersNum: Int
             , eps: Double = 0.00001
             , pool: ExecutorService
             , threadsNum: Int = 1)
  : List[List[List[Double]]] = {



  }
}

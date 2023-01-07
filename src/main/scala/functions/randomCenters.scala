package functions

import scala.annotation.tailrec
import scala.util.Random

object randomCenters extends App{ // for get first points - random

  val threadNum = 2 // number of threads

  val cluster = List(List(1D, 2D, 1D), List(2D, 6D, 1D), List(3D, 7D, 1D)) // example cluster
  val numOfClusters = 2

  val lenJ = cluster.head.length // size of dimension
  val lenI = cluster.length // number of vectors

  @tailrec
  def randomChoice(
                    from: List[List[Double]],
                    acc: List[List[Double]] = List[List[Double]](),
                    i: Int = 0
                  ):
  List[List[Double]] =
  {
    if (i >= numOfClusters) acc
    else {
      val choice = Random.shuffle(from)
      randomChoice(choice.tail, acc :+ choice.head, i + 1)
    }
  }

  val centers = randomChoice(cluster) // result
  centers.foreach(println)
}

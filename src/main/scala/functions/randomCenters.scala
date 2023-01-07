package functions

import scala.annotation.tailrec
import scala.util.Random

object randomCenters { // for get first points - random

  val cluster: List[List[Double]] = List(List(1D, 2D, 1D), List(2D, 6D, 1D), List(3D, 7D, 1D)) // example cluster
  val numOfClusters = 2

  @tailrec
  def randomChoice(
                    from: List[List[Double]],
                    numOfClusters: Int,
                    acc: List[List[Double]] = List[List[Double]](),
                    i: Int = 0
                  ):
  List[List[Double]] =
  {
    if (i >= numOfClusters) acc
    else {
      val choice = Random.shuffle(from)
      randomChoice(choice.tail, numOfClusters, acc :+ choice.head, i + 1)
    }
  }

  val centers: List[List[Double]] = randomChoice(cluster, numOfClusters) // result
  centers.foreach(println)
}

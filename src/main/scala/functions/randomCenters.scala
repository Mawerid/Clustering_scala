package functions

import scala.annotation.tailrec
import scala.util.Random

object randomCenters extends App{ // for get first points - random

  val threadNum = 2 // number of threads

  val cluster: Array[Array[Double]] = Array(Array(1, 2, 1), Array(2, 6, 1), Array(3, 7, 1)) // example cluster
  val numOfClusters = 2

  val lenJ = cluster(0).length // size of dimension
  val lenI = cluster.length // number of vectors

  @tailrec
  def randomChoice(from: Array[Array[Double]], acc: Array[Array[Double]] = Array[Array[Double]](), i: Int = 0): Array[Array[Double]] = {
    if (i >= numOfClusters) acc
    else {
      val choice = Random.shuffle(from.toList)
      randomChoice(choice.tail.toArray, acc :+ choice.head, i + 1)
    }
  }

  val centers: Array[Array[Double]] = randomChoice(cluster) // result
  printMatrix.printM(centers)
}

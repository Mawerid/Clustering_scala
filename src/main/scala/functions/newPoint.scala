package functions

import scala.annotation.tailrec

object newPoint extends App{

  val threadNum = 2 // number of threads
  val cluster: Array[Array[Double]] = Array(Array(1, 2, 0), Array(2, 6, 0), Array(3, 7, 0)) // example cluster
  var center: Array[Double] = Array[Double]() // result
  val lenJ = cluster(0).length // size of dimension
  val lenI = cluster.length // number of vectors

  def sumAxis0: (Array[Array[Double]], Int, Int) => Double = (cluster, idThread, dim) => {
    @tailrec
    def sum(acc: Double = 0, i: Int = idThread): Double = {
      if (i >= lenI) acc
      else sum(acc + cluster(i)(dim), i + threadNum)
    }
    sum()
  }

  (0 until lenJ).foreach(dimNum => {
    var res: Double = 0 // part of result point
    (0 until threadNum).foreach(id => new Thread(() => this.synchronized {
      res += sumAxis0(cluster, id, dimNum)
    }).start())

    Thread.sleep(100) // for synchronize
    center = center :+ res / lenI // add coordinate of point
  })

  println(center.mkString("Array(", ", ", ")"))

}

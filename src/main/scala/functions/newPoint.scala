package functions

import scala.annotation.tailrec

object newPoint extends App{

  val threadNum = 2 // number of threads
  val cluster: List[List[Double]] = List(List(1D, 2, 0), List(2D, 6, 0), List(3D, 7, 0)) // example cluster
  var center: List[Double] = List[Double]() // result
  val lenJ = cluster.head.length // size of dimension
  val lenI = cluster.length // number of vectors

  def sumAxis0: (List[List[Double]], Int, Int) => Double = (cluster, idThread, dim) => {
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

  println(center)

}

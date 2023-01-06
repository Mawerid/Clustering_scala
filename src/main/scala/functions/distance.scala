package functions

import java.util.concurrent.Executors
import scala.annotation.tailrec

object distance extends App{

  val threadNum = 2 // number of threads
  val p1: Array[Double] = Array(1, 1, 1, 1, 1) // example point
  val p2: Array[Double] = Array(4, 5, 3, 3, 5) // example point

  // calculate sum of Square of difference between two points
  def diff_sum: (Array[Double], Array[Double], Int) => Double = (p1, p2, idThread) => {
    val len = p1.length // const - len
    @tailrec
    def loop(acc: Double = 0, i: Int = idThread): Double = { // main tailrec
      if (i >= len) acc
      else loop(acc + Math.pow(p1(i) - p2(i), 2), i + threadNum)
    }
    loop()
  }

  var res: Double = 0 // var for res

  (0 until threadNum).foreach(id => new Thread(() => this.synchronized { // work of many threads
    res += diff_sum(p1, p2, id)
  }).start())

  Thread.sleep(100) // for synchronize
  val distance = Math.pow(res, 0.5) // result sqrt
  println(distance)

}

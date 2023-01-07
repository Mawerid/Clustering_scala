import functions.Distance._
import functions.RandomCenters._

import scala.util.Random
import java.util.concurrent.Executors.newFixedThreadPool

object testingCode extends App{

  val centers = List(List(1D, 2, 0), List(2D, 6, 0))
  val clusters: List[List[List[Double]]] = centers.indices.map(_ => List()).toList // example cluster
  var clusters1: List[List[List[Double]]] = centers.indices.map(_ => List()).toList // example cluster
  var center: List[Double] = List[Double]() // result
  val data = List(List(1D, 2, 0), List(2D, 6, 1), List(3D, 5D, 3D))

//  def runnable(num: Int) = new Runnable {
//    override def run(): Unit = println(s"I run in parallel ${Thread.currentThread.getName}")
//  }
//
//  val countProcessor = java.lang.Runtime.getRuntime.availableProcessors - 1
//  val pool = newFixedThreadPool(countProcessor)
//
//  (0 until countProcessor).foreach(_ => pool.execute(runnable(5)))
//  pool.shutdown()
//  Thread.sleep(100)
//  println(pool.isShutdown)

}

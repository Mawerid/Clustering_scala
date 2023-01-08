import functions.Distance._
import functions.RandomCenters._
import algorithm.KMeans._
import algorithm.SequentialJoins._
import io.Visualisation._

import java.util.concurrent.{ExecutorService, Executors}
import scala.util.Random
import java.util.concurrent.Executors.newFixedThreadPool

object testingCode extends App{

  val centers = List(List(1D, 2, 0), List(2D, 6, 0))
  //val clusters: List[List[List[Double]]] = centers.indices.map(_ => List()).toList // example cluster
  var clusters1: List[List[List[Double]]] = centers.indices.map(_ => List()).toList // example cluster
  var center: List[Double] = List[Double]() // result
  val data = List(List(0D, 0), List(0D, 1), List(1D, 1), List(1D, 2)
    , List(1D, 0), List(2D, 1), List(3D, 4), List(4D, 3), List(4D, 4D)
    , List(4D, 5), List(5D, 4), List(5D, 5), List(1D, 4), List(1.5, 1.5)
    , List(1.5, 4D), List(2, 4D), List(2.5, 4D), List(2, 4.05), List(1D, 3.5)
    , List(1D, 3), List(1.0, 2.5), List(0.9, 2.5))

  val countThread = 4
  val clusterNum = 2

  val pool = Executors.newFixedThreadPool(countThread)

  val clusters = kMeans(data, clusterNum, 0.00001, pool, countThread)
//  val clusters = sequentialJoins(data, clusterNum, pool)

  pool.shutdown()

  // Визуализация данных
  println(clusters.size)
  clusters.foreach(it => println(it.size))

  draw2DPlot(prepareToPlot(data, clusters))

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

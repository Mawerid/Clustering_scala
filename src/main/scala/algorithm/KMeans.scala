package algorithm

import functions.Distance._
import functions.randomCenters._

import java.util.concurrent.{ExecutorService, Executors}
import scala.annotation.tailrec

import scala.annotation.tailrec
import java.net.{Socket, ServerSocket}
import java.util.concurrent.{Executors, ExecutorService}
import java.util.Date


object KMeans {
//  def newCenter(cluster: List[List[Double]], idThread: Int): List[Double] = {
//    @tailrec
//    def loop()
//  }

  def meanCoor(cluster: List[List[Double]], dimention: Int): Double =
    cluster.map(point => point(dimention)).sum / cluster.size

  def updateCenter(cluster: List[List[Double]]): List[Double] =
    cluster.head.indices.map(i => meanCoor(cluster, i)).toList

  def fillClusters(data: List[List[Double]], centers: List[List[Double]])
                  (implicit distance: (List[Double], List[Double]) => Double = euclidean)
  : List[List[List[Double]]] = {
    var clusters: List[List[List[Double]]] = centers.indices.map(_ => List()).toList

    data.foreach(point => {
      val distances = centers
        .indices
        .map(i => distance(point, centers(i)))
        .toList

      val nearestCenter = distances
        .indexOf(distances.min)
      // ====+++++++=====
      clusters =
      clusters
        .slice(0, nearestCenter) ::: clusters(nearestCenter)
        .::(point) :: clusters
        .slice(nearestCenter + 1, clusters.length)

    })
    clusters
  }

  def KMeans(data: List[List[Double]]
             , clustersNum: Int
             , eps: Double = 0.00001)
  : List[List[List[Double]]] = {

    val centersStart = randomChoice(data, clustersNum)

    @tailrec
    def loop(centersCurr: List[List[Double]]
             , currEps: Double)
    : List[List[Double]] = {
      if (currEps - eps < 0) centersCurr
      else {
        val clusters = fillClusters(data, centersCurr)
        val centersNew = clusters.map(updateCenter)

        loop(centersNew
          , centersCurr.zip(centersNew)
            .map(it => euclidean(it._1, it._2))
            .sum)
      }
    }

    fillClusters(data, loop(centersStart, eps + 1d))
  }

}

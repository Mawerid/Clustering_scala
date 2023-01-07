package algorithm

import functions.Distance._
import functions.randomCenters._
import java.util.concurrent.{ExecutorService, Executors}
import scala.annotation.tailrec

object KMeans {

  /**
   * Find mean value of dimension in cluster
   *
   * @param cluster cluster data
   * @param dimension dimension to find mean
   * @return mean value
   */
  def meanCoor(cluster: List[List[Double]], dimension: Int): Double =
    cluster.map(point => point(dimension)).sum / cluster.size

  /**
   * Find new center of cluster
   *
   * @param cluster cluster of points
   * @return new center(mean)
   */
  def updateCenter(cluster: List[List[Double]]): List[Double] =
    cluster.head.indices.map(i => meanCoor(cluster, i)).toList

  /**
   * Filling each cluster
   *
   * @param data data to fill in clusters
   * @param centers centers of each cluster
   * @param distance function of distance between two points
   * @return list with each cluster filled with points
   */
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


      clusters =
      clusters
        .slice(0, nearestCenter) ::: clusters(nearestCenter)
        .::(point) :: clusters
        .slice(nearestCenter + 1, clusters.length)

    })
    clusters
  }

  /**
   * Function of clustering by K-Means method
   *
   * @param data data to cluster
   * @param clustersNum number of clusters to fill
   * @param eps accuracy of clustering
   * @return filled list with each cluster
   */
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

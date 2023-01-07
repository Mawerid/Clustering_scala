package algorithm

import functions.Distance._
import functions.RandomCenters._
import functions.UpdateCenters._
import java.util.concurrent.ExecutorService
import scala.annotation.tailrec

object KMeans {

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
   * @param pool pool with using threads
   * @param threadsNum number of using threads
   * @return filled list with each cluster
   */
  def kMeans(data: List[List[Double]]
             , clustersNum: Int
             , eps: Double = 0.00001
             , pool: ExecutorService
             , threadsNum: Int  = 1)
  : List[List[List[Double]]] = {

    val centersStart = randomChoice(data, clustersNum)

    //val pool = Executors.newFixedThreadPool(threadsNum)
    var clustersVar: List[List[List[Double]]] = centersStart.indices.map(_ => List()).toList
    val r = data.length % threadsNum
    val n = data.length / threadsNum

    def runnable(centersCurr: List[List[Double]], startIndex: Int, endIndex: Int) = new Runnable {
      override def run(): Unit = this.synchronized {
        val partClusters = fillClusters(data.slice(startIndex, endIndex), centersCurr)
        clustersVar.indices.foreach(clustNum => clustersVar =
          clustersVar
            .slice(0, clustNum) ::: (clustersVar(clustNum) ::: partClusters(clustNum)) :: clustersVar
            .slice(clustNum + 1, clustersVar.length))
      }
    }

    @tailrec
    def loop(centersCurr: List[List[Double]]
             , currEps: Double)
    : List[List[Double]] = {
      if (currEps - eps < 0) centersCurr
      else {
        clustersVar = centersCurr.indices.map(_ => List()).toList

        (0 until threadsNum).foreach(id => {
          pool.execute(runnable(centersCurr, id * n, (id + 1) * n))
        })
        runnable(centersCurr, threadsNum * n, r + threadsNum * n).run()

        Thread.sleep(100)

        val clusters = clustersVar //fillClusters(data, centersCurr)
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

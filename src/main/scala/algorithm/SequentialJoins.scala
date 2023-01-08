package algorithm

import functions.Distance._
import functions.UpdateCenters._

import java.util.concurrent.ExecutorService
import scala.annotation.tailrec
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}

object SequentialJoins {

  /**
   * find indexes of clusters with minimum distance between them
   *
   * @param distances list of pairwise distances for each cluster
   * @return tuple with two indexes
   */
  def findMin(distances: List[List[Double]]): (Int, Int) = {
    val min: Double = distances.map(_.filterNot(_ == 0.0).min).filterNot(_ == 0.0).min

    val tmp: List[Int] = distances.map(_.indexOf(min)) // список с индексами минимальных элементов
    // (каждый индекс соответствует списку)
    val indI: Int = tmp.filter(_ != -1).min
    val indJ: Int = tmp.indexOf(indI)

    // возвращаем в порядке возрастания
    if (indI > indJ) (indJ, indI)
    else (indI, indJ)
  }

  /**
   * Function of clustering by Sequential Joins method
   *
   * @param data        data to cluster
   * @param clustersNum number of clusters to fill
   * @param pool        pool with using threads
   * @return filled list with each cluster
   */
  def sequentialJoins(data: List[List[Double]]
                      , clustersNum: Int
                      , pool: ExecutorService)
  : List[List[List[Double]]] = {

    val clustersStart: List[List[List[Double]]] = data.map(point => List(point)) // начальные кластеры (точка - кластер)
    var distancesVar: List[List[Double]] = List[List[Double]]() // список расстояний - будет изменяться потоками
    implicit val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(pool)

    def task(centers: List[List[Double]], currCenter: List[Double]): Future[Unit] = {
      Future {
        distancesVar =
          centers.map(point => euclidean(point, currCenter)) :: distancesVar // добавление списка
        // расстояний соответствующим потоком
      }
    }


    @tailrec
    def loop(clusters: List[List[List[Double]]]): List[List[List[Double]]] = {
      if (clusters.size == clustersNum) clusters
      else {
        distancesVar = List[List[Double]]() // обнуление списка расстояний
        val centers: List[List[Double]] = clusters.map(updateCenter) // списки центров (по ним считаются расстояния)

        // параллельное вычисление расстояний - каждый кластер отдается потокам
        centers.indices.foreach(i => {
          val aggregated: Future[Unit] = task(centers, centers(i))
          Await.result(aggregated, scala.concurrent.duration.Duration.Inf)
        })

        val distances: List[List[Double]] = distancesVar.sortBy(lst => lst.indexOf(0D)) // список попарных расстояний

        val indClust = findMin(distances) // индексы ближайших кластеров

        // соединение очередных кластеров
        val newClusters =
          (clusters(indClust._1) ::: clusters(indClust._2)) ::
            clusters.slice(0, indClust._1) :::
            clusters.slice(indClust._1 + 1, indClust._2) :::
            clusters.slice(indClust._2 + 1, clusters.size)

        loop(newClusters) // рекурсия
      }
    }

    loop(clustersStart) // результат
  }

  /**
   * Prediction of clustering
   *
   * @param centers centers of clusters
   * @param point   point to predict
   * @return number of predicted cluster
   */
  def predictSeqJoinsCenters(centers: List[List[Double]], point: List[Double]): Int = {
    val distances = centers.map(center => euclidean(center, point))
    distances.indexOf(distances.min)
  }

  /**
   * Prediction of clustering
   *
   * @param clusters clusters
   * @param point    point to predict
   * @return number of predicted cluster
   */
  def predictSeqJoinsClusters(clusters: List[List[List[Double]]], point: List[Double]): Int = {
    val centers = clusters.map(updateCenter)
    val distances = centers.map(center => euclidean(center, point))
    distances.indexOf(distances.min)
  }
}

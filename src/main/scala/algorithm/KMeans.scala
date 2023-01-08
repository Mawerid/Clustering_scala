package algorithm

import functions.Distance._
import functions.RandomCenters._
import functions.UpdateCenters._

import java.util.concurrent.ExecutorService

import scala.annotation.tailrec
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}

object KMeans {

  /**
   * Filling each cluster
   *
   * @param data     data to fill in clusters
   * @param centers  centers of each cluster
   * @param distance function of distance between two points
   * @return list with each cluster filled with points
   */
  def fillClusters(data: List[List[Double]], centers: List[List[Double]])
                  (implicit distance: (List[Double], List[Double]) => Double = euclidean)
  : List[List[List[Double]]] = {

    var clusters: List[List[List[Double]]] = centers.indices.map(_ => List()).toList // инициализация результата


    data.foreach(point => {
      val distances = centers  // вычисление расстояний для данной точки
        .indices
        .map(i => distance(point, centers(i)))
        .toList

      val nearestCenter = distances // индекс ближайшего кластера
        .indexOf(distances.min)

      clusters =  // добавление точки в ближайший кластер
        clusters
          .slice(0, nearestCenter) ::: clusters(nearestCenter)
          .::(point) :: clusters
          .slice(nearestCenter + 1, clusters.length)

    })

    clusters // результат
  }

  /**
   * Function of clustering by K-Means method
   *
   * @param data        data to cluster
   * @param clustersNum number of clusters to fill
   * @param eps         accuracy of clustering
   * @param pool        pool with using threads
   * @param threadsNum  number of using threads
   * @return filled list with each cluster
   */
  def kMeans(data: List[List[Double]]
             , clustersNum: Int
             , eps: Double = 0.00001
             , pool: ExecutorService
             , threadsNum: Int = 1)
  : List[List[List[Double]]] = {

    val centersStart = randomChoice(data, clustersNum) // начальные центры - случайные точки
    var clustersVar: List[List[List[Double]]] = centersStart.indices.map(_ => List()).toList // инициализация кластеров

    val r = data.length % threadsNum // остаток от честного деления работы между потоками
    val n = data.length / threadsNum // сколько потоку делать)
    implicit val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(pool)

    def task(centersCurr: List[List[Double]], startIndex: Int, endIndex: Int): Future[Unit] = {
      Future {
        val partClusters = fillClusters(data.slice(startIndex, endIndex), centersCurr) // кусок кластера, посчитанный потоком
        // присоединение кусочка к общему кластеру
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
      if (currEps - eps < 0) centersCurr // пока центры не перестанут двигаться
      else {
        clustersVar = centersCurr.indices.map(_ => List()).toList // обнуляем текущие кластеры

        // раздаем потокам задачи (кластеризовать часть данных)
        (0 until threadsNum).foreach(id => {
          val aggregated: Future[Unit] = task(centersCurr, id * n, (id + 1) * n)
          Await.result(aggregated, scala.concurrent.duration.Duration.Inf)
        })

        // отдаем в очередь оставшуюся часть
        val aggregated: Future[Unit] = task(centersCurr, threadsNum * n, r + threadsNum * n)
        Await.result(aggregated, scala.concurrent.duration.Duration.Inf)

        val clusters = clustersVar // итоговый кластер
        val centersNew = clusters.map(updateCenter) // новые центры

        // продолжаем вычисления
        loop(centersNew
          , centersCurr.zip(centersNew)
            .map(it => euclidean(it._1, it._2))
            .sum)
      }
    }

    fillClusters(data, loop(centersStart, eps + 1d)) // итоговые кластеры
  }
}

package algorithm

import functions.Distance._

import java.util.concurrent.ExecutorService

import scala.annotation.tailrec
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}

object AffinityPropagation {

  def similarityMatrix(data: List[List[Double]]): List[List[Double]] = {
    val matrix = data.map(pointI => data.map(pointJ => (-1) * euclideanSqr(pointI, pointJ)))
    val min = matrix.map(_.min).min
    matrix.map(point => point.map(crd => if (crd == 0.0) min else crd))
  }

  def responsibilityMatrix(simMatrix: List[List[Double]]): List[List[Double]] = {
    val maximums = simMatrix.map(it => it.map(curr => it.filterNot(_ == curr).max))
    simMatrix.zip(maximums).map(it => it._1.zip(it._2).map(tmp => tmp._1 - tmp._2))
  }

  def sumPositive(matr: List[List[Double]], dimension: Int): Double = {
    val positives = matr.map(point => point(dimension)).map(i => if (i > 0.0) i else 0.0)
    positives.indices.map(i => if (i != dimension) positives(i) else 0.0).sum
  }

  def availabilityMatrix(respMatrix: List[List[Double]]): List[List[Double]] = {
    val matrix = respMatrix.indices
      .map(i => sumPositive(respMatrix, i))
      .toList
      .map(value => respMatrix
        .indices.map(_ => value).toList)

    matrix.indices.map(i => matrix.indices.map(j => {
      if (i == j) matrix(i)(j)
      else {
        val diff = {
          if (respMatrix(i)(j) > 0.0)  matrix(j)(i) - respMatrix(i)(j)
          else matrix(j)(i)
        }
        val tmp = diff + respMatrix(j)(j)
        if (tmp > 0.0) 0.0
        else tmp
      }
    }).toList).toList
  }

  def criterionMatrix(respMatrix: List[List[Double]]
                      , availMatrix: List[List[Double]]
                      , pool: ExecutorService
                      , threadsNum: Int = 1)
  : List[List[Double]] = {

    val R = respMatrix.length % threadsNum // остаток от честного деления работы между потоками
    val N = respMatrix.length / threadsNum // сколько потоку делать)
    implicit val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(pool)
    var res = respMatrix
      .indices
      .map(_ => respMatrix
        .indices.map(_ => 0.0)
        .toList)
      .toList

    def sum(startIndex: Int
            , endIndex: Int
            , matr1: List[List[Double]]
            , matr2: List[List[Double]])
    : Future[Unit] = Future {
          res = res.slice(0, startIndex):::
            res
              .slice(startIndex, endIndex)
              .indices
              .map(i => matr1(i).zip(matr2(i))
                .map(it => it._1 + it._2))
              .toList :::
            res.slice(endIndex, res.length)
    }

    // раздаем потокам задачи (кластеризовать часть данных)
    (0 until threadsNum).foreach(id => {
      val aggregated: Future[Unit] = sum(id * N, (id + 1) * N, respMatrix, availMatrix)
      Await.result(aggregated, scala.concurrent.duration.Duration.Inf)
    })

    // отдаем в очередь оставшуюся часть
    val aggregated: Future[Unit] = sum(threadsNum * N, R + threadsNum * N, respMatrix, availMatrix)
    Await.result(aggregated, scala.concurrent.duration.Duration.Inf)

    res
    //sumElements(respMatrix, availMatrix)
  }

  def sumElements(first: List[List[Double]], second: List[List[Double]])
  : List[List[Double]] = {
    first.zip(second).map(it =>
      it._1.zip(it._2).map(values => values._1 + values._2))
  }

  def clusteringData(data: List[List[Double]], critMatrix: List[List[Double]])
  : List[List[List[Double]]] = {
    val maximums = critMatrix.map(_.max)
    val clustersVal = maximums.toSet.toList

    var clusters: List[List[List[Double]]] = clustersVal
      .indices
      .map(_ => List(List[Double]())).toList

    data.zip(maximums).foreach(it => {
      val ind = clustersVal.indexOf(it._2)
      clusters = clusters.slice(0, ind) ::: (it._1 :: clusters(ind)) :: clusters.slice(ind + 1, clusters.length)
    })
    clusters
  }

  def affinityPropagation(data: List[List[Double]]
                          , pool: ExecutorService
                          , threadsNum: Int = 1
                          , maxIter: Int = 1)
  : List[List[List[Double]]] = {

    val simMatrix = similarityMatrix(data)
    val nullMatrix: List[List[Double]] = simMatrix
      .indices
      .map(_ => simMatrix
        .indices.map(_ => 0.0)
        .toList)
      .toList

    @tailrec
    def loop(matrices: (List[List[Double]], List[List[Double]]), iter: Int = 0)
    : (List[List[Double]], List[List[Double]]) = {
      if (iter >= maxIter) matrices
      else {
        val respMatrix = responsibilityMatrix(sumElements(matrices._2, simMatrix))
        val availMatrix = availabilityMatrix(respMatrix)

        loop((respMatrix, availMatrix), iter + 1)
      }
    }

    val matrices = loop((nullMatrix, nullMatrix))
    clusteringData(data, criterionMatrix(matrices._1, matrices._2, pool, threadsNum))
  }

}

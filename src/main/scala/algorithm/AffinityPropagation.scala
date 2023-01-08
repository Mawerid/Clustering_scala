package algorithm

import functions.Distance._

import scala.annotation.tailrec

object AffinityPropagation {

  def similarityMatrix(data: List[List[Double]]): List[List[Int]] = {
    val matrix = data.map(pointI => data.map(pointJ => (-1) * euclideanSqr(pointI, pointJ)))
    val min = matrix.map(_.min).min
    matrix.map(point => point.map(crd => if (crd == 0) min.toInt else crd.toInt))
  }

  def responsibilityMatrix(simMatrix: List[List[Int]]): List[List[Int]] = {
    val maximums = simMatrix.map(it => it.map(curr => it.filterNot(_ == curr).max))
    simMatrix.zip(maximums).map(it => it._1.zip(it._2).map(tmp => tmp._1 - tmp._2))
  }

  def sumPositive(matr: List[List[Int]], dimension: Int): Int = {
    val positives = matr.map(point => point(dimension)).filter(_ > 0)
    positives.indices.map(i => if(i != dimension) positives(i) else 0).sum
  }

  def availabilityMatrix(respMatrix: List[List[Int]]): List[List[Int]] = {
    val matrix = respMatrix.indices
      .map(i => sumPositive(respMatrix, i))
      .toList
      .map(value => respMatrix
        .indices.map(_ => value).toList)

    matrix.indices.map(i => matrix.indices.map(j => {
      if (i == j) matrix(i)(j)
      else {
        val tmp = matrix(i)(j) + respMatrix(j)(j)
        if (tmp > 0) 0
        else tmp
      }
    }).toList).toList
  }

  def criterionMatrix(respMatrix: List[List[Int]], availMatrix: List[List[Int]])
  : List[List[Int]] =
    respMatrix.zip(availMatrix).map(it =>
        it._1.zip(it._2).map(values => values._1 + values._2))

  def sumElements(first: List[List[Int]], second: List[List[Int]])
  : List[List[Int]] = {
    first.zip(second).map(it =>
      it._1.zip(it._2).map(values => values._1 + values._2))
  }

  def clusteringData(data: List[List[Double]], critMatrix: List[List[Int]])
  : List[List[List[Double]]] = {
    val maximums = critMatrix.map(_.max)
    val clustersVal = maximums.toSet.toList

    println(clustersVal)
    println(clustersVal.size)

    val clusters: List[List[List[Double]]] = clustersVal
      .indices
      .map(_ => List(List[Double]())).toList

    println(clusters.size)

    data.zip(maximums).map(it => it._1 :: clusters(clustersVal.indexOf(it._2)))
  }

  def affinityPropagation(data: List[List[Double]], maxIter: Int = 10): List[List[List[Double]]] = {
    val simMatrix = similarityMatrix(data)
    val nullMatrix: List[List[Int]] = simMatrix
      .indices
      .map(_ => simMatrix
        .indices.map(_ => 0)
        .toList)
      .toList

    @tailrec
    def loop(matrices: (List[List[Int]], List[List[Int]]), iter: Int = 0)
    : (List[List[Int]], List[List[Int]]) = {
      if (iter >= maxIter) matrices
      else {
        val respMatrix = responsibilityMatrix(sumElements(matrices._2, simMatrix))
        val availMatrix = availabilityMatrix(respMatrix)
        println(iter)
        loop((respMatrix, availMatrix), iter + 1)
      }
    }

    val matrices = loop((nullMatrix, nullMatrix))
    clusteringData(data, criterionMatrix(matrices._1, matrices._2))
  }

}

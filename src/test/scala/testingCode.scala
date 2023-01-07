import functions.Distance._
import functions.randomCenters._

import scala.util.Random

object testingCode extends App{

  val centers = List(List(1D, 2, 0), List(2D, 6, 0))
  val clusters: List[List[List[Double]]] = centers.indices.map(_ => List()).toList // example cluster
  var clusters1: List[List[List[Double]]] = centers.indices.map(_ => List()).toList // example cluster
  var center: List[Double] = List[Double]() // result
  val data = List(List(1D, 2, 0), List(2D, 6, 0), List(3D, 5D, 3D))

  println(data)
  println(centers)
  println(clusters)

  println(data.map(point => {
    val distances = centers
      .indices
      .map(i => euclidean(point, centers(i)))
      .toList

    val nearestCenter = distances
      .indexOf(distances.min)
    // ====+++++++=====
    println(clusters1)
    clusters1 =
      clusters1.slice(0, nearestCenter) ::: clusters1(nearestCenter).::(point) :: clusters1.slice(nearestCenter + 1, clusters1.length)
    println(clusters1)
    println()
    point :: clusters(nearestCenter)
  }))
}

package functions

import functions.Distance._
import functions.UpdateCenters._

object Predict {

  /**
   * Prediction of clustering
   *
   * @param centers centers of clusters
   * @param point   point to predict
   * @return number of predicted cluster
   */
  def predictCenters(centers: List[List[Double]], point: List[Double]): Int = {
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
  def predictClusters(clusters: List[List[List[Double]]], point: List[Double]): Int = {
    val centers = clusters.map(updateCenter)
    val distances = centers.map(center => euclidean(center, point))
    distances.indexOf(distances.min)
  }

}

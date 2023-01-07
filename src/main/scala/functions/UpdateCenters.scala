package functions

object UpdateCenters {
  /**
   * Find mean value of dimension in cluster
   *
   * @param cluster   cluster data
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
}

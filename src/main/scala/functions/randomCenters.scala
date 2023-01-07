package functions

import scala.util.Random

object randomCenters {

  /**
   * Generate first centers of clusters
   *
   * @param data       data for clustering
   * @param clusterNum number of clusters
   * @return List with points - random centers of clusters
   */
  def randomChoice(data: List[List[Double]], clusterNum: Int): List[List[Double]] =
    Random.shuffle(data).slice(0, clusterNum)

}

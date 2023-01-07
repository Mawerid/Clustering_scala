package functions

import scala.util.Random

object randomCenters {

  /**
   *
   * @param data
   * @param clusterNum
   * @return
   */
  def randomChoice(data: List[List[Double]], clusterNum: Int): List[List[Double]] =
    Random.shuffle(data).slice(0, clusterNum)

}

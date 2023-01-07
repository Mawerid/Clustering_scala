package functions

object Distance {

  /**
   * square of euclidean distance
   *
   * @param p1 first point
   * @param p2 second point
   * @return distance between them
   */
  def euclideanSqr(p1: List[Double], p2: List[Double]): Double =
    p1.zip(p2).map(it => Math.pow(it._1 - it._2, 2)).sum

  /**
   * euclidean distance
   *
   * @param p1 first point
   * @param p2 second point
   * @return distance between them
   */
  def euclidean(p1: List[Double], p2: List[Double]): Double =
    Math.pow(euclideanSqr(p1, p2), 0.5)

  /**
   * manhattan distance
   *
   * @param p1 first point
   * @param p2 second point
   * @return distance between them
   */
  def manhattan(p1: List[Double], p2: List[Double]): Double =
    p1.zip(p2).map(it => Math.abs(it._1 - it._2)).sum

  /**
   * chebyshev distance
   *
   * @param p1 first point
   * @param p2 second point
   * @return distance between them
   */
  def chebyshev(p1: List[Double], p2: List[Double]): Double =
    p1.zip(p2).map(it => Math.abs(it._1 - it._2)).max


  /**
   * powering distance
   *
   * @param p1 first point
   * @param p2 second point
   * @param r degree of radical
   * @param p degree of powering
   * @return distance between them
   */
  def powering(p1: List[Double], p2: List[Double], r: Double, p: Double): Double =
    Math.pow(
      p1.zip(p2).map(it => Math.pow(it._1 - it._2, p)).sum,
      r)

}

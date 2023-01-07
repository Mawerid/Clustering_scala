import functions.distance._

object testingCode extends App{

  val p1: List[Double] = List(1D, 1D, 1D, 1D, 1D) // example point
  val p2: List[Double] = List(4D, 5D, 3D, 3D, 5D) // example point

  println(euclidean(p1, p2))
  println(manhattan(p1, p2))

}

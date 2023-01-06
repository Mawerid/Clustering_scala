package functions

object printMatrix extends App{

  def printM: Array[Array[Double]] => Unit = matrix => {

    val lenJ = matrix(0).length
    val lenI = matrix.length

    for (i <- 0 until lenI) {
      println(matrix(i).mkString("Array(", ", ", ")"))
    }
  }

}

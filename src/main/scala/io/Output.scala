package io

import com.github.tototoshi.csv._
import java.io.File
import java.nio.file.{Files, Paths}

object Output {

  /**
   *
   * @param centers
   * @param path
   * @return
   */
  def saveClusterCenters(centers: List[List[Double]], path: String): Boolean = {
    val file = new File(path)
    val writer = CSVWriter.open(file)
    writer.writeAll(centers)
    writer.close()
    file.isFile
  }

  /**
   *
   * @param scatter
   * @param path
   * @return
   */
  def saveScatterPlot(scatter: Array[Byte], path: String): Boolean = {
    val output = Files.write(Paths.get(path), scatter)
    output.toFile.isFile
  }

}

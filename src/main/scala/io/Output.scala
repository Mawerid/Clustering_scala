package io

import com.github.tototoshi.csv._
import java.io.File
import java.nio.file.{Files, Paths}

object Output {

  /**
   * Save centers of founded cluster (or just data)
   * @param centers list of point
   * @param path path to file
   * @return file created or not
   */
  def saveClusterCenters(centers: List[List[Double]], path: String): Boolean = {
    val file = new File(path)
    val writer = CSVWriter.open(file)
    writer.writeAll(centers)
    writer.close()
    file.isFile
  }

  /**
   * Save plot to png
   * @param scatter plot (as ByteArray)
   * @param path path to file
   * @return file created or not
   */
  def saveScatterPlot(scatter: Array[Byte], path: String): Boolean = {
    val output = Files.write(Paths.get(path), scatter)
    output.toFile.isFile
  }

}

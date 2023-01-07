package io

import com.github.tototoshi.csv._
import java.io.File

object Input {

  /**
   * Read CSV file with headers
   * @param path path to file
   * @return List of Map(header -> value)
   */
  def readFullHeadersCSV(path: String): List[Map[String, Double]] = {
    val reader = CSVReader.open(new File(path))
    reader.allWithHeaders().map(_.map(it => it._1 -> it._2.toDouble))
  }

  /**
   * Read CSV file without headers
   * @param path path to file
   * @return List of points
   */
  def readFullCSV(path: String): List[List[Double]] = {
    val reader = CSVReader.open(new File(path))
    reader.all().map(_.map(_.toDouble))
  }

  /**
   * Read CSV file without headers
   * @param path path to file
   * @return Iterator in each data point
   */
  def iteratorLineCSV(path: String): Iterator[Seq[Double]] = {
    val reader = CSVReader.open(new File(path))
    reader.iterator.map(_.map(_.toDouble))
  }
}

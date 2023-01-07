package io

import com.github.tototoshi.csv._
import java.io.File

object Input {

  /**
   *
   * @param path
   * @return
   */
  def readFullReadersCSV(path: String): List[Map[String, Double]] = {
    val reader = CSVReader.open(new File(path))
    reader.allWithHeaders().map(_.map(it => it._1 -> it._2.toDouble))
  }

  /**
   *
   * @param path
   * @return
   */
  def readFullCSV(path: String): List[List[Double]] = {
    val reader = CSVReader.open(new File(path))
    reader.all().map(_.map(_.toDouble))
  }

  /**
   *
   * @param path
   * @return
   */
  def iteratorLineCSV(path: String): Iterator[Seq[Double]] = {
    val reader = CSVReader.open(new File(path))
    reader.iterator.map(_.map(_.toDouble))
  }
}

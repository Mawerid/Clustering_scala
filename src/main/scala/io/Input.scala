package io

import com.github.tototoshi.csv._
import java.io.File

object Input {

  def readFullReadersCSV(path: String): List[Map[String, Double]] = {
    val reader = CSVReader.open(new File(path))
    reader.allWithHeaders().map(_.map(it => it._1 -> it._2.toDouble))
  }

  def readFullCSV(path: String): List[List[Double]] = {
    val reader = CSVReader.open(new File(path))
    reader.all().map(_.map(_.toDouble))
  }

  def iteratorLineCSV(path: String): Iterator[Seq[Double]] = {
    val reader = CSVReader.open(new File(path))
    reader.iterator.map(_.map(_.toDouble))
  }
}

package io

import com.github.tototoshi.csv._
import java.io.File
import scala.io.StdIn.readLine

object Input {

  /**
   * Print Intro message add fill path and method
   *
   * @return (path, method)
   */
  def intro(): (String, String) = {
    println("====================================================")
    println("--------------Clustering Data App-------------------")
    println("====================================================")
    println("This app use multiple thread to clustering your data")
    println("1) K-Means")
    println("2) Sequential Joins")
    println("3) Affinity Propagation")
    print("Enter path to file with data: -> ")
    var flag = false
    var path = ""
    while (!flag) {
      path = readLine()
      val file = new File(path)
      flag = file.isFile
      if (!flag) println("No such file. Try again")
    }

    print("Choose method: -> ")
    var method = ""
    flag = false
    while (!flag) {
      method = readLine()
      method = method match {
        case i if i == "K-Means" || i == "1" => "1"
        case i if i == "Sequential Joins" || i == "2" => "2"
        case i if i == "Affinity Propagation" || i == "3" => "3"
        case _ => "-1"
      }
      if (method != "-1") flag = true
      else println("No such method. Try again")
    }

    (path, method)
  }

  /**
   * Read CSV file with headers
   *
   * @param path path to file
   * @return List of Map(header -> value)
   */
  def readFullHeadersCSV(path: String): List[Map[String, Double]] = {
    val reader = CSVReader.open(new File(path))
    reader.allWithHeaders().map(_.map(it => it._1 -> it._2.toDouble))
  }

  /**
   * Read CSV file without headers
   *
   * @param path path to file
   * @return List of points
   */
  def readFullCSV(path: String): List[List[Double]] = {
    val reader = CSVReader.open(new File(path))
    reader.all().map(_.map(_.toDouble))
  }

  /**
   * Read CSV file without headers
   *
   * @param path path to file
   * @return Iterator in each data point
   */
  def iteratorLineCSV(path: String): Iterator[Seq[Double]] = {
    val reader = CSVReader.open(new File(path))
    reader.iterator.map(_.map(_.toDouble))
  }
}

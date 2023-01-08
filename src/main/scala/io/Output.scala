package io

import com.github.tototoshi.csv._
import java.io.File
import scala.io.StdIn.readLine
import functions.UpdateCenters._

object Output {

//  /**
//   * Make prediction for some point
//   *
//   * @param clusters list of clusters
//   */
//  def makePredictions(clusters: List[List[List[Double]]]): Unit = {
//
//  }

  /**
   * Print clustering summary
   *
   * @param clusters list of clusters
   */
  def summary(clusters: List[List[List[Double]]]): Unit = {
    println("Clustering done")
    println("Cluster's count is " + clusters.size)
    println("====================================================")
    println("Here are all clusters: ")
    clusters.indices.zip(clusters).foreach(it => println(s"In cluster ${it._1} -- " + it._2.size) + "elements")
  }

  /**
   * Save clusters or cluster's centers to files
   *
   * @param clusters list of clusters
   */
  def saving(clusters: List[List[List[Double]]]): Unit = {
    print("Save clusters? (Y/N) -> ")
    val answer1 = readLine()
    if (answer1 == "Y" || answer1 == "y") {
      print("Enter the path to directory: -> ")
      var flag = false
      var path = ""
      while (!flag) {
        path = readLine()
        val file = new File(path)
        flag = file.isDirectory
        if (!flag) println("No such directory. Try again")
      }
      saveClusters(clusters, path)
      println(s"Clusters have been saved to $path")
    }

    print("Save cluster's centers? (Y/N) -> ")
    val answer2 = readLine()
    if (answer2 == "Y" || answer2 == "y") {
      print("Enter the path to file: -> ")
      val path = readLine()
      val centers = clusters.map(updateCenter)
      saveClusterCenters(centers, path)
      println(s"Cluster's centers have been saved to $path")
    }
  }

  /**
   * Save centers of founded cluster (or just data)
   *
   * @param centers list of point
   * @param path    path to file
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
   * Save clusters in some directory
   *
   * @param clusters        list of clusters with point
   * @param pathToDirectory path to directory to save clusters
   * @return files created or not
   */
  def saveClusters(clusters: List[List[List[Double]]], pathToDirectory: String): Boolean = {
    clusters.indices.map(ind => {
      val file = new File(pathToDirectory + s"/cluster_$ind.csv")
      val writer = CSVWriter.open(file)
      writer.writeAll(clusters(ind))
      writer.close()
      file.isFile
    }).forall(_ == true)
  }
}

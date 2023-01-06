package io

import com.github.tototoshi.csv._
import java.io.File


object Input extends App {

  val path = "data/input/input_data.csv"

  val reader = CSVReader.open(new File(path))

  println(reader.all())

}

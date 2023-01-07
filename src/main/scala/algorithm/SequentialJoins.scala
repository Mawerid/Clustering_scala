package algorithm

import functions.Distance._
import functions.RandomCenters._
import java.util.concurrent.{ExecutorService, Executors}
import scala.annotation.tailrec
object SequentialJoins {


  def sequentialJoins(data: List[List[Double]]
             , clustersNum: Int
             , eps: Double = 0.00001
             , pool: ExecutorService
             , threadsNum: Int = 1)
  : List[List[List[Double]]] = {



  }
}

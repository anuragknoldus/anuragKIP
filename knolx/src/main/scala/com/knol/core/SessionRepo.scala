package com.knol.core
import scala.collection.mutable.MutableList

/**
 * Here we define a trait SessionRepo.
 * It contains the def of createS , updateS , deleteS , getList.
 */
trait SessionRepo {
  def createS(knolx: Knolx): Option[Int]
  def updateS(knolx: Knolx): Option[Int]
  def deleteS(id: Int): Int
  def getListS(): Option[List[Knolx]]

}

/**
 * Here we define a case class Knolx .
 * It used as constructor.
 */
case class Knolx(id: Int, topic: String, s_date: String, knol_id: Int) {
}

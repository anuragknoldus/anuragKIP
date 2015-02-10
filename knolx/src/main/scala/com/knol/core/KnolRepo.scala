package com.knol.core
import scala.collection.mutable.MutableList

/**
 * Here we define a trait SessionRepo.
 * It contains the def of createS , updateS , deleteS , getList.
 */

trait KnolRepo {

  def create(knold: Knolder): Option[Int]
  def update(knold: Knolder): Option[Int]
  def delete(id: Int): Int
  def getList(): Option[List[Knolder]]

}

/**
 * Here we define a case class Knolder .
 * It used as constructor.
 */

case class Knolder(name: String, email: String, mobno: Int, id: Int) {
}

package com.knol.core

trait KnolSessionRepo {
  def retreive(value : Int) : Option[List[KnolSession]]

}

case class KnolSession(id: Int, topic: String, s_date: String, Knol_id: Int, name: String) {

}

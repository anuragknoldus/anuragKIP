package com.knol.core.impl

import org.scalatest.FunSuite
import com.knol.core.KnolSession
import org.slf4j.LoggerFactory


class knolSessionTest extends FunSuite {
  val knolsessionimp = new KnolSessionImp
  test("Get a Complete List from the Table knol & knol_x"){
    val result = knolsessionimp.retreive(35)
    assert(!(result === None))
    val logger=LoggerFactory.getLogger(this.getClass)
    logger.info("Final result: "+result)
  }
}
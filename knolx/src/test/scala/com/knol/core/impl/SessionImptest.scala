package com.knol.core.impl

import org.scalatest.FunSuite
import com.knol.core.Knolx

class SessionImptest extends FunSuite {

 var sessionImp = new SessionImp

  /**
   * This is a Unit test for insert the value in the table knol_x.
   * We pass the value as input is(0,"Java","2015-08-24",23).
   * Here 0 for pass the value in object because id is auto incremented so it is a dummy value for insert in the table.
   * Expected output is inserted in knol_x.
   */

  test("For Insert the Value"){
    val info = Knolx(0,"Java","2015-08-24",35)
    val result = sessionImp.createS(info)
    assert(result===Some(1))
  }

  /**
   * This is a Unit test for update the value in the table knol_x.
   * We pass the value as input is(2,"Scala","2015-08-24",23).
   * Expected output is Update in knol_x.
   */

  test("For Update the Record"){
    val info = Knolx(19,"Scala","2015-08-24",35)
    val result = sessionImp.updateS(info)
    assert(result===Some(1))
  }

  /**
   * This is a Unit test for delete the value in the table knol_x.
   * We pass the value as input is(13).
   * Expected output is delete from knol_x.
   */

   test("Delete from knol_x") {

    val result = sessionImp.deleteS(18)
    assert(result === 1)
  }

  /**
   * This is a Unit test for retrieve the value in the table knol_x.
   * We pass the value in list as input is(2, "Java", "2015-06-15", 23).
   * Expected output is retrieve the value.
   */

   
  test("For Get the value") {

    val result = sessionImp.getListS()
    assert(!(result === None))

  }

}
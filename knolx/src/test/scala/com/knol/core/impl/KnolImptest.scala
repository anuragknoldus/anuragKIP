package com.knol.core.impl

import org.scalatest.FunSuite
import com.knol.core.Knolder
import com.knol.core.Knolder

class KnolImptest extends FunSuite {

  val knolRepoImp = new KnolRepoImpl

  /**
   * This is a Unit test for insert the value in the table knol.
   * We pass the value as input is("Raj","raj@knoldus.com",12345,0).
   * Here 0 for pass the value in object because id is auto incremented so it is a dummy value for insert in the table.
   * Expected output is inserted in knol.
   */

  test("create a knol"){
    
    val info = Knolder("Raj","rajatsri@knoldus.com",12345,0)
    val result = knolRepoImp.create(info)
    assert(result===Some(1))
    //assert(result===1)
    
    //assert(knolRepoImp.create(info)===None)
  }
  
  /**
   * This is a Unit test for update the value in the table knol.
   * We pass the value as input is("Vikas","anurg@knoldus.com",123654,16).
   * Expected output is Update in knol.
   */

   test("update a knol"){
    val info= Knolder("Vikas","anurg@knoldus.com",123654,39)
    val result=knolRepoImp.update(info)
    assert(result===Some(1))
  }
  
  /**
   * This is a Unit test for delete the value in the table knol.
   * We pass the value as input is(24).
   * Expected output is delete from knol.
   */

  test("Delete from knol") {

    val result = knolRepoImp.delete(39)
    assert(result === 1)
  }

  /**
   * This is a Unit test for retrieve the value in the table knol.
   * We pass the value in list as input is("Akash","akash@gmail.com",123,23).
   * Expected output is retrieve the value.
   */
  
   test("get A LIST") {

    val result = knolRepoImp.getList()
    assert(!(result === None))
     
    
  }

}
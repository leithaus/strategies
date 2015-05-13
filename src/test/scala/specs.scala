package com.biosimilarity.mdp4tw


import ru.circumflex._, core._, web._

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.MustMatchers

@RunWith(classOf[JUnitRunner])
class MySpec
  extends FreeSpec
  with BeforeAndAfter
  with MustMatchers {

  before {
    cx("cx.router") = classOf[Main]
    MockApp.start()
  }

  after {
    MockApp.stop()
  }

  "My application" - {
    "test itself" in {
      MockApp.get("/test").execute().content must equal ("I'm fine, thanks!")
    }
  }
}
package com.biosimilarity.mdp4tw

import ru.circumflex._, core._, web._, freemarker._
import java.util.Date

class Main extends Router {
  val log = new Logger("com.biosimilarity.mdp4tw")

  'currentDate := new Date

  get("/test") = "I'm fine, thanks!"
  get("/") = ftl("index.ftl")
  get("/skein") = redirect("index.html")

}

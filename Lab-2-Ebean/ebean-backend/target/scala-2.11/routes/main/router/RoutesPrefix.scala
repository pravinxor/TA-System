
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/pravin/Homework/CS4353/Lab-2-Ebean/ebean-backend/conf/routes
// @DATE:Sat Jan 28 12:36:13 CST 2023


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}

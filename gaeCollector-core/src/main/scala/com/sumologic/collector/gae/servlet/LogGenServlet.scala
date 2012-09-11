package com.sumologic.collector.gae.servlet

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException
import java.util.logging.Level
import java.util.logging.Logger
import scala.throws

/**
 * User: gorzell
 * Date: 9/6/12
 */
class LogGenServlet extends HttpServlet {
  private val log = Logger.getLogger(classOf[LogGenServlet].getName)

  @throws(classOf[ServletException])
  @throws(classOf[IOException])
  override protected def doGet(request: HttpServletRequest, response: HttpServletResponse) {
    log.info("Normal log line" + request.getQueryString())
    response.getWriter.append("Logged a random event")
    response.getWriter.flush()
    if ((System.currentTimeMillis() % 10) < 2) log.log(Level.WARNING, "Bullshit Exception", new IllegalStateException())
  }
}

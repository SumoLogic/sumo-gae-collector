package com.sumologic.collector.gae.servlet

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException
import java.util.logging.Logger
import scala.throws
;

/**
 * User: gorzell
 * Date: 9/6/12
 */
class ReceiveTestServlet extends HttpServlet {
  private var log = Logger.getLogger(classOf[LogGenServlet].getName)

  @throws(classOf[ServletException])
  @throws(classOf[IOException])
  override protected def doPost(request: HttpServletRequest, response: HttpServletResponse) {
    log.info("Request length:" + request.getContentLength)
    log.info("Content-Type:" + request.getHeader("Content-Type"))
    val builder = new StringBuilder()
    val reader = request.getReader
    var line = reader.readLine()
    while (line != null) {
      builder.append(line)
      builder.append("\n")
      line = reader.readLine()
    }
    log.info(builder.toString())
  }

  @throws(classOf[ServletException])
  @throws(classOf[IOException])
  override protected def doGet(request: HttpServletRequest, response: HttpServletResponse) {
    //TODO return error response
  }
}

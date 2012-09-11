package com.sumologic.collector.gae.servlet

import com.sumologic.collector.gae.CollectApplicationLogs
import java.io.IOException
import scala.throws
import javax.servlet.ServletException
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

/**
 * User: gorzell
 * Date: 9/6/12
 */
class RequestLogServlet extends javax.servlet.http.HttpServlet {
  @throws(classOf[ServletException])
  @throws(classOf[IOException])
  override protected def doPost(request: HttpServletRequest, response: HttpServletResponse) {
    //TODO return error response
  }

  @throws(classOf[ServletException])
  @throws(classOf[IOException])
  override protected def doGet(request: HttpServletRequest, response: HttpServletResponse) {
    val grf = if(request.getParameter("googleFormat")!=null)request.getParameter("googleFormat").toBoolean else false
    new CollectApplicationLogs().collect(includeAppLogs = false, minLogLevel = "INFO",
      googleRequestFormat = grf)
  }
}

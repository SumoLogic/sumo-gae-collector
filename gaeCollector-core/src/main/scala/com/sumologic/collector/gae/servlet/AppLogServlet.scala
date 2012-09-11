package com.sumologic.collector.gae.servlet

import com.sumologic.collector.gae.CollectApplicationLogs
import javax.servlet.http.{HttpServletResponse, HttpServletRequest}
import javax.servlet.ServletException
import java.io.IOException

/**
 * User: gorzell
 * Date: 9/6/12
 */
class AppLogServlet extends javax.servlet.http.HttpServlet {
  @throws(classOf[ServletException])
  @throws(classOf[IOException])
  override protected def doPost(request: HttpServletRequest, response: HttpServletResponse) {
    //TODO error response code
  }

  @throws(classOf[ServletException])
  @throws(classOf[IOException])
  override protected def doGet(request: HttpServletRequest, response: HttpServletResponse) {
    val grf = if(request.getParameter("googleFormat")!=null)request.getParameter("googleFormat").toBoolean else false
    new CollectApplicationLogs().collect(includeAppLogs = true, minLogLevel = "INFO",
      googleRequestFormat = grf )
  }
}

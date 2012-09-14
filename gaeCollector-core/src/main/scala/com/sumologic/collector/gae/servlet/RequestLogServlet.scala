/**
 *    _____ _____ _____ _____    __    _____ _____ _____ _____
 *   |   __|  |  |     |     |  |  |  |     |   __|     |     |
 *   |__   |  |  | | | |  |  |  |  |__|  |  |  |  |-   -|   --|
 *   |_____|_____|_|_|_|_____|  |_____|_____|_____|_____|_____|
 *
 *                UNICORNS AT WARP SPEED SINCE 2010
 *
 * Copyright (C) 2012 Sumo Logic
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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

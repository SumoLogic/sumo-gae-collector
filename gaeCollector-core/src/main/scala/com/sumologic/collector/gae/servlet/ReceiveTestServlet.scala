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

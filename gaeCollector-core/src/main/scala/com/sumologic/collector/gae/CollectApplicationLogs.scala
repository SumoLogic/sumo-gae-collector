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
package com.sumologic.collector.gae

import com.google.appengine.api.log.{RequestLogs, LogServiceFactory, AppLogLine, LogQuery}
import scala.collection.JavaConversions._
import java.util.Date
import java.text.SimpleDateFormat
import java.net.{MalformedURLException, HttpURLConnection, URL}
import java.io.IOException
import java.util.logging.{Level, Logger}
import com.google.appengine.api.urlfetch._
import com.google.appengine.api.log.LogService.LogLevel

/**
 * User: gorzell
 * Date: 9/6/12
 */

class CollectApplicationLogs {
  val log = Logger.getLogger(classOf[CollectApplicationLogs].getName)
  //ISO Time Format YYYY-MM-DDThh:mm:ss.sTZD = 1997-07-16T19:20:30.45+01:00
  val format = new SimpleDateFormat("yyyy-MM-DD'T'HH:mm:ss.SSZ")
  val newLineBreak = "--\n"

  def collect(includeAppLogs: Boolean = false, minLogLevel: String = "INFO", googleRequestFormat: Boolean = false) {
    val urlString = System.getProperty("com.sumo.collection.url")
    val queryResult = getLogs(includeAppLogs, minLogLevel)

    log.info("Sending %d logs to %s".format(queryResult.size, urlString))
    val requestBuilder = new StringBuilder
    requestBuilder.append(newLineBreak)

    queryResult.foreach(requestLog => {
      log.fine("Logs per request %d".format(requestLog.getAppLogLines.size))
      requestBuilder.append(if (googleRequestFormat) "%s%s%s".format(requestLog.getCombined, "\n", newLineBreak)
      else formatRequestLogLine(requestLog))

      if (includeAppLogs) {
        requestLog.getAppLogLines.foreach(appLog => requestBuilder.append(formatAppLogLine(appLog)))
      }
    })
    log.fine(requestBuilder.toString)

    val response = sendToSumo(urlString, requestBuilder)
    if (response.getResponseCode != HttpURLConnection.HTTP_OK) {
      log.severe("HTTP return code is non 200: %d".format(response.getResponseCode))
    }
    log.fine("HTTP return code is %d".format(response.getResponseCode))
  }

  private def sendToSumo(urlString: String, payload: StringBuilder): HTTPResponse = {
    try {
      val url = new URL(urlString)
      val options = FetchOptions.Builder.disallowTruncate.followRedirects.validateCertificate
      val request = new HTTPRequest(url, HTTPMethod.POST, options)
      request.setHeader(new HTTPHeader("Content-Type", "text/plain"))
      request.setPayload(payload.toArray.map(_.toByte))
      URLFetchServiceFactory.getURLFetchService.fetch(request)
    } catch {
      case ex: MalformedURLException => log.log(Level.SEVERE, "", ex)
      throw ex
    }
  }

  private def getLogs(includeAppLogs: Boolean, minLogLevel: String): Seq[RequestLogs] = {
    val logService = LogServiceFactory.getLogService
    val query = LogQuery.Builder.withDefaults().minLogLevel(LogLevel.valueOf(minLogLevel)).includeIncomplete(true).
        includeAppLogs(includeAppLogs)
    logService.fetch(query).toSeq
  }

  private def formatAppLogLine(logLine: AppLogLine): String = {
    val tsString = format.format(new Date(logLine.getTimeUsec / 1000))
    "%s %s %s\n%s".format(tsString, logLine.getLogLevel, logLine.getLogMessage, newLineBreak)
  }

  private def formatRequestLogLine(rLog: RequestLogs): String = {
    val startTsString = format.format(new Date(rLog.getStartTimeUsec / 1000))
    val endTsString = format.format(new Date(rLog.getEndTimeUsec / 1000))

    "%s %s %s %s %s %s %s %s %s %s %s %s %s %s %s\n%s".
        format(startTsString, endTsString, rLog.getAppId, rLog.getCost, rLog.getHost, rLog.getHttpVersion, rLog.getIp,
      rLog.getLatencyUsec, rLog.getMcycles, rLog.getMethod, rLog.getNickname, rLog.getReferrer, rLog.getResource,
      rLog.getResponseSize, rLog.getUserAgent, newLineBreak)
  }
}
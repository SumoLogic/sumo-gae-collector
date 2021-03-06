# Overview

# Design

![](http://sumologic.github.com/sumo-gae-collector/images/design.png)

# Configuration

## web.xml
```xml
  <servlet>
    <servlet-name>RequestLogServlet</servlet-name>
    <servlet-class>com.sumologic.collector.gae.servlet.RequestLogServlet</servlet-class>
  </servlet>
  <servlet>
    <servlet-name>AppLogServlet</servlet-name>
    <servlet-class>com.sumologic.collector.gae.servlet.AppLogServlet</servlet-class>
  </servlet>
  <servlet-mapping>
      <servlet-name>RequestLogServlet</servlet-name>
      <url-pattern>/cron/logs/request</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
      <servlet-name>AppLogServlet</servlet-name>
      <url-pattern>/cron/logs/application</url-pattern>
  </servlet-mapping>
  <security-constraint>
      <web-resource-collection>
        <url-pattern>/cron/*</url-pattern>
      </web-resource-collection>
      <auth-constraint>
        <role-name>admin</role-name>
      </auth-constraint>
  </security-constraint>
```

## appengine-web.xml
```xml
<system-properties>
    <property name="com.sumo.collection.url"
              value=""/>
```

## cron.xml
Pick one unless you want to send multiple copies

```xml
<?xml version="1.0" encoding="UTF-8"?>
<cronentries>
  <cron>
    <url>/cron/logs/application</url>
    <description>Send logs to sumo every hours</description>
    <schedule>every 1 hours</schedule>
  </cron>
  <cron>
    <url>/cron/logs/request</url>
    <description>Send logs to sumo every hours</description>
    <schedule>every 1 hours</schedule>
  </cron>
  <cron>
    <url>/cron/logs/request?googleFormat=true</url>
    <description>Send logs to sumo every hours</description>
    <schedule>every 1 hours</schedule>
  </cron>
</cronentries>
```

# [Scaladoc](http://sumologic.github.com/sumo-gae-collector/docs/scaladoc)


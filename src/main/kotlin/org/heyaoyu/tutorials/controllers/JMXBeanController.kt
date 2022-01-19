package org.heyaoyu.tutorials.controllers

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import javax.management.Query
import javax.management.StringValueExp
import javax.management.remote.JMXConnectorFactory
import javax.management.remote.JMXServiceURL

@RestController
@RequestMapping("/jmx")
class JMXBeanController {

    @RequestMapping("/log4j2RingBufferRemainning")
    @ResponseBody
    fun getLog4j2RingBufferRemainning(): Any {
        val hostName = "10.85.33.96"
        val port = 19200
        val serviceURL = "service:jmx:rmi:///jndi/rmi://${hostName}:${port}/jmxrmi"
        val jmxUrl = JMXServiceURL(serviceURL)
        val jmxCon = JMXConnectorFactory.connect(jmxUrl)

        var ret = mutableListOf<String>()
        try {
            val query = Query.isInstanceOf(StringValueExp("org.apache.logging.log4j.core.jmx.RingBufferAdmin"))
            val mbeans = jmxCon.getMBeanServerConnection().queryMBeans(null, query)
            mbeans.forEach {
                ret.add(
                    "${it.className}###${it.objectName}###${
                        jmxCon.getMBeanServerConnection().getAttribute(it.objectName, "RemainingCapacity")
                    }"
                )
            }
//      jmxCon.getMBeanServerConnection().getMBeanInfo(it.objectName).attributes.get(0) - BufferSize
//      jmxCon.getMBeanServerConnection().getMBeanInfo(it.objectName).attributes.get(1) - RemainingCapacity
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            jmxCon?.close()
        }
        return ret
    }
}
package org.heyaoyu.tutorials.controllers

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/kotlin")
class KotlinxController {

    @RequestMapping("/kotlinx")
    @ResponseBody
    fun kotlinx(): Any? {
        runBlocking {
            // start coroutine
//            val d = GlobalScope.async {}
            delayAndPrintln(1)
            launch {
                delayAndPrintln(2)
                coroutineScope {
                    delayAndPrintln(3, 300)
                }
                delayAndPrintln(4)
            }
            //suspend invoked with
            delayAndPrintln(5)
            coroutineScope {
                delayAndPrintln(6, 1000)
            }
//            d.await()
//            withTimeout(2000) {}
            delayAndPrintln(7)
        }
        return "OK"
    }

    suspend fun delayAndPrintln(msg: Int, delays: Long = 100) {
        delay(delays)
        println(msg)
    }
}
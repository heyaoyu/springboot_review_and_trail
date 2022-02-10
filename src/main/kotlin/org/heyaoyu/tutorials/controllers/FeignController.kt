package org.heyaoyu.tutorials.controllers

import org.heyaoyu.tutorials.services.FeignService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/feign")
class FeignController(val feignService: FeignService) {

    @RequestMapping("/demo")
    @ResponseBody
    fun feignDemo(): Any {
        return feignService.feignTest()
    }

    @RequestMapping("/feignCalled")
    @ResponseBody
    fun feignCalled(): Any {
        throw Exception("Excep")
    }

}
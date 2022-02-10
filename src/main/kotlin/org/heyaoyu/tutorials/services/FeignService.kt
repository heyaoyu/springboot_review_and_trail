package org.heyaoyu.tutorials.services

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(value = "spring-cloud-service-provider", path = "/mongo")
interface FeignService {
    @GetMapping("/findOne")
    fun feignTest(): String
}
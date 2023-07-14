package org.utarid.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("spring-cloud-eureka-client")
public interface FeignInterface {
    @RequestMapping("/greeting")
    String greeting();
}

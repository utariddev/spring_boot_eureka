package org.utarid.client;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class ClientApplicationTests {

    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private FeignInterface feignInterface;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void testWithFeign() {
        String response = feignInterface.greeting();
        System.out.println("response: " + response);
        Assertions.assertEquals("greeting", response);
    }

    @Test
    void testWithRestTemplate() {
        InstanceInfo service = eurekaClient
                .getApplication("spring-cloud-eureka-client")
                .getInstances()
                .getFirst();

        String hostName = service.getHostName();
        int port = service.getPort();

        System.out.println("hostName: " + hostName);
        System.out.println("port: " + port);

        String url = "http://" + hostName + ":" + port + "/greeting";
        RestTemplate rTemplate = restTemplateBuilder.build();
        String response = rTemplate.getForObject(url, String.class);
        System.out.println("response: " + response);
        Assertions.assertEquals("greeting", response);
    }

    @Test
    void testWithLoadBalancedRestTemplate() {
        String response = restTemplate.getForObject("http://spring-cloud-eureka-client/greeting", String.class);
        System.out.println("response: " + response);
        Assertions.assertEquals("greeting", response);
    }
}

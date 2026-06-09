/*
 * ©2020 Nisum Technologies, Inc. All Rights Reserved.
 */
package com.pmotracker.msproject;

/*
 *  Created by IntelliJ IDEA
 *  User: Malik Imran (msabir@nisum.com)
 *  Date: 3/20/2020
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages  = {"com.pmotracker"})
@Configuration
@EnableAsync
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

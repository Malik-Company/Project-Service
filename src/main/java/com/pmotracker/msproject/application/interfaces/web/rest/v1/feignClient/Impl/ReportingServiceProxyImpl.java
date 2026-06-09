/*
 * ©2020 Nisum Technologies, Inc. All Rights Reserved.
 */

package com.pmotracker.msproject.application.interfaces.web.rest.v1.feignClient.Impl;

/*
 *  Created by IntelliJ IDEA
 *  User: Malik Imran (msabir@nisum.com)
 *  Date: 3/20/2020
 */

import com.pmotracker.msproject.application.dto.ProjectDto;
import com.pmotracker.msproject.application.interfaces.web.rest.v1.feignClient.ReportingServiceProxy;
import com.pmotracker.msproject.infrastructure.util.ConversionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@EnableAutoConfiguration
@RequiredArgsConstructor
public class ReportingServiceProxyImpl {

    private final ReportingServiceProxy reportingServiceProxy;


    //@HystrixCommand(fallbackMethod = "defaultResponse")
    public void onUpdate(ProjectDto data) {


        // TODO calls should be in seperate thread
        try {
            reportingServiceProxy.saveChanges(data);
        } catch (Exception e) {
            log.error("search Service not responding....",e);
        }



    }

    // When we define a fallback method, the fallback-method must match the same parameters of the method where you define the Hystrix Command using the hystrix-command annotation.
    public void defaultResponse(String data) {
        log.warn("You are seeing this fallback response because the underlying micro service is down.");
    }

}

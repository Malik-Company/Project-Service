package com.pmotracker.msproject.application.interfaces.web.rest.v1.feignClient;

import com.pmotracker.msproject.application.dto.ProjectDto;
//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@FeignClient(name = "reporting-service")
//@FeignClient(name="content-service", url="http://110.93.214.166:30898")
public interface ReportingServiceProxy {

    @RequestMapping(method = RequestMethod.POST, value = "${pmotracker.reporting-service-path}")
    ResponseEntity<String> saveChanges(@RequestBody ProjectDto value);

}

/*
 * ©2020 Nisum Technologies, Inc. All Rights Reserved.
 */
package com.pmotracker.msproject.infrastructure.sync.Impl;

/*
 *  Created by IntelliJ IDEA
 *  User: Malik Imran (msabir@nisum.com)
 *  Date: 3/20/2020
 */

import com.pmotracker.msproject.application.dto.ProjectDto;
import com.pmotracker.msproject.application.dto.ProjectResponseDto;
import com.pmotracker.msproject.domain.converter.ProjectToProjectDtoConverter;
import com.pmotracker.msproject.application.interfaces.amqp.sync.ProjectSyncByAmqp;
import com.pmotracker.msproject.application.interfaces.web.rest.v1.feignClient.Impl.ReportingServiceProxyImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProjectSync {

    private final ReportingServiceProxyImpl bookSyncByFeignClientImpl;
    private final ProjectSyncByAmqp dealSyncByAmqp;
    private final ProjectToProjectDtoConverter dealToDealDtoConverter;
    @Value("${data.sync.using.feign.client}")
    private Boolean isUseFeignClient;

    @Async("threadPoolTaskExecutor")
    public void onUpdate(ProjectDto dealDto) {

        log.info("Inside DealSync method onUpdate(Deal deal)...");

        if (isUseFeignClient) {
            // Sync with Feign client --- Async Call
            bookSyncByFeignClientImpl.onUpdate(dealDto);
        } else {
            // Sync with Queue --- Async Call
            ProjectResponseDto dto = dealToDealDtoConverter.convertForDealResponseDto(dealDto);
            dealSyncByAmqp.onUpdate(dto);
        }
    }
}

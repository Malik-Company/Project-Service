/*
 * ©2020 Nisum Technologies, Inc. All Rights Reserved.
 */

package com.pmotracker.msproject.application.service;

/*
 *  Created by IntelliJ IDEA
 *  User: Malik Imran (msabir@nisum.com)
 *  Date: 3/20/2020
 */

import com.pmotracker.msproject.application.dto.ProjectDto;
import com.pmotracker.msproject.application.interfaces.web.rest.v1.criteria.ProjectCriteria;
import com.pmotracker.msproject.domain.service.ProjectService;
import com.pmotracker.msproject.infrastructure.sync.Impl.ProjectSync;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@EnableAsync
@RequiredArgsConstructor
public class ProjectApplicationService {

    private final ProjectService projectService;

    public Page<ProjectDto> getAllByCriteria(ProjectCriteria criteria, Pageable pageable) {
        return projectService.getAllByCriteria(criteria, pageable);
    }

    public ProjectDto save(ProjectDto dto) {
        projectService.save(dto);
        return dto;
    }

    public ProjectDto get(String code) {
        return projectService.get(code);
    }

    public void update(ProjectDto dto) {
        projectService.update(dto);
    }

    public void delete(String id, String userName) {
        projectService.delete(id, userName);
    }

}
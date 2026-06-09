/*
 * ©2020 Nisum Technologies, Inc. All Rights Reserved.
 */
package com.pmotracker.msproject.domain.service;
/*
 *  Created by IntelliJ IDEA
 *  User: Malik Imran (msabir@nisum.com)
 *  Date: 3/20/2020
 */

import com.pmotracker.msproject.application.dto.ProjectDto;
import com.pmotracker.msproject.application.interfaces.web.rest.v1.criteria.ProjectCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {

    Page<ProjectDto> getAllByCriteria(ProjectCriteria criteria, Pageable pageable);

    void save(ProjectDto deal);

    ProjectDto get(String code);

    void update(ProjectDto deal);

    void delete(String code, String userName);

}


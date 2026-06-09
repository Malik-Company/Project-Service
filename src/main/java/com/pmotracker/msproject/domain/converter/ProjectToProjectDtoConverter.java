/*
 * ©2020 Nisum Technologies, Inc. All Rights Reserved.
 */

package com.pmotracker.msproject.domain.converter;

/*
 *  Created by IntelliJ IDEA
 *  User: Malik Imran (msabir@nisum.com)
 *  Date: 3/20/2020
 */


import com.pmotracker.msproject.application.dto.ProjectDto;
import com.pmotracker.msproject.application.dto.ProjectResponseDto;
import com.pmotracker.msproject.domain.model.Project;
import com.pmotracker.msproject.domain.repository.ProjectRepository;
import com.pmotracker.msproject.infrastructure.util.ConversionUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectToProjectDtoConverter {
    private final ModelMapper modelMapper;
    private final ConversionUtils conversionUtils;
    private final ProjectRepository dealRepository;


    public ProjectDto convert(Project project) {
        ProjectDto dto = modelMapper.map(project, ProjectDto.class);
        return dto;
    }

    public ProjectResponseDto convertForDealResponseDto(Project project) {
        ProjectResponseDto dto = modelMapper.map(project, ProjectResponseDto.class);
        return dto;
    }
    public ProjectResponseDto convertForDealResponseDto(ProjectDto deal) {
        ProjectResponseDto dto = modelMapper.map(deal, ProjectResponseDto.class);
        return dto;
    }

}
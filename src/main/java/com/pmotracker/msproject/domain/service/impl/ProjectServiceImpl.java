package com.pmotracker.msproject.domain.service.impl;

import com.pmotracker.msproject.application.dto.ProjectDto;
import com.pmotracker.msproject.application.interfaces.web.rest.v1.criteria.ProjectCriteria;
import com.pmotracker.msproject.domain.projection.ProjectProjection;
import com.pmotracker.msproject.domain.repository.ProjectRepository;
import com.pmotracker.msproject.domain.service.ProjectService;
import com.pmotracker.msproject.infrastructure.util.ConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ConversionUtils conversionUtils;

    @Autowired
    private ProjectRepository projectRepository;


    @Override
    public Page<ProjectDto> getAllByCriteria(ProjectCriteria criteria, Pageable pageable) {
        return null;
    }

    @Override
    public void save(ProjectDto deal) {

    }

    @Override
    public ProjectDto get(String code) {
        return null;
    }

    @Override
    public void update(ProjectDto deal) {

    }

    @Override
    public void delete(String code, String userName) {

    }
}

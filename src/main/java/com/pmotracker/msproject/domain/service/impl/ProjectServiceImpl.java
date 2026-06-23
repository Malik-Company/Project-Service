package com.pmotracker.msproject.domain.service.impl;

import com.pmotracker.msproject.application.dto.ProjectDto;
import com.pmotracker.msproject.application.interfaces.web.rest.v1.criteria.ProjectCriteria;
import com.pmotracker.msproject.domain.model.Project;
import com.pmotracker.msproject.domain.repository.ProjectRepository;
import com.pmotracker.msproject.domain.service.ProjectService;
import com.pmotracker.msproject.domain.specification.ProjectSpecification;
import com.pmotracker.msproject.infrastructure.common.RecordStatus;
import com.pmotracker.msproject.infrastructure.util.ConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ConversionUtils conversionUtils;

    @Autowired
    private ProjectRepository projectRepository;


    @Override
    public Page<ProjectDto> getAllByCriteria(ProjectCriteria criteria, Pageable pageable) {
        return projectRepository.findAll(new ProjectSpecification(criteria), pageable)
                .map(project -> conversionUtils.mapEntityToDto(project, ProjectDto.class));
    }

    @Override
    public ProjectDto save(ProjectDto projectDto) {
        Project project = conversionUtils.mapDtoToEntity(projectDto, Project.class);
        if (project.getCode() == null || project.getCode().trim().isEmpty()) {
            project.setCode(UUID.randomUUID().toString());
        }
        Project savedProject = projectRepository.save(project);
        return conversionUtils.mapEntityToDto(savedProject, ProjectDto.class);
    }

    @Override
    public ProjectDto get(String code) {
        Project project = findProject(code);
        return conversionUtils.mapEntityToDto(project, ProjectDto.class);
    }

    @Override
    public void update(ProjectDto projectDto) {
        Project project = findProject(projectDto.getCode());
        conversionUtils.mapSourceModelToDestinationModel(projectDto, project);
        projectRepository.save(project);
    }

    @Override
    public void delete(String code, String userName) {
        Project project = findProject(code);
        project.setRecordStatus(RecordStatus.DELETED);
        project.setUpdatedBy(userName);
        projectRepository.save(project);
    }

    private Project findProject(String code) {
        return projectRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Project not found for code: " + code));
    }
}

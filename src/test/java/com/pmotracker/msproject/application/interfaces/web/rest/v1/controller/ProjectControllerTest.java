
package com.pmotracker.msproject.application.interfaces.web.rest.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmotracker.msproject.application.dto.ProjectDto;
import com.pmotracker.msproject.application.dto.UserContext;
import com.pmotracker.msproject.application.interfaces.web.rest.v1.criteria.ProjectCriteria;
import com.pmotracker.msproject.application.service.ProjectApplicationService;
import com.pmotracker.msproject.domain.model.Project;
import com.pmotracker.msproject.infrastructure.common.ProjectStatus;
import com.pmotracker.msproject.infrastructure.util.ConversionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    private static final String BASE_URL = "/v1/public/admin/books";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProjectApplicationService projectApplicationService;

    @MockBean
    private ConversionUtils conversionUtils;

    private UserContext createUserContext() {
        UserContext userContext = new UserContext();
        userContext.setUserId("test-user");
        userContext.setFullName("Test User");
        return userContext;
    }

    private ProjectDto createProjectDto() {
        ProjectDto dto = new ProjectDto();
        dto.setId(1L);
        dto.setCode("PROJ-001");
        dto.setName("Test Project");
        dto.setBook(createProject());
        return dto;
    }

    private Project createProject() {
        Project project = new Project();
        project.setId(1L);
        project.setCode("PROJ-001");
        project.setName("Test Project");
        project.setSummary("A test project");
        project.setStatus(ProjectStatus.PUBLISHED);
        project.setCreatedAt(new Date());
        project.setUpdatedAt(new Date());
        return project;
    }

    @Test
    void saveProject_shouldReturnCreated() throws Exception {
        ProjectDto projectDto = createProjectDto();
        String projectDtoJson = objectMapper.writeValueAsString(projectDto);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectDtoJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/v1/public/admin/books/1"));
    }

    @Test
    void getProjectByCode_shouldReturnProject() throws Exception {
        String projectCode = "PROJ-001";
        ProjectDto projectDto = createProjectDto();

        when(projectApplicationService.get(projectCode)).thenReturn(projectDto);

        mockMvc.perform(get(BASE_URL + "/{code}", projectCode))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(projectCode));
    }

    @Test
    void updateProject_shouldReturnNoContent() throws Exception {
        String projectCode = "PROJ-001";
        ProjectDto projectDto = createProjectDto();
        UserContext userContext = createUserContext();
        String userInfo = objectMapper.writeValueAsString(userContext);

        when(conversionUtils.convertStringToObject(userInfo, UserContext.class)).thenReturn(userContext);
        doNothing().when(projectApplicationService).update(any(ProjectDto.class));
        
        mockMvc.perform(put(BASE_URL + "/{code}", projectCode)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("user-info", userInfo)
                        .content(objectMapper.writeValueAsString(projectDto)))
                .andExpect(status().isNoContent());
    }

    @Test
    void getProjects_shouldReturnPageOfProjects() throws Exception {
        ProjectDto projectDto = createProjectDto();
        List<ProjectDto> projectDtos = Collections.singletonList(projectDto);
        Page<ProjectDto> projectPage = new PageImpl<>(projectDtos, Pageable.unpaged(), 1);

        when(projectApplicationService.getAllByCriteria(any(ProjectCriteria.class), any(Pageable.class)))
                .thenReturn(projectPage);

        mockMvc.perform(get(BASE_URL)
                        .param("name", "Test Project"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].name").value("Test Project"));
    }

    @Test
    void deleteProject_shouldReturnNoContent() throws Exception {
        String projectCode = "PROJ-001";
        UserContext userContext = createUserContext();
        String userInfo = objectMapper.writeValueAsString(userContext);

        when(conversionUtils.convertStringToObject(userInfo, UserContext.class)).thenReturn(userContext);
        doNothing().when(projectApplicationService).delete(eq(projectCode), any(UserContext.class));

        mockMvc.perform(delete(BASE_URL + "/{code}", projectCode)
                        .header("user-info", userInfo))
                .andExpect(status().isNoContent());
    }
}


package com.pmotracker.msproject.application.interfaces.web.rest.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmotracker.msproject.application.dto.ProjectDto;
import com.pmotracker.msproject.application.dto.UserContext;
import com.pmotracker.msproject.application.interfaces.web.rest.v1.criteria.ProjectCriteria;
import com.pmotracker.msproject.application.service.ProjectApplicationService;
import com.pmotracker.msproject.infrastructure.util.ConversionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;


@WebMvcTest(ProjectController.class)
class ProjectControllerApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectApplicationService projectApplicationService;

    @MockBean
    private ConversionUtils conversionUtils;

    @Autowired
    private ObjectMapper objectMapper;

    private final String userContextHeader = "{\"emailAddress\":\"test@test.com\"}";

    @Test
    void save_shouldReturnCreated() throws Exception {
        mockMvc.perform(post("/v1/public/admin/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .header("user-info", userContextHeader))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/v1/public/admin/books/1"));
    }

    @Test
    void get_shouldReturnProject() throws Exception {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setCode("PROJ1");
        projectDto.setName("Test Project");

        when(projectApplicationService.get("PROJ1")).thenReturn(projectDto);

        mockMvc.perform(get("/v1/public/admin/books/{code}", "PROJ1"))
                .andExpect(status().isOk());
    }

    @Test
    void update_shouldReturnNoContent() throws Exception {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setCode("PROJ1");
        projectDto.setName("Updated Project");
        
        UserContext userContext = new UserContext();
        userContext.setEmailAddress("test@test.com");

        when(conversionUtils.convertStringToObject(anyString(), any(Class.class))).thenReturn(userContext);
        doNothing().when(projectApplicationService).update(any(ProjectDto.class));

        mockMvc.perform(put("/v1/public/admin/books/{code}", "PROJ1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectDto))
                        .header("user-info", userContextHeader))
                .andExpect(status().isNoContent());
    }

    @Test
    void get_withCriteria_shouldReturnPageOfProjects() throws Exception {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setCode("PROJ1");
        Page<ProjectDto> projectPage = new PageImpl<>(Collections.singletonList(projectDto));

        when(projectApplicationService.getAllByCriteria(any(ProjectCriteria.class), any(Pageable.class)))
                .thenReturn(projectPage);

        mockMvc.perform(get("/v1/public/admin/books")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        UserContext userContext = new UserContext();
        userContext.setEmailAddress("test@test.com");
        
        when(conversionUtils.convertStringToObject(anyString(), any(Class.class))).thenReturn(userContext);
        doNothing().when(projectApplicationService).delete("PROJ1", "test@test.com");

        mockMvc.perform(delete("/v1/public/admin/books/{code}", "PROJ1")
                        .header("user-info", userContextHeader))
                .andExpect(status().isNoContent());
    }
}

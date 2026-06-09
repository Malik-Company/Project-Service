/*
 * ©2020 Nisum Technologies, Inc. All Rights Reserved.
 */
package com.pmotracker.msproject.application.interfaces.web.rest.v1.controller;

/*
 *  Created by IntelliJ IDEA
 *  User: Malik Imran (msabir@nisum.com)
 *  Date: 3/20/2020
 */

import com.pmotracker.msproject.application.dto.ProjectDto;
import com.pmotracker.msproject.application.dto.UserContext;
import com.pmotracker.msproject.application.interfaces.web.rest.v1.criteria.ProjectCriteria;
import com.pmotracker.msproject.application.service.ProjectApplicationService;
import com.pmotracker.msproject.infrastructure.util.ConversionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ProjectController {
    private final ConversionUtils conversionUtils;
    private final ProjectApplicationService projectApplicationService;

    @PostMapping("/v1/public/admin/books")
    public ResponseEntity<Object> save(@Valid @RequestBody String dto, HttpServletRequest request, @RequestHeader("user-info") String userContext) {
        URI uri = URI.create(request.getRequestURI().concat("/").concat(new Integer(1).toString()));
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/v1/public/admin/books/{code}")
    public ResponseEntity<Object> get(@PathVariable("code") String code) {
        return ResponseEntity.ok(projectApplicationService.get(code));
    }

    @PutMapping(value = "/v1/public/admin/books/{code}")
    public ResponseEntity<Object> update(@PathVariable("code") String code, @Valid @RequestBody ProjectDto dto,
                                         @RequestHeader("user-info") String userContext) {
        UserContext userContextObj = conversionUtils.convertStringToObject(userContext, UserContext.class);
        projectApplicationService.update(dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/public/admin/books")
    public ResponseEntity<Page<ProjectDto>> get(ProjectCriteria criteria, Pageable pageable) {
        return ResponseEntity.ok(projectApplicationService.getAllByCriteria(criteria, pageable));
    }

    @DeleteMapping(value = "/v1/public/admin/books/{code}")
    public ResponseEntity<Object> delete(@PathVariable("code") String code, @RequestHeader("user-info") String userContext) {
        UserContext userContextObj = conversionUtils.convertStringToObject(userContext, UserContext.class);
        projectApplicationService.delete(code, userContextObj.getEmailAddress());
        return ResponseEntity.noContent().build();
    }



}

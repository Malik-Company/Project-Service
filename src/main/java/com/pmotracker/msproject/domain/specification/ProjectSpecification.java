/*
 * ©2020 Nisum Technologies, Inc. All Rights Reserved.
 */
package com.pmotracker.msproject.domain.specification;
/*
 *  Created by IntelliJ IDEA
 *  User: Malik Imran (msabir@nisum.com)
 *  Date: 3/20/2020
 */

import com.pmotracker.msproject.application.interfaces.web.rest.v1.criteria.ProjectCriteria;
import com.pmotracker.msproject.domain.model.BaseModel_;
import com.pmotracker.msproject.domain.model.Project;
import com.pmotracker.msproject.domain.model.Project_;
import com.pmotracker.msproject.infrastructure.common.RecordStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class ProjectSpecification implements Specification<Project> {

    private final transient ProjectCriteria criteria;

    public ProjectSpecification(ProjectCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.equal(root.get(BaseModel_.recordStatus), RecordStatus.LIVE);

        if (criteria == null) {
            return predicate;
        }

        if (criteria.getUpdatedAt() > 0) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.greaterThan(root.get(BaseModel_.updatedAt), criteria.getUpdatedAt()));
        }

        if (criteria.getRecordStatus() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get(BaseModel_.recordStatus), criteria.getRecordStatus()));
        }

        if (StringUtils.isNotBlank(criteria.getName())) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(Project_.title)),
                            String.format("%%%s%%", criteria.getName().toLowerCase())));
        }

        if (criteria.getStatus() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get(Project_.status), criteria.getStatus()));
        }

        if (StringUtils.isNotBlank(criteria.getLocation())) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get(Project_.cityCode), criteria.getLocation()));
        }

        return predicate;
    }

}

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
import com.pmotracker.msproject.domain.model.Project;
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

//        Predicate predicate = criteriaBuilder.equal(root.get(BaseModel_.recordStatus), RecordStatus.LIVE);
//
//        if (criteria.getUpdatedAt() > 0) {
//            predicate = criteriaBuilder.greaterThan(root.get(BaseModel_.updatedAt), criteria.getUpdatedAt());
//        }
//
//        if (criteria.getRecordStatus() != null && criteria.getRecordStatus().toString().length() > 0) {
//            Predicate statusPredicate = criteriaBuilder.equal(root.get(BaseModel_.recordStatus), criteria.getRecordStatus());
//            predicate = criteriaBuilder.and(predicate, statusPredicate);
//        }
//
//        if (StringUtils.isNotBlank(criteria.getName())) {
//            Predicate namePredicate = criteriaBuilder.like(root.get(Project_.title), String.format("%%%s%%", criteria.getName()));
//            predicate = criteriaBuilder.and(predicate, namePredicate);
//        }
//
//        /*if (criteria.getCategoryCode() != null) {
//            Join<Deal, Category> categoryJoin = root.join(Project_.category);
//            Predicate categoryPredicate = criteriaBuilder.equal(categoryJoin.get(Category_.code), criteria.getCategoryCode());
//            predicate = criteriaBuilder.and(predicate, categoryPredicate);
//        }
//
//        if (criteria.getBrandCode() != null && criteria.getBrandCode().length() > 0) {
//            Predicate brandCodePredicate = criteriaBuilder.equal(root.get(Deal_.brandCode), criteria.getBrandCode());
//            predicate = criteriaBuilder.and(predicate, brandCodePredicate);
//        }*/
//        return predicate;

        return null;
    }

}

/*
 * ©2020 Nisum Technologies, Inc. All Rights Reserved.
 */

package com.pmotracker.msproject.application.converter;

/*
 *  Created by IntelliJ IDEA
 *  User: Malik Imran (msabir@nisum.com)
 *  Date: 3/20/2020
 */

import com.pmotracker.msproject.application.dto.BaseModelDto;
import com.pmotracker.msproject.domain.model.BaseModel;
import org.springframework.stereotype.Component;


@Component
public class AuditingModelToAuditingDtoConverter {

    public BaseModelDto convert(BaseModel auditingModel, BaseModelDto auditingDto) {

//        if(auditingModel.getCreatedBy()!= null) {
//            auditingDto.setCreatedUser(auditingModel.getCreatedBy());
//        }
//        if(auditingModel.getCreatedDate()> 0 ) {
//            auditingDto.setCreatedAt(auditingModel.getCreatedDate());
//        }
//        if(auditingModel.getLastModifiedBy()!= null) {
//            auditingDto.setLastModifiedUser(auditingModel.getLastModifiedBy());
//        }
//
//        if(auditingModel.getLastModifiedDate()> 0 ) {
//            auditingDto.setLastModifiedAt(auditingModel.getLastModifiedDate());
//        }

        return auditingDto;
    }
}
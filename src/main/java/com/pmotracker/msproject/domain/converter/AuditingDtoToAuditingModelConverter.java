/*
 * ©2020 Nisum Technologies, Inc. All Rights Reserved.
 */

package com.pmotracker.msproject.domain.converter;

/*
 *  Created by IntelliJ IDEA
 *  User: Malik Imran (msabir@nisum.com)
 *  Date: 3/20/2020
 */

import com.pmotracker.msproject.application.dto.BaseModelDto;
import com.pmotracker.msproject.domain.model.BaseModel;
import org.springframework.stereotype.Component;


@Component
public class AuditingDtoToAuditingModelConverter {

    public BaseModel convert(BaseModelDto auditingDto, BaseModel auditingModel) {

//        if(auditingDto.getCreatedUser()!= null) {
//            auditingModel.setCreatedBy(auditingDto.getCreatedUser());
//        }else{
//            auditingModel.setCreatedBy("system");
//        }
//        if(auditingDto.getCreatedAt()> 0) {
//            auditingModel.setCreatedDate(auditingDto.getCreatedAt());
//        }else{
//            auditingModel.setCreatedDate(System.currentTimeMillis());
//        }
//        if(auditingDto.getLastModifiedUser()!= null) {
//            auditingModel.setLastModifiedBy(auditingDto.getLastModifiedUser());
//        }else{
//            auditingModel.setLastModifiedBy("system");
//        }
//        if(auditingDto.getLastModifiedAt()> 0) {
//            auditingModel.setLastModifiedDate(auditingDto.getLastModifiedAt());
//        }else{
//            auditingModel.setLastModifiedDate(System.currentTimeMillis());
//        }

        return auditingModel;
    }
}
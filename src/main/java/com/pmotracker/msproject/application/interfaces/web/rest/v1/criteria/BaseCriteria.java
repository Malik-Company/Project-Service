/*
 * ©2020 Nisum Technologies, Inc. All Rights Reserved.
 */
package com.pmotracker.msproject.application.interfaces.web.rest.v1.criteria;

/*
 *  Created by IntelliJ IDEA
 *  User: Malik Imran (msabir@nisum.com)
 *  Date: 3/20/2020
 */

import com.pmotracker.msproject.infrastructure.common.RecordStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseCriteria {

    private RecordStatus recordStatus;

    private long createdAt;

    private long updatedAt;

    private String createdBy;

    private String updatedBy;

}

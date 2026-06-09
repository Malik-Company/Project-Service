/*
 * ©2020 Nisum Technologies, Inc. All Rights Reserved.
 */
package com.pmotracker.msproject.application.interfaces.web.rest.v1.criteria;

/*
 *  Created by IntelliJ IDEA
 *  User: Malik Imran (msabir@nisum.com)
 *  Date: 3/20/2020
 */

import com.pmotracker.msproject.infrastructure.common.ProjectStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectCriteria extends BaseCriteria {

    private String name;
    private String categoryCode;
    private String partnerCode;
    private String brandCode;
    private String location;
    private ProjectStatus status;
}

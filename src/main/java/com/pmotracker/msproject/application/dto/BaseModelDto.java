/*
 * ©2020 Nisum Technologies, Inc. All Rights Reserved.
 */

package com.pmotracker.msproject.application.dto;

/*
 *  Created by IntelliJ IDEA
 *  User: Malik Imran (msabir@nisum.com)
 *  Date: 3/20/2020
 */

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseModelDto {
    private Integer id;
    private String code;
    private boolean isEnabled;
    private String createdBy;
    private long createdAt;
    private String updatedBy;
    private long updatedAt;
}
/*
 * ©2020 Nisum Technologies, Inc. All Rights Reserved.
 */

package com.pmotracker.msproject.application.dto;

/*
 *  Created by IntelliJ IDEA
 *  User: Malik Imran (msabir@nisum.com)
 *  Date: 3/20/2020
 */

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class ProjectResponseDto {
    private String code;
    private String title;
    private String description;
    private long startDate;
    private long endDate;
    private double totalSaving;
    private int totalBrands;
    private int totalDeals;
    private String coverImageUrl;
    private String iconImageUrl;
    private int activePeriod;
    private String cityCode;
    private boolean showInApp;
}
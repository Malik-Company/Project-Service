/*
 * ©2020 Nisum Technologies, Inc. All Rights Reserved.
 */

package com.pmotracker.msproject.application.dto;

/*
 *  Created by IntelliJ IDEA
 *  User: Malik Imran (msabir@nisum.com)
 *  Date: 3/20/2020
 */

import com.pmotracker.msproject.domain.model.Project;
import com.pmotracker.msproject.infrastructure.common.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto extends BaseModelDto {
    private Project book;
    private String name;
    private String title;
    private ProjectStatus status;
    private String cityCode;
    private String description;
    private String eventName;
    private String punchline;
    private long startDate;
    private long endDate;
    private int activePeriod;
    private int maxPurchaseCount;
    private String buyNowUrl;
    private String coverImageUrl;
    private String iconImageUrl;
    private String imageUrls;
    private double price;
    private double discountedPrice;
    private boolean shareable;
    private double totalSaving;
    private int totalBrands;
    private int totalDeals;
    private boolean isPopular;
    private boolean showInApp;
}

/*
 * ©2020 Nisum Technologies, Inc. All Rights Reserved.
 */

package com.pmotracker.msproject.domain.model;

/*
 *  Created by IntelliJ IDEA
 *  User: Malik Imran (msabir@nisum.com)
 *  Date: 3/20/2020
 */

import com.pmotracker.msproject.infrastructure.common.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Project extends BaseModel {

    @Basic
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Basic
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjectStatus status = ProjectStatus.UNPUBLISHED;

    @Basic
    @Column(name = "city_code", nullable = false)
    private String cityCode;

    @Basic
    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Basic
    @Column(name = "event_name")
    private String eventName;

    @Basic
    @Column(name = "punch_line")
    private String punchline;

    @Basic
    @Column(name = "start_date")
    private long startDate;

    @Basic
    @Column(name = "end_date")
    private long endDate;

    @Basic
    @Column(name = "active_period")
    private int activePeriod;

    @Basic
    @Column(name = "max_purchase_count")
    private int maxPurchaseCount;

    @Basic
    @Column(name = "buy_now_url", length = 1024)
    private String buyNowUrl;

    @Basic
    @Column(name = "cover_image_url", length = 1024)
    private String coverImageUrl;

    @Basic
    @Column(name = "icon_image_url", length = 1024)
    private String iconImageUrl;

    @Basic
    @Column(name = "image_urls", columnDefinition = "text")
    private String imageUrls;

    @Basic
    @Column(name = "price", nullable = false)
    private double price;

    @Basic
    @Column(name = "discounted_price")
    private double discountedPrice;

    @Basic
    @Column(name = "is_shareable")
    private boolean isShareable;

    @Basic
    @Column(name = "total_saving")
    private double totalSaving;

    @Basic
    @Column(name = "total_brands")
    private int totalBrands;

    @Basic
    @Column(name = "total_deals")
    private int totalDeals;

    @Column(name = "show_in_app")
    private boolean showInApp;
}

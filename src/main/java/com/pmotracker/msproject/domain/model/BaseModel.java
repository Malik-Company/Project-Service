/*
 * ©2020 Nisum Technologies, Inc. All Rights Reserved.
 */

package com.pmotracker.msproject.domain.model;

/*
 *  Created by IntelliJ IDEA
 *  User: Malik Imran (msabir@nisum.com)
 *  Date: 3/20/2020
 */

import com.pmotracker.msproject.infrastructure.common.RecordStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
public class BaseModel implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    @Column(name = "code", nullable = false, length = 36, unique = true)
    private String code;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Basic
    @Column(name = "created_by")
    private String createdBy;

    @Basic
    @Column(name = "created_at")
    private long createdAt;

    @Basic
    @Column(name = "updated_by")
    private String updatedBy;

    @Basic
    @Column(name = "updated_at")
    private long updatedAt;

    @Enumerated(EnumType.STRING)
    @Basic
    @Column(name = "record_status", nullable = false)
    private RecordStatus recordStatus;

    @PrePersist
    private void prePersist() {
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        this.recordStatus = RecordStatus.LIVE;
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = System.currentTimeMillis();
    }

}

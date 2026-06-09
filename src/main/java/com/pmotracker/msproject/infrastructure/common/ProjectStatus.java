/*
 * ©2020 Nisum Technologies, Inc. All Rights Reserved.
 */
package com.pmotracker.msproject.infrastructure.common;

/*
 *  Created by IntelliJ IDEA
 *  User: Malik Imran (msabir@nisum.com)
 *  Date: 3/20/2020
 */
public enum ProjectStatus {
    PUBLISHED("PUBLISHED"),
    UNPUBLISHED("UNPUBLISHED"),
    PURCHASED("PURCHASED");


    private String name;

    ProjectStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}

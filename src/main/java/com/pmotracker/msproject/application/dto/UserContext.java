package com.pmotracker.msproject.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserContext {

    private String userId;

    private String emailAddress;

    private String fullName;

    private String firstName;

    private String lastName;

    private String userRole;

    private String userCapabilities;

}

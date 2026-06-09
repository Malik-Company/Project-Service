/*
 * ©2020 Nisum Technologies, Inc. All Rights Reserved.
 */
package com.pmotracker.msproject.infrastructure.exception;

/*
 *  Created by IntelliJ IDEA
 *  User: Malik Imran (msabir@nisum.com)
 *  Date: 3/20/2020
 */

import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by msabir on 3/28/2020.
 */
class Error {
    private final int status;
    private final String message;
    private List<FieldError> fieldErrors = new ArrayList<>();

    Error(int status, String message) {
        this.status = status;
        this.message = message;
    }

    Error(int status, String message, List<FieldError> fieldErrors) {
        this.status = status;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void addFieldError(String objectName, String field, String message) {
        FieldError error = new FieldError(objectName, field, message);
        fieldErrors.add(error);
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
}

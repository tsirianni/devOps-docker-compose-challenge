package org.courses.api.exceptions;

import lombok.Getter;

@Getter
public enum ErrorTypes {
    INVALID_CATEGORY("category.invalid");

    // Getter for the error message
    private final String errorMessage;

    // Constructor to initialize the error message
    ErrorTypes(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}


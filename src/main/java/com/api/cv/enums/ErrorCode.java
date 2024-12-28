package com.api.cv.enums;

public enum ErrorCode {
    A500("ERROR SYSTEM"),
    A400("BAD REQUEST"),
    A403("FORBIDDEN"),
    AF001("Offer not found"),
    AF002("Offer ALREADY EXIST"),
    AU001("USERNAME ALREADY EXISTS"),
    AU002("USER DOESN'T EXIST"),
    AU003("Role assignment failed"),
    AU004("Failed to retrieve user ID"),
    AK001("Error from keycloak"),
    AK002("User creation failed"),
    AK003("HTTP client error during user creation"),
    AK004("USERNAME ALREADY EXISTS in Keycloak"),
    AK005("EMAIL ALREADY EXISTS in Keycloak"),
    AK006("USERNAME OR PASSWORD INCORRECT"),
    AK007("Role Not found in keycloak"),
    AK008("error while getting Roles"),
    AR001("Role Not found");

    private final String value;

    ErrorCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

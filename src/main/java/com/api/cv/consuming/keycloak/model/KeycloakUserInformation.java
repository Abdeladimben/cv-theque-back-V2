package com.api.cv.consuming.keycloak.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class KeycloakUserInformation {
    private String id;
    private String username;
    private String email;
    private boolean emailVerified;
    private long createdTimestamp;
    private boolean enabled;
    private boolean totp;
    //private List<String> disableableCredentialTypes;
    //private List<String> requiredActions;
    private int notBefore;
    private KeycloakAccess access;

}

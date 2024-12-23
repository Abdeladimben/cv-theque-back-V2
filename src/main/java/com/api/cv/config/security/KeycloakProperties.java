package com.api.cv.config.security;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.keycloak")
public class KeycloakProperties {

    private String authServerUrl;
    private String realm;
    private String clientId;
    private String clientSecret;
    private String uriToken;
    private String uriUserInfo;
    private String uriUsers;
    private String uriUsersAssignRole;
    private String uriRoles;
    private String adminUser;
    private String adminPassword;
    private String clientUuid;
}
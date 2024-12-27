package com.api.cv.config;

import com.api.cv.consuming.keycloak.model.KeycloakRole;
import com.api.cv.consuming.keycloak.services.KeycloakService;
import com.api.cv.entities.Role;
import com.api.cv.exceptions.base_exception.ApiErrorException;
import com.api.cv.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


//@Profile("init")
@RequiredArgsConstructor
@Slf4j
@Component
public class DbInit {

    final private RoleRepository roleRepository;

    final private KeycloakService keycloakService;

    @PostConstruct
    private void postConstruct() throws ApiErrorException {
        if( roleRepository.count()==0 ) {
            log.info("************************************************************** START GET ROLES **************************************************************");
            getAllRoles();
            log.info("************************************************************** END GET ROLES **************************************************************");
        }
    }

    private void getAllRoles() throws ApiErrorException {

        List<KeycloakRole> keycloakRoles = keycloakService.getRoles();

        List<Role> roles = new ArrayList<>();

        for (KeycloakRole keycloakRole : keycloakRoles) {
            Role role =  new Role();
            role.setLabel(keycloakRole.getName());
            role.setIdKeycloak(keycloakRole.getId());
            role.setClientRole(keycloakRole.isClientRole());
            role.setComposite(keycloakRole.isComposite());
            role.setDescription(keycloakRole.getDescription());
            roles.add(role);
        }
        roleRepository.saveAll(roles);
    }
}

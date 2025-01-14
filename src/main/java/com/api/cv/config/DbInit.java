package com.api.cv.config;

import com.api.cv.consuming.keycloak.model.KeycloakRole;
import com.api.cv.consuming.keycloak.services.KeycloakService;
import com.api.cv.entities.Role;
import com.api.cv.entities.referentiel.ContractType;
import com.api.cv.entities.referentiel.OfferStatus;
import com.api.cv.exceptions.base_exception.ApiErrorException;
import com.api.cv.repositories.RoleRepository;
import com.api.cv.repositories.referentiel.ContractTypeRepository;
import com.api.cv.repositories.referentiel.OfferStatusRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


//@Profile("init")
@RequiredArgsConstructor
@Slf4j
@Component
public class DbInit {

    final private RoleRepository roleRepository;

    final private KeycloakService keycloakService;
    private final OfferStatusRepository offerStatusRepository;
    private final ContractTypeRepository contractTypeRepository;
    

    @PostConstruct
    private void postConstruct() throws ApiErrorException {
        if( roleRepository.count()==0 ) {
            log.info("************************************************************** START GET ROLES **************************************************************");
            saveRolesFromKeycloak();
            log.info("************************************************************** END GET ROLES **************************************************************");
        }
        
        if( offerStatusRepository.count()==0 ) {
            log.info("************************************************************** START GETTING STATUSES **************************************************************");
            saveOfferStatus();
            log.info("************************************************************** END GETTING STATUSES  **************************************************************");
        }
        
        if( contractTypeRepository.count()==0 ) {
            log.info("************************************************************** START GETTING CONTRACT TYPES **************************************************************");
            saveContractType();
            log.info("************************************************************** END GETTING CONTRACT TYPES **************************************************************");
        }
        
    }

    
    public void saveContractType() {
    	
    	List<ContractType> Types= new ArrayList<>();
    	
    	ContractType CDI=new ContractType();
    	
    	CDI.setCode("1");
    	CDI.setDelete(false);
    	CDI.setLibelle("CDI");
    	CDI.setDescription("CDI");
    	CDI.setUuid(UUID.randomUUID().toString());
    	Types.add(CDI);
    	
    	contractTypeRepository.saveAll(Types);  
          
          log.info("ContractTypes initialized successfully");
    }

    private void saveRolesFromKeycloak() throws ApiErrorException {

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

    private void saveOfferStatus() throws ApiErrorException {
    	
        List<OfferStatus> statuses = new ArrayList<>();
        
        // Create DRAFT status
        OfferStatus draft = new OfferStatus();
        draft.setCode("DRAFT");
        draft.setUuid(UUID.randomUUID().toString());
        draft.setLibelle("Draft");
        draft.setDelete(false);
        statuses.add(draft);
        
        // Create PUBLISHED status
        OfferStatus published = new OfferStatus();
        published.setCode("PUBLISHED");
        published.setUuid(UUID.randomUUID().toString());
        published.setLibelle("Published");
        published.setDelete(false);
        statuses.add(published);
        
        // Create CLOSED status
        OfferStatus closed = new OfferStatus();
        closed.setCode("CLOSED");
        closed.setUuid(UUID.randomUUID().toString());
        closed.setLibelle("Closed");
        closed.setDelete(false);
        statuses.add(closed);
        
        // Create ARCHIVED status
        OfferStatus archived = new OfferStatus();
        archived.setCode("ARCHIVED");
        archived.setUuid(UUID.randomUUID().toString());
        archived.setLibelle("Archived");
        archived.setDelete(false);
        statuses.add(archived);
        offerStatusRepository.saveAll(statuses);  
        
        log.info("Offer statuses initialized successfully");
    

    }
}

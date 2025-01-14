package com.api.cv.services.offer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.cv.dto.offer.ApplyDto;
import com.api.cv.entities.User;
import com.api.cv.entities.offer.Offer;
import com.api.cv.entities.referentiel.Apply;
import com.api.cv.enums.ErrorCode;
import com.api.cv.exceptions.RessourceDbNotFoundException;
import com.api.cv.exceptions.base_exception.ApiErrorException;

import com.api.cv.repositories.UserRepository;
import com.api.cv.repositories.offer.OfferRepository;
import com.api.cv.repositories.referentiel.ApplyRepository;
import com.api.cv.services.user.UserService;

import lombok.RequiredArgsConstructor;

@Service  
@RequiredArgsConstructor
public class ApplyService implements IApplyService {

    private final OfferRepository offerRepository;
    private final ApplyRepository applyRepository;
    private final UserRepository userRepository;
    
    private final UserService userService; 

    
    protected User getUserFromService() throws ApiErrorException {
        return userService.getUserConnected();
    }

    
    @Override
    public void Apply(ApplyDto applyDto) throws RessourceDbNotFoundException, ApiErrorException {
        Offer offer = offerRepository.findByUuid(applyDto.getOfferUuid())
                .orElseThrow(() -> new RessourceDbNotFoundException(ErrorCode.A400));
        //Optional<User> userOpt = userRepository.findByUserName(getUserFromService().getUserName());
       // User user = userOpt.orElseThrow(() -> new ApiErrorException(ErrorCode.AU002));
        Apply apply = new Apply();
        apply.setUser(getUserFromService());
        apply.setOffer(offer);
        apply.setStatus("Pending"); 
        applyRepository.save(apply);
    }
}

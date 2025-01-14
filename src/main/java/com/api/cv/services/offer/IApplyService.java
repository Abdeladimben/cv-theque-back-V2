package com.api.cv.services.offer;

import com.api.cv.dto.offer.ApplyDto;
import com.api.cv.exceptions.RessourceDbNotFoundException;
import com.api.cv.exceptions.base_exception.ApiErrorException;

public interface IApplyService {

	void Apply(ApplyDto applyDto) throws RessourceDbNotFoundException, ApiErrorException;
}

package com.api.cv.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.cv.dto.auth.RegisterRequestDto;
import com.api.cv.dto.offer.ApplyDto;
import com.api.cv.exceptions.base_exception.ApiErrorException;
import com.api.cv.services.offer.IApplyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${endpoint.prefix}Apply")
public class ApplyController {
	
	private final IApplyService ApplyService;
	
	@Operation(summary="Apply endpoint",description="User apply to an offer")
	@ApiResponse(responseCode = "200",description="User Applied successfully")
	@ApiResponse(responseCode = "400", description = "Invalid infos ")
	@ApiResponse(responseCode = "500", description = "Internal server error")
	@PostMapping()
    public ResponseEntity<Void> Apply(@RequestBody ApplyDto applyDto) throws ApiErrorException {
		ApplyService.Apply(applyDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

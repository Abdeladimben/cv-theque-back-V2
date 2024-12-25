package com.api.cv.controllers;

import java.util.List;

import com.api.cv.exceptions.RessourceDbNotFoundException;
import com.api.cv.exceptions.UserNotConnectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.cv.dto.offer.OfferRequestDto;
import com.api.cv.dto.offer.OfferResponseDto;
import com.api.cv.dto.offer.OfferUpdateRequestDto;
import com.api.cv.exceptions.base_exception.ApiErrorException;
import com.api.cv.services.offer.IOfferService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${endpoint.prefix}offers")
@Tag(name = "Offer Controller", description = "APIs - GET ALL | SEARCH | GET BY UUID | CREATE | UPDATE | DELETE")
public class OfferController {

    private final IOfferService offerService;

    private final Logger LOG = LoggerFactory.getLogger(OfferController.class);

    public OfferController(IOfferService offerService) {
        this.offerService = offerService;
    }

    @Operation(summary = "Get all offers endpoint", description = "Get all offers exist")
    @ApiResponse(responseCode = "200", description = "offers retrieved successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping()
    public ResponseEntity<List<OfferResponseDto>> getAll() throws ApiErrorException {
        LOG.debug("Request to GET ALL offers");
        return ResponseEntity.ok(offerService.getAll());
    }

    @Operation(summary = "Get offer by UUID endpoint", description = "Get offer by UUID")
    @ApiResponse(responseCode = "200", description = "offer retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("{uuid}")
    public ResponseEntity<OfferResponseDto> getByUuid(@PathVariable String uuid) throws RessourceDbNotFoundException {
        LOG.debug("Request to GET offer by UUID: {}", uuid);
        return ResponseEntity.ok(offerService.getByUuid(uuid));
    }

    @Operation(summary = "Create a new offer endpoint", description = "Create a new offer with provided information")
    @ApiResponse(responseCode = "201", description = "Offer created successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping()
    public ResponseEntity<OfferResponseDto> create(@RequestBody OfferRequestDto offerDto) throws ApiErrorException, RessourceDbNotFoundException {
        LOG.debug("Request to CREATE offer: {}", offerDto);
        OfferResponseDto createdOffer = offerService.create(offerDto);
        return ResponseEntity.status(201).body(createdOffer);
    }

    @Operation(summary = "Update offer endpoint", description = "Update an offer with provided information")
    @ApiResponse(responseCode = "200", description = "Offer updated successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PutMapping()
    public ResponseEntity<OfferResponseDto> update(@RequestBody OfferUpdateRequestDto offerUpdateRequestDto) throws ApiErrorException, UserNotConnectedException, RessourceDbNotFoundException {
        LOG.debug("Request to UPDATE offer: {}", offerUpdateRequestDto);
        OfferResponseDto updatedOffer = offerService.update(offerUpdateRequestDto);
        return ResponseEntity.ok(updatedOffer);
    }

    @Operation(summary = "Delete offer endpoint", description = "Delete an offer by UUID")
    @ApiResponse(responseCode = "204", description = "Offer deleted successfully")
    @ApiResponse(responseCode = "400", description = "Invalid UUID")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @DeleteMapping("{uuid}")
    public ResponseEntity<Void> delete(@PathVariable String uuid) throws RessourceDbNotFoundException {
        LOG.debug("Request to DELETE offer by UUID: {}", uuid);
        offerService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}

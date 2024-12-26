package com.api.cv.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.cv.dto.PaginationResultDto;
import com.api.cv.dto.offer.OfferRequestDto;
import com.api.cv.dto.offer.OfferResponseDto;
import com.api.cv.dto.offer.OfferSearchRequestDto;
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

    
    /*
    @GetMapping("/offers")
    public Page<OfferResponseDto> getFilteredOffers(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String ville,
            @RequestParam(required = false) Double remuneration,
            @RequestParam(required = false) Integer dureeContrat,
            Pageable pageable
    ) {
        return offerService.getFilteredOffers(title, ville, remuneration, dureeContrat, pageable);
    }
    
    */
    
    
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
    public ResponseEntity<OfferResponseDto> getByUuid(@PathVariable String uuid) throws ApiErrorException {
   
        return ResponseEntity.ok(offerService.getByUuid(uuid));
    }

    @Operation(summary = "Create a new offer endpoint", description = "Create a new offer with provided information")
    @ApiResponse(responseCode = "201", description = "Offer created successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping()
    public ResponseEntity<OfferResponseDto> create(@RequestBody OfferRequestDto offerDto) throws ApiErrorException {
       
        OfferResponseDto createdOffer = offerService.create(offerDto);
        return ResponseEntity.status(201).body(createdOffer);
    }	

    @Operation(summary = "Update offer endpoint", description = "Update an offer with provided information")
    @ApiResponse(responseCode = "200", description = "Offer updated successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PutMapping()
    public ResponseEntity<OfferResponseDto> update(@RequestBody OfferUpdateRequestDto offerUpdateRequestDto) throws ApiErrorException {
       
        OfferResponseDto updatedOffer = offerService.update(offerUpdateRequestDto);
        return ResponseEntity.ok(updatedOffer);
    }

    @Operation(summary = "Delete offer endpoint", description = "Delete an offer by UUID")
    @ApiResponse(responseCode = "204", description = "Offer deleted successfully")
    @ApiResponse(responseCode = "400", description = "Invalid UUID")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @DeleteMapping("{uuid}")
    public ResponseEntity<Void> delete(@PathVariable String uuid) throws ApiErrorException {
        LOG.debug("Request to DELETE offer by UUID: {}", uuid);
        offerService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/all/")
    public ResponseEntity<PaginationResultDto<OfferResponseDto>> searchOffers(
            @ModelAttribute OfferSearchRequestDto offerSearchRequestDto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // Call the service to fetch paginated results
        PaginationResultDto<OfferResponseDto> response = offerService.searchOffers(offerSearchRequestDto, page, size);

        // Return the response entity
        return ResponseEntity.ok(response);
    }
    
    
    
}

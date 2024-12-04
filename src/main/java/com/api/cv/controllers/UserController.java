package com.api.cv.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.cv.dto.user.UserKeycloakInfoResponse;
import com.api.cv.services.user.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @Operation(
            summary = "Get information about the authenticated user",
            description = "Returns details such as user ID, username, email, and roles of the connected user.",
            security = @SecurityRequirement(name = "bearerAuth")
        )
    
    
    
    
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", 
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = UserKeycloakInfoResponse.class)
                        )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        })
       
    
    @GetMapping("/info")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<UserKeycloakInfoResponse> getUserInfo() {
        UserKeycloakInfoResponse userInfoResponse = userService.getUserInfo();
        return ResponseEntity.ok(userInfoResponse);
    }
}

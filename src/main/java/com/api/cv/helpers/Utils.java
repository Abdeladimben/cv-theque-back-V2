package com.api.cv.helpers;

import com.api.cv.enums.ErrorCode;
import com.api.cv.exceptions.base_exception.ApiErrorException;
import com.api.cv.exceptions.base_exception.ConflitException;
import com.api.cv.exceptions.base_exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.UUID;

public class Utils {
	
    public static  String getHashedUuid( LocalDateTime dateCreation,  Long id) {
        UUID uuid = UUID.randomUUID();
        String hashString = uuid + dateCreation.toString() + id.toString();
        byte[] hashBytes = sha256(hashString.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashBytes);
    }
    
    private static byte[] sha256(byte[] input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(input);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String bytesToHex(byte  [] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }


    public static void manageKeycloakErrorWithException(HttpClientErrorException e) throws ApiErrorException {
        if (HttpStatus.CONFLICT.value() == e.getStatusCode().value()) {
            if (e.getResponseBodyAsString().contains("User exists with same username")) {
                throw new ConflitException(ErrorCode.AK004);
            } else if (e.getResponseBodyAsString().contains("User exists with same email")){
                throw new ConflitException(ErrorCode.AK005);
            } else {
                throw new ApiErrorException(ErrorCode.AK001);
            }
        } else if (HttpStatus.UNAUTHORIZED.value() == e.getStatusCode().value()) {
            if (e.getResponseBodyAsString().contains("Invalid user credentials")) {
                throw new ApiErrorException(ErrorCode.AK006);
            } else {
                throw new ApiErrorException(ErrorCode.AK001);
            }
        } else if (HttpStatus.NOT_FOUND.value() == e.getStatusCode().value()) {
            if (e.getResponseBodyAsString().contains("Role not found")) {
                throw new NotFoundException(ErrorCode.AK007);
            } else {
                throw new ApiErrorException(ErrorCode.AK001);
            }
        } else {
            throw new ApiErrorException(ErrorCode.AK001);
        }
    }

    public static void manageKeycloakErrorWithResponseError(ResponseEntity<?> response) throws ApiErrorException {
        if (HttpStatus.CONFLICT.equals(response.getStatusCode())) {
            if(response.getBody()==null)
                throw new ApiErrorException(ErrorCode.AK001);
            String responseBody = response.getBody().toString();
            if (responseBody.contains("User exists with same username")) {
                throw new ConflitException(ErrorCode.AK004);
            } else if (responseBody.contains("User exists with same email")) {
                throw new ConflitException(ErrorCode.AK005);
            } else {
                throw new ApiErrorException(ErrorCode.AK001);
            }
        } else if (HttpStatus.UNAUTHORIZED.equals(response.getStatusCode())) {
            if(response.getBody()==null)
                throw new ApiErrorException(ErrorCode.AK001);
            String responseBody = response.getBody().toString();
            if (responseBody.contains("Invalid user credentials")) {
                throw new ApiErrorException(ErrorCode.AK006);
            } else {
                throw new ApiErrorException(ErrorCode.AK001);
            }
        } else if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ApiErrorException(ErrorCode.AK001);
        }
    }

}

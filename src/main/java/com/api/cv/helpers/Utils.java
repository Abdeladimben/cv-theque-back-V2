package com.api.cv.helpers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.api.cv.enums.ErrorCode;
import com.api.cv.exceptions.base_exception.ApiErrorException;
import com.api.cv.exceptions.base_exception.ConflitException;
import com.api.cv.exceptions.base_exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

/**
 * A utility class providing several helper methods, including:
 * <ul>
 *   <li>Hash generation using SHA-256</li>
 *   <li>Exception handling for Keycloak-related errors</li>
 *   <li>String validation methods for null and emptiness</li>
 * </ul>
 */
public class Utils {

    /**
     * Generates a hashed UUID based on the current timestamp and a unique ID.
     *
     * @param dateCreation The creation date as an {@link Instant}.
     * @param id The unique ID (e.g., database primary key).
     * @return A hex string representation of the hashed data.
     */
    public static String getHashedUuid(Instant dateCreation, Long id) {
        UUID uuid = UUID.randomUUID(); // Generate a random UUID
        String hashString = uuid + dateCreation.toString() + id; // Combine UUID, timestamp, and ID
        byte[] hashBytes = sha256(hashString.getBytes(StandardCharsets.UTF_8)); // Hash the combined string
        return bytesToHex(hashBytes); // Convert bytes to hex representation
    }

    /**
     * Performs SHA-256 hashing on the given byte array.
     *
     * @param input The byte array to hash.
     * @return A byte array containing the SHA-256 hashed result.
     * @throws IllegalStateException If the SHA-256 algorithm is not available.
     */
    private static byte[] sha256(byte[] input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(input);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm is not available", e);
        }
    }

    /**
     * Converts an array of bytes into a hexadecimal string.
     *
     * @param bytes The byte array to convert.
     * @return A hexadecimal string representing the given byte array.
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            result.append(Character.forDigit((b >> 4) & 0xF, 16)) // Convert the higher 4 bits
                    .append(Character.forDigit(b & 0xF, 16));       // Convert the lower 4 bits
        }
        return result.toString();
    }

    /**
     * Handles Keycloak errors thrown as {@link HttpClientErrorException}.
     *
     * @param e The HTTP client exception to handle.
     * @throws ApiErrorException A custom exception with appropriate error codes.
     */
    public static void manageKeycloakErrorWithException(HttpClientErrorException e) throws ApiErrorException {
        String responseBody = e.getResponseBodyAsString(); // Extract response body
        if (responseBody == null) {
            throw new ApiErrorException(ErrorCode.AK001); // If no response body, throw a default error
        }

        HttpStatusCode status = e.getStatusCode();
        if (HttpStatus.CONFLICT.equals(status)) {
            if (responseBody.contains("User exists with same username")) {
                throw new ConflitException(ErrorCode.AK004);
            }
            if (responseBody.contains("User exists with same email")) {
                throw new ConflitException(ErrorCode.AK005);
            }
            throw new ApiErrorException(ErrorCode.AK001);
        } else if (HttpStatus.UNAUTHORIZED.equals(status)) {
            if (responseBody.contains("Invalid user credentials")) {
                throw new ApiErrorException(ErrorCode.AK006);
            }
            throw new ApiErrorException(ErrorCode.AK001);
        } else if (HttpStatus.NOT_FOUND.equals(status)) {
            if (responseBody.contains("Role not found")) {
                throw new NotFoundException(ErrorCode.AK007);
            }
            throw new ApiErrorException(ErrorCode.AK001);
        }
        throw new ApiErrorException(ErrorCode.AK001); // Default error for other statuses
    }

    /**
     * Handles response errors from Keycloak and maps them to custom exceptions.
     *
     * @param response The HTTP response from Keycloak containing potential errors.
     * @throws ApiErrorException A custom exception with appropriate error codes.
     */
    public static void manageKeycloakErrorWithResponseError(ResponseEntity<?> response) throws ApiErrorException {
        if (response == null || response.getStatusCode() == null) {
            throw new ApiErrorException(ErrorCode.AK001); // Throw default error if response or status is null
        }

        HttpStatusCode status = response.getStatusCode();
        Object responseBodyObj = response.getBody();
        String responseBody = responseBodyObj != null ? responseBodyObj.toString() : null;

        if (HttpStatus.CONFLICT.equals(status)) {
            if (responseBody != null && responseBody.contains("User exists with same username")) {
                throw new ConflitException(ErrorCode.AK004);
            } else if (responseBody != null && responseBody.contains("User exists with same email")) {
                throw new ConflitException(ErrorCode.AK005);
            } else {
                throw new ApiErrorException(ErrorCode.AK001);
            }
        } else if (HttpStatus.UNAUTHORIZED.equals(status)) {
            if (responseBody != null && responseBody.contains("Invalid user credentials")) {
                throw new ApiErrorException(ErrorCode.AK006);
            } else {
                throw new ApiErrorException(ErrorCode.AK001);
            }
        } else if (!status.is2xxSuccessful()) {
            throw new ApiErrorException(ErrorCode.AK001); // Throw default error for non-2xx status
        }
    }

    /**
     * Checks if the given string is not null and not empty.
     *
     * @param string The string to check.
     * @return {@code true} if the string is neither null nor empty; {@code false} otherwise.
     */
    public static boolean isNotNullAndNotEmpty(String string) {
        return !isNullAndEmptyString(string); // Negates the null or empty check
    }

    /**
     * Checks if a given string is null or empty.
     *
     * @param string The string to be checked.
     * @return {@code true} if the string is null or empty; {@code false} otherwise.
     */
    public static boolean isNullAndEmptyString(String string) {
        return string == null || string.isEmpty();
    }

    /**
     * Checks if the given string is null.
     *
     * @param string The string to check.
     * @return {@code true} if the string is null; {@code false} otherwise.
     */
    private static boolean isNull(String string) {
        return string == null;
    }

    /**
     * Checks if the given string is empty.
     *
     * @param string The string to check.
     * @return {@code true} if the string is empty (or null since handled earlier); {@code false} otherwise.
     */
    private static boolean isEmpty(String string) {
        return string == null || string.isEmpty(); // Updated for null safety
    }


    /**
     * Checks if the given list is not null and not empty.
     *
     * @param list The list to check.
     * @return {@code true} if the list is neither null nor empty; {@code false} otherwise.
     */
    public static boolean isNotNullAndNotEmpty(List<?> list) {
        return !isNullOrEmptyList(list); // Negates the null or empty check
    }

    /**
     * Checks if a given list is null or empty.
     *
     * @param list The list to be checked.
     * @return {@code true} if the list is null or empty; {@code false} otherwise.
     */
    public static boolean isNullOrEmptyList(List<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * Checks if the given list is null.
     *
     * @param list The list to check.
     * @return {@code true} if the list is null; {@code false} otherwise.
     */
    private static boolean isNull(List<?> list) {
        return list == null;
    }

    /**
     * Checks if the given list is empty.
     *
     * @param list The list to check.
     * @return {@code true} if the list is empty (or null since handled earlier); {@code false} otherwise.
     */
    private static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty(); // Updated for null safety
    }


}
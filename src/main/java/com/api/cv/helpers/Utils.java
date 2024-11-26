package com.api.cv.helpers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.UUID;

public class Utils {
	
    public static  String getHashedUuid( LocalDateTime dateCreation,  Long id) {
        UUID uuid = UUID.randomUUID();
        String hashString = uuid.toString() + dateCreation.toString() + id.toString();
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

}

package org.semicolon.semicolonartworksystem.utils;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class SecretKeyGenerator {
    public static void main(String... args) {
        byte[] secretKeyBytes = new byte[32];
        new SecureRandom().nextBytes(secretKeyBytes);
        System.out.println(Arrays.toString(secretKeyBytes));
        String secretKey = Base64.getEncoder().encodeToString(secretKeyBytes);
        System.out.print(secretKey);

        // UUID uuid = UUID.randomUUID();
        // String uuidString = uuid.toString();
        // System.out.print(uuidString);

    }
}

package org.dinote.service.password;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Slf4j
@Component
public class PasswordHasher {

    private static final int ITERATIONS = 1000;
    private static final int HASH_WIDTH = 128;
    private static final String PEPPER = "209d3u9=-0d2!!d23290d-2da";

    private final MessageDigest md;

    public PasswordHasher() {
        try {
            this.md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.error("Should never happen: ", e);
            throw new IllegalStateException(e);
        }
    }

    public String hash(@NotNull final String password) {
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), PEPPER.getBytes(), ITERATIONS, HASH_WIDTH);
            SecretKey secretKey = secretKeyFactory.generateSecret(spec);
            return Hex.encodeHexString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("Error while hashing password: ", e);
            throw new IllegalStateException(e);
        }
    }

    public String hashWithMd5(@NotNull final String password) {
        md.update(password.getBytes());
        return Hex.encodeHexString(md.digest());
    }
}

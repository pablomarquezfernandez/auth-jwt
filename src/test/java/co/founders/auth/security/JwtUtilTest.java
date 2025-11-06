package co.founders.auth.security;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {
    
    private final JwtUtil jwtUtil = new JwtUtil();

    @Test
    void testGenerateAndValidateToken() {
        String email = "test@example.com";
        String role = "ADMIN";
        String token = jwtUtil.generateToken(email, role);
        assertNotNull(token);
        assertTrue(jwtUtil.validateToken(token));
        assertEquals(email, jwtUtil.extractEmail(token));
        assertEquals(role, jwtUtil.extractRole(token));
    }

    @Test
    void testInvalidToken() {
        String invalidToken = "invalid.token.value";
        assertFalse(jwtUtil.validateToken(invalidToken));
    }


    @Test
    void testRenewToken() {
        String email = "test@example.com";
        String role = "USER";
        String originalToken = jwtUtil.generateToken(email, role);

        // Mock Authentication principal
        org.springframework.security.core.Authentication authentication = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(originalToken, null);

        co.founders.auth.dto.AuthorizedDTO authorized = null;

        try {
            authorized = jwtUtil.renewToken(authentication);
        } catch (Exception e) {
        }

        assertNotNull(authorized);

        assertNotNull(authorized);
        assertNotNull(authorized.getToken());
        assertTrue(jwtUtil.validateToken(authorized.getToken()));
        assertEquals(email, jwtUtil.extractEmail(authorized.getToken()));
        assertEquals(role, jwtUtil.extractRole(authorized.getToken()));
        assertNotEquals(originalToken, authorized.getToken());
    }

    @Test
    void testRenewInvalidToken() {
        String invalidToken = "invalid.token.value";
        org.springframework.security.core.Authentication authentication = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(invalidToken, null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            jwtUtil.renewToken(authentication);
        });
        assertEquals("Token inválido o expirado", exception.getMessage());
    }

    @Test
    void testHashMD5() {
        String testData = "test-data";
        String hash = jwtUtil.hashMD5(testData);

        // MD5 siempre produce el mismo resultado para los mismos datos
        assertNotNull(hash);
        assertEquals(32, hash.length()); // MD5 produce 32 caracteres hexadecimales
        assertEquals("eb733a00c0c9d336e65691a37ab54293", hash); // Hash esperado para "test-data"
    }

    @Test
    void testHashSHA1() {
        String testData = "test-data";
        String hash = jwtUtil.hashSHA1(testData);

        // SHA-1 siempre produce el mismo resultado para los mismos datos
        assertNotNull(hash);
        assertEquals(40, hash.length()); // SHA-1 produce 40 caracteres hexadecimales
        assertEquals("f48dd853820860816c75d54d0f584dc863327a7c", hash); // Hash esperado para "test-data"
    }

    @Test
    void testHashSHA256() {
        String testData = "test-data";
        String hash = jwtUtil.hashSHA256(testData);

        // SHA-256 siempre produce el mismo resultado para los mismos datos
        assertNotNull(hash);
        assertEquals(64, hash.length()); // SHA-256 produce 64 caracteres hexadecimales
        assertEquals("6ae8a75555209fd6c44157c0aed8016e763ff435a19cf186f23f367d8eacf0f547", hash); // Hash esperado para "test-data"
    }

    @Test
    void testHashConsistency() {
        String testData = "consistent-test";

        // Los hashes deben ser consistentes para los mismos datos
        String md5_1 = jwtUtil.hashMD5(testData);
        String md5_2 = jwtUtil.hashMD5(testData);
        assertEquals(md5_1, md5_2);

        String sha1_1 = jwtUtil.hashSHA1(testData);
        String sha1_2 = jwtUtil.hashSHA1(testData);
        assertEquals(sha1_1, sha1_2);

        String sha256_1 = jwtUtil.hashSHA256(testData);
        String sha256_2 = jwtUtil.hashSHA256(testData);
        assertEquals(sha256_1, sha256_2);
    }
}

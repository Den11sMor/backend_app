package com.mastermartini.backend.service;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtServiceTest {
    @Test void generaToken() throws Exception {
        JwtEncoder encoder = mock(JwtEncoder.class);
        Jwt jwt = Jwt.withTokenValue("jwt-test")
                .header("alg", "HS256")
                .subject("ana@test.com")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60))
                .build();
        when(encoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwt);

        JwtService service = new JwtService(encoder);
        Field expiration = JwtService.class.getDeclaredField("jwtExpiration");
        expiration.setAccessible(true);
        expiration.set(service, 86400000L);

        assertEquals("jwt-test", service.generarToken("ana@test.com"));
    }
}

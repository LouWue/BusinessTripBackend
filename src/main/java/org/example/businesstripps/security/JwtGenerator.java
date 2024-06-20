package org.example.businesstripps.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.example.businesstripps.security.constants.SecurityConstants;

import java.util.Date;

public final class JwtGenerator {

    /**
     * Generates a JWT token for the given username.
     *
     * @param username the username for which to generate the JWT token
     * @return the generated JWT token
     */
    public static String generateJwtToken(String username) {
        try {
            SignedJWT jwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), buildJWTClaimsSet(username));
            jwt.sign(new MACSigner(SecurityConstants.SECRET_KEY_SPEC));
            return jwt.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Builds a JWTClaimsSet object for the given username.
     *
     * @param username the username for which to build the JWTClaimsSet
     * @return the constructed JWTClaimsSet object
     */
    private static JWTClaimsSet buildJWTClaimsSet(String username) {
        return new JWTClaimsSet.Builder()
                .subject(username)
                .expirationTime(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .build();
    }

}


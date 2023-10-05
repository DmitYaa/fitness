package kz.danilov.backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {
    @Value("${jwt_secret}")
    private String secret;
    private static final int TOKEN_TIME = 30;
    private static final int TIME_DIFFERENCE_IN_SECONDS = 900;
    private static final String SUBJECT = "User details";
    private static final String ISSUER = "fitness";
    private static final String USERNAME = "username";
    private static final String EXPIRATION_DATE = "exp";
    private static final String ISSUED_DATE = "iat";

    public String generateToken(String username) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(TOKEN_TIME).toInstant());

        return JWT.create()
                .withSubject(SUBJECT)
                .withClaim(USERNAME, username)
                .withIssuedAt(new Date())
                .withIssuer(ISSUER)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndGetNewTokenIfNeeded(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject(SUBJECT)
                .withIssuer(ISSUER)
                .build();

        DecodedJWT jwt = verifier.verify(token);

        long exp = jwt.getClaim(EXPIRATION_DATE).asLong();
        long now = System.currentTimeMillis() / 1000;

        if (now + TIME_DIFFERENCE_IN_SECONDS > exp & now < exp)
            return generateToken(jwt.getClaim(USERNAME).asString());
        return token;
    }

    public boolean checkToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject(SUBJECT)
                .withIssuer(ISSUER)
                .build();

        try {
            DecodedJWT jwt = verifier.verify(token);
            long exp = jwt.getClaim(EXPIRATION_DATE).asLong();
            long now = System.currentTimeMillis() / 1000;
            return now < exp;
        } catch (JWTVerificationException | IllegalArgumentException e) {
            return false;
        }
    }

    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject(SUBJECT)
                .withIssuer(ISSUER)
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim(USERNAME).asString();
    }
}

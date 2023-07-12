package otb.project.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import otb.project.entity.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    private static final String SECRET_KEY = "aaaabbbbccccddddaaaabbbbccccddddaaaabbbbccccddddaaaabbbbccccdddd";

    public String create(User entity) {
        Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

        return Jwts.builder()
                .signWith(SignatureAlgorithm.ES512, SECRET_KEY)
                .setSubject(entity.getUsername())
                .setIssuer("OTB app provider")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }
    public String validateAndGetUserId(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}

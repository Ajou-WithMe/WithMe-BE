package ajou.withme.main.Service;

import ajou.withme.main.Repository.AuthRepository;
import ajou.withme.main.domain.Auth;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    @Value("${KEY.JWT}")
    private String SECRETKEY;

    public String createToken(String subject, Long expTime) {
        if (expTime <= 0) {
            throw new RuntimeException("만료시간이 0보다 작습니다.");
        }
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRETKEY);

        // key 생성
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setSubject(subject)
                .signWith(signatureAlgorithm, signingKey)
                .setExpiration(new Date(System.currentTimeMillis() + expTime))
                .compact();
    }

    public Auth saveAuth(Auth auth) {
        return authRepository.save(auth);
    }

    public String getSubject(Claims claims) {
        return claims.getSubject();
    }

    public Claims getClaimsByToken(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRETKEY))
                .parseClaimsJwt(token)
                .getBody();
    }

    public boolean isValidToken(String token) {
        try {
            getClaimsByToken(token);
            return true;
        } catch (JwtException exception) {
            // refresh
            return false;
        } catch (NullPointerException exception) {
            return false;
        }
    }
}

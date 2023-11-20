package project.eventregistration.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static project.eventregistration.Constant.SecurityConstant.lifetime;
import static project.eventregistration.Constant.SecurityConstant.secret;

@Component
public class JwtTokenUtils {


    public String generateToken(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + lifetime);

        String role = "";

        for (GrantedAuthority authority : user.getAuthorities()) {
            role = authority.toString();
        }

        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public String getRole(String token) {
        return getAllClaimsFromToken(token).get("role", String.class);
    }


    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

}

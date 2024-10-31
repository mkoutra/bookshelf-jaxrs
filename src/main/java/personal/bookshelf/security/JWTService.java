package personal.bookshelf.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.context.ApplicationScoped;
import personal.bookshelf.model.User;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@ApplicationScoped
public class JWTService {

    // In a real project it must be an environmental variable
    private final String SECRET_KEY = "5d9669eed4249531da7d7536888c6620807686446937d87a7ff2958bab64f19b";
    private final long JWT_EXPRITATION = 10800000;  // 3 hours in milliseconds

    public String generateToken(String username, String role) {
        var claims = new HashMap<String, Object>();
        claims.put("role", role);
        return Jwts
                .builder()
                .setIssuer("mk")
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPRITATION))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, User user) {
        final String subject = extractSubject(token);
        return (subject.equals(user.getName())) && !isTokenExpired(token);
    }

    // Here we mean extract username
    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Creates a HS256 Key. Key is an interface.
     * Starting from secretKey we get a byte array
     * of the secret. Then we get the {@link javax.crypto.SecretKey},
     * class that implements the {@link Key } interface.
     *
     * @return  a SecretKey which implements Key.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

package QL_Chua.Security.jwt;

import QL_Chua.Security.services.UserDetailImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.hibernate.annotations.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${bezkoder.app.jwtSecret}")
    private String jwtSecret;

    @Value("${bezkoder.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    @Value("604800000")
    private long refreshExpirations;

    //    tạo mã token xác thực qua việc mã hóa email
    public String generateJwtToken(Authentication authentication) {
        return buildToken(authentication, jwtExpirationMs);
    }

    public String generateRefreshToken(Authentication authentication) {  //tạo mã refreshToken
        return buildToken(authentication, refreshExpirations);
    }

    public String buildToken(Authentication authentication, long expiration) {
        UserDetailImpl userPrincipal = (UserDetailImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((userPrincipal).getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expiration))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }


    //  mã hóa chuỗi và giải mã cho token
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }


    //trích xuất tên người dùng đã đựược ký và mã hóa
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }


    //    xác thực tính hợp lệ của mã token truyền vào
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("mã token không hợp lệ: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("mã token đã hết hạn: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("mã token không được hỗ trợ: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("chuỗi yêu cầu token trống: {}", e.getMessage());
        }

        return false;
    }

//    public String extractUsername(String token) {  //trích xuất tên người dùng từ chuỗi token
//        return extractClaim(token, Claims::getSubject); //
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { // trích xuất thông tin jwt được truyển dưới dạng token
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//    public boolean isTokenValid(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
//    }
//
//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(key())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
}

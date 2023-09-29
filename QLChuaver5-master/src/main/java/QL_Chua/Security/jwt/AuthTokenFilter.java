package QL_Chua.Security.jwt;

import QL_Chua.Security.services.UserDetailServiceImpl;
import QL_Chua.Security.token.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailServiceImpl userDetailsService;
    @Autowired
    private TokenRepository tokenrepo;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
    @Override
//   xác thực và phân quyền truy cập tài khoản dựa vào chuỗi token
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = parseJwt(request);  //truyền chuỗi token trong phần header postman để check tính hơp lệ
            if (token != null && jwtUtils.validateJwtToken(token) && checkToken(token)==true) {
                String email = jwtUtils.getUserNameFromJwtToken(token);

                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authentication = // phương thúc tạo ra 1 authentication mới chứa thông tin người dùng và quyền truy cập tài nguyên
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());  //.getAuthorities() danh sách quền hạn của người dùng
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication); //thiết lập authentication mới tạo vào context của srping security và cho phép người dùng truy cập các dữ liệu đợc bảo vệ
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }


//    truyền mã token vào phần header postman gias trị truyền là Bearer + token
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }

    public boolean checkToken(String token) {
        if (tokenrepo.findByStoken(token).isPresent()) {
            return true;
        }
        return false;
    }
}


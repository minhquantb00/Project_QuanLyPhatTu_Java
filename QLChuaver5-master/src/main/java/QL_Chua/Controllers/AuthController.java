package QL_Chua.Controllers;

import QL_Chua.Models.Chuas;
import QL_Chua.Models.KieuThanhViens;
import QL_Chua.Models.PhatTus;
import QL_Chua.Repository.ChuaRepository;
import QL_Chua.Repository.KieuThanhVienRepository;
import QL_Chua.Repository.PhatTuRepository;
import QL_Chua.SMS.Service;
import QL_Chua.SMS.SmsRequest;
import QL_Chua.Security.jwt.JwtUtils;
import QL_Chua.Security.services.LogoutServices;
import QL_Chua.Security.token.Token;
import QL_Chua.Security.token.TokenRepository;
import QL_Chua.Security.token.TokenType;
import QL_Chua.payload.request.LoginRequest;
import QL_Chua.payload.request.SignupRequest;
import QL_Chua.payload.response.JwtResponse;
import QL_Chua.payload.response.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PhatTuRepository phatturepo;
    @Autowired
    KieuThanhVienRepository kieuthanhvienrepo;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    ChuaRepository chuarepo;
    @Autowired
    TokenRepository tokenrepo;
    @Autowired
    LogoutServices logoutServices;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate( // xác thực phân quyền cho việc truy cập tài nguyên
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);

        PhatTus phatTus = phatturepo.findByEmail(loginRequest.getUsername()).orElse(null);
        revokeAllUserTokens(phatTus);
        saveUserToken(phatTus, token);
        if (phatTus == null) {
            return ResponseEntity.badRequest().body("email sai");
        }
        return ResponseEntity.ok(new JwtResponse(token, phatTus));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (phatturepo.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email da dang ky"));
        } else if (phatturepo.existsBySoDienThoai(signUpRequest.getSoDienThoai())) {
            return ResponseEntity.badRequest().body(new MessageResponse("So dien thoai da dang ky"));
        } else {

            Chuas chuas = chuarepo.findById(signUpRequest.getChua().getChuaId()).orElseThrow(() -> new RuntimeException("Khong tim thay chua"));

            KieuThanhViens kieuThanhViens = kieuthanhvienrepo.findById(signUpRequest.getKieuThanhVien()
                    .getKieuThanhVienId()).orElseThrow(() -> new RuntimeException("khong tim thay kieu thanh vien"));

            PhatTus phatTus = new PhatTus();
            phatTus.setHo(signUpRequest.getHo());
            phatTus.setTenDem(signUpRequest.getTenDem());
            phatTus.setTen(signUpRequest.getTen());
            phatTus.setEmail(signUpRequest.getEmail());
            phatTus.setPassword(encoder.encode(signUpRequest.getPassword()));
            phatTus.setPhapDanh(signUpRequest.getPhapDanh());
            phatTus.setAnhChup(signUpRequest.getAnhChup());
            phatTus.setSoDienThoai(signUpRequest.getSoDienThoai());
            phatTus.setGioiTinh(signUpRequest.getGioiTinh());
            phatTus.setChua(chuas);
            phatTus.setKieuThanhVien(kieuThanhViens);
            phatturepo.save(phatTus);
            return ResponseEntity.ok(new MessageResponse("dang ky thanh cong"));
        }
//        return ResponseEntity.ok(new MessageResponse("dang ky that bai"));
    }

    //    @PostMapping("/logout")
//    public void logOut(@RequestBody )
    @PostMapping(value = "/logout")
    public String logOutPage(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        if (authentication1 != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication1);
        }
        return "./";
    }

    @GetMapping(value = "test")
    public String testApi() {
        return "public content";
    }

    public void revokeAllUserTokens(PhatTus phattu) {
        var validUserTokens = tokenrepo.findAllValidTokenByUser(phattu.getPhatTuId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenrepo.saveAll(validUserTokens);
    }

    private void saveUserToken(PhatTus phatTus, String jwtToken) {
        var token = Token.builder()
                .phatTu(phatTus)
                .Stoken(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenrepo.save(token);
    }



}

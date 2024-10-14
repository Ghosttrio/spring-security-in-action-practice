//package com.ghosttrio.security.config;
//
//import org.springframework.security.web.csrf.CsrfToken;
//import org.springframework.security.web.csrf.CsrfTokenRepository;
//import org.springframework.security.web.csrf.DefaultCsrfToken;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.UUID;
//
//public class CustomCsrfTokenRepository implements CsrfTokenRepository {
//    @Override
//    public CsrfToken generateToken(HttpServletRequest request) {
//        String uuid = UUID.randomUUID().toString();
//        return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", uuid);
//    }
//
//    @Override
//    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
//        // 토큰 save 로직
//    }
//
//    @Override
//    public CsrfToken loadToken(HttpServletRequest request) {
//        // 토큰 find 로직
//    }
//}

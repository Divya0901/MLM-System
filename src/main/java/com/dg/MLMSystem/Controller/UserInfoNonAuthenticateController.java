package com.dg.MLMSystem.Controller;

import com.dg.MLMSystem.DTO.AuthRequest;
import com.dg.MLMSystem.DTO.JwtResponse;
import com.dg.MLMSystem.DTO.RefreshTokenRequest;
import com.dg.MLMSystem.DTO.UserDTO;
import com.dg.MLMSystem.Entity.RefreshToken;
import com.dg.MLMSystem.Entity.UserInfo;
import com.dg.MLMSystem.Service.JwtService;
import com.dg.MLMSystem.Service.RefreshTokenService;
import com.dg.MLMSystem.Service.UserInfoService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class UserInfoNonAuthenticateController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @GetMapping(value = "/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
//        response.sendRedirect("/swagger-ui/index.html");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            if (userInfoService.authenticateUser(authRequest.getUsername(), authRequest.getPassword())) {
                String generatedToken = jwtService.generateToken(authRequest.getUsername());
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());
                JwtResponse jwtResponse = JwtResponse.builder()
                        .withAccessToken(generatedToken)
                        .withToken(refreshToken.getToken())
                        .build();
                return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
            }
        } catch (UsernameNotFoundException e) {
            System.out.println(e);
        }
        return new ResponseEntity<>("Invalid user request", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String generatedToken = jwtService.generateToken(userInfo.getName());
                    return JwtResponse.builder()
                            .withAccessToken(generatedToken)
                            .withToken(refreshTokenRequest.getToken())
                            .build();
                }).orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }

    @PostMapping("/register")
    public ResponseEntity<UserInfo> registerUser(@RequestBody UserDTO userDTO) {
        UserInfo userInfo = userInfoService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userInfo);
    }
}

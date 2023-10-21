package com.volunteering.AuthenticationApi.auth;

import com.volunteering.AuthenticationApi.config.JwtService;
import com.volunteering.AuthenticationApi.token.Token;
import com.volunteering.AuthenticationApi.token.TokenRepository;
import com.volunteering.AuthenticationApi.user.User;
import com.volunteering.AuthenticationApi.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class AuthenticationServiceTest {

    @Autowired
    AuthenticationService authenticationService;

    @MockBean
    UserRepository repository;
    @MockBean
    private TokenRepository tokenRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @Test
    void register() {
        User user = User.builder()
                .firstname("test")
                .lastname("test")
                .email("t@t.t")
                .password("test")
                .id(0)
                .build();

        String jwtToken = "auth-token";
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

        Mockito.when(repository.save(Mockito.any())).thenReturn(user);
        Mockito.when(jwtService.generateToken(Mockito.any())).thenReturn(jwtToken);

        RegisterRequest request = RegisterRequest.builder()
                .firstname("test")
                .lastname("test")
                .email("t@t.t")
                .password("test")
                .build();
        AuthenticationResponse token = authenticationService.register(request);
        Assertions.assertEquals(authenticationResponse, token);
    }

    @Test
    void authenticate() {
    }

    @Test
    void refreshToken() {
    }
}
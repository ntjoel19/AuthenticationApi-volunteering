package com.volunteering.AuthenticationApi.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.volunteering.AuthenticationApi.config.JwtService;
import com.volunteering.AuthenticationApi.token.TokenRepository;
import com.volunteering.AuthenticationApi.user.User;
import com.volunteering.AuthenticationApi.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthenticationServiceTest {

    private UserRepository repository;
    @Autowired
    private AuthenticationService authSvc;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void register() {
        RegisterRequest req = new RegisterRequest("n","m","n@y.c","pass");

        AuthenticationResponse res = authSvc.register(req);

        Assertions.assertNotNull(res.getToken());
    }

    @Test
    void authenticate() {
        AuthenticationRequest req = new AuthenticationRequest("n@y.c","pass");

        AuthenticationResponse res = authSvc.authenticate(req);

        Assertions.assertNotNull(res.getToken());
    }

    /*@Test
    @WithMockUser(username = "testUser")
    void refreshToken() {

        // Mock the request and response
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        // Mock the authHeader, refreshToken, and userEmail
        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer mockRefreshToken");
        Mockito.when(jwtService.extractUsername("mockRefreshToken")).thenReturn("testUser");

        // Mock the user and token validation
        User testUser = new User();
        Mockito.when(repository.findByEmail("testUser")).thenReturn(java.util.Optional.of(testUser));
        Mockito.when(jwtService.isTokenValid("mockRefreshToken", testUser)).thenReturn(true);

        // Mock the ObjectMapper behavior
        ObjectMapper objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(ObjectMapper.class).thenReturn(objectMapper);

        // Mock the writeValue method
        doNothing().when(objectMapper).writeValue(response.getOutputStream(), any(AuthenticationResponse.class));

        // Call the refreshToken method
        authenticationService.refreshToken(request, response);

        // Verify that the saveUserToken and isTokenValid methods were called
        Mockito.verify(authenticationService, Mockito.times(1)).saveUserToken(testUser, "mockAccessToken");
        Mockito.verify(authenticationService, Mockito.times(1)).isTokenValid("mockRefreshToken", testUser);
    }*/
}
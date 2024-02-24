package com.volunteering.AuthenticationApi.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    @Test
    void mockRegisterSuccess() throws Exception {
        RegisterRequest newUser = new RegisterRequest("jean", "ntepp", "ntjoel19@yahoo.fr", "namek");
        AuthenticationResponse expectedResponse = new AuthenticationResponse("token", "refresh token");
        Mockito.when(authenticationService.register(newUser))
                .thenReturn(expectedResponse);

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newUser)))
                        .andExpect(status().isOk())
                        .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                        .andExpect(jsonPath("$.access_token").value(expectedResponse.getToken()))
                        .andExpect(jsonPath("$.refresh_token").value(expectedResponse.getRefreshToken()));

    }

    @Test
    void mockRegisterFailure() throws Exception{
        RegisterRequest request = new RegisterRequest("jean", "ntepp", "ntjoel19@y.c","namek");
        AuthenticationResponse resp = new AuthenticationResponse(null, null);

        Mockito.when(authenticationController.register(request))
                .thenThrow(new RuntimeException("Simulated registration failure"));

        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.access_token").value(resp.getToken()));
    }


    @Test
    void authenticate() {
    }

    @Test
    void refreshToken() {
    }

    @Test
    void verifyEmail() {
    }
}
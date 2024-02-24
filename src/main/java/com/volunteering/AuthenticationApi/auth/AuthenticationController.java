package com.volunteering.AuthenticationApi.auth;

import com.volunteering.AuthenticationApi.error.ErrorResponse;
import com.volunteering.AuthenticationApi.user.User;
import com.volunteering.AuthenticationApi.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Jean Joel NTEPP
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        log.info("Registration controller : request from {} processing ... ", request.getEmail());
        try {
            log.info("Registration controller : request from {} succeeded", request.getEmail());
            AuthenticationResponse response = authenticationService.register(request);
            return ResponseEntity.ok(response); // Return a 200 OK response with the result.
        } catch (Exception e) {
            log.info("Registration controller : request from {} failed", request.getEmail());
            ErrorResponse errorResponse = new ErrorResponse("Registration failed : "+ e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        log.info("Authentication controller : request from {} processing ... ", request.getEmail());
        try {
            log.info("Authentication controller : request from {} succeeded", request.getEmail());
            AuthenticationResponse response = authenticationService.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.info("Authentication controller : request from {} failed", request.getEmail());
            ErrorResponse errorResponse = new ErrorResponse("Registration failed : "+ e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    /**
     * Email verification
     * @param token
     * @return
     */
    @GetMapping("/verify/{token}")
    public ResponseEntity<String> verifyEmail(@PathVariable String token) {
        Optional<User> userOptional = userRepository.findByVerificationToken(token);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEmailVerified(true);
            userRepository.save(user);
            return ResponseEntity.ok("Email verified successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid or expired token.");
        }
    }

}

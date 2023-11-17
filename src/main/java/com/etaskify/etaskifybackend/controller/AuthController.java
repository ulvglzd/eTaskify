package com.etaskify.etaskifybackend.controller;


import com.etaskify.etaskifybackend.dto.authDTO.AuthResponse;
import com.etaskify.etaskifybackend.dto.authDTO.SignInRequest;
import com.etaskify.etaskifybackend.dto.authDTO.SignUpRequest;
import com.etaskify.etaskifybackend.exception.IncorrectCredentialsException;
import com.etaskify.etaskifybackend.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest request) {
        try{
            return ResponseEntity.ok(authService.signIn(request));
        } catch (IncorrectCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}

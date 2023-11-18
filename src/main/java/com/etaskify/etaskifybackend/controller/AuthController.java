package com.etaskify.etaskifybackend.controller;


import com.etaskify.etaskifybackend.dto.authDTO.AuthResponse;
import com.etaskify.etaskifybackend.dto.authDTO.SignInRequest;
import com.etaskify.etaskifybackend.dto.authDTO.SignUpRequest;
import com.etaskify.etaskifybackend.exception.IncorrectCredentialsException;
import com.etaskify.etaskifybackend.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Api for sign-up and sign in", description = "Use this api to sign up to create your organization profile")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Sign up", description = "It is accessible to public users and enables user to sign up and create organization profile")
    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @Operation(summary = "Sign in", description = "It is accessible to public users and enables user to sign in")
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest request) {
        try{
            return ResponseEntity.ok(authService.signIn(request));
        } catch (IncorrectCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}

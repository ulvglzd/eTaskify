package com.etaskify.etaskifybackend.service.auth;

import com.etaskify.etaskifybackend.dto.authDTO.AuthResponse;
import com.etaskify.etaskifybackend.dto.authDTO.SignInRequest;
import com.etaskify.etaskifybackend.dto.authDTO.SignUpRequest;
import com.etaskify.etaskifybackend.enums.Role;
import com.etaskify.etaskifybackend.exception.AlreadyExistsException;
import com.etaskify.etaskifybackend.exception.EntityNotFoundException;
import com.etaskify.etaskifybackend.exception.IncorrectCredentialsException;
import com.etaskify.etaskifybackend.model.Organization;
import com.etaskify.etaskifybackend.model.User;
import com.etaskify.etaskifybackend.repository.OrganizationRepository;
import com.etaskify.etaskifybackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final OrganizationRepository organizationRepository;
    private final UserDetailsServiceImpl userDetailsService;

    @Transactional
    public AuthResponse signUp(SignUpRequest signUpRequest) {

        List<String> errors = new ArrayList<>();

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            errors.add("User with this username already exists");
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            errors.add("User with this email already exists");
        }
        if (organizationRepository.existsByName(signUpRequest.getOrganizationName())) {
            errors.add("Organization with this name already exists");
        }

        if (!errors.isEmpty()) {
            String errorMessage = String.join(", ", errors);
            throw new AlreadyExistsException(errorMessage);
        }

        Organization organization = Organization.builder()
                .name(signUpRequest.getOrganizationName())
                .address(signUpRequest.getOrganizationAddress())
                .phoneNumber(signUpRequest.getOrganizationPhoneNumber())
                .build();
        organizationRepository.save(organization);

        var user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .organization(organization)
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String generatedToken = jwtService.generateToken(userDetails);
        log.info("User registered successfully with email: {}", user.getEmail());


        return AuthResponse.builder()
                .token(generatedToken)
                .build();
    }

    public AuthResponse signIn(SignInRequest request) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());
            String generatedToken = jwtService.generateToken(user);
            log.info("User signed in successfully with email: {}", user.getUsername());

            return AuthResponse.builder()
                    .token(generatedToken)
                    .message("Successfully signed in")
                    .build();

        } catch (AuthenticationException e) {
            throw new IncorrectCredentialsException("Incorrect username or password");
        }
    }

    public User getSignedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User signedInUser = userRepository
                .findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return signedInUser;
    }
}

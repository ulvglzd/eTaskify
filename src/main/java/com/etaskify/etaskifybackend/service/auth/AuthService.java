package com.etaskify.etaskifybackend.service.auth;

import com.etaskify.etaskifybackend.dto.authDTO.AuthResponse;
import com.etaskify.etaskifybackend.dto.authDTO.SignInRequest;
import com.etaskify.etaskifybackend.dto.authDTO.SignUpRequest;
import com.etaskify.etaskifybackend.enums.Role;
import com.etaskify.etaskifybackend.exception.AlreadyExistsException;
import com.etaskify.etaskifybackend.exception.IncorrectCredentialsException;
import com.etaskify.etaskifybackend.model.Organization;
import com.etaskify.etaskifybackend.model.User;
import com.etaskify.etaskifybackend.repository.OrganizationRepository;
import com.etaskify.etaskifybackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final OrganizationRepository organizationRepository;
    private final UserDetailsServiceImpl userDetailsService;

    @Transactional
    public AuthResponse signUp(SignUpRequest signUpRequest) {
        //check if username, email, or organization name exists
        Map<String, Boolean> exists = new HashMap<>();
        exists.put("emailExists", userRepository.existsByEmail(signUpRequest.getEmail()));
        exists.put("usernameExists", userRepository.existsByUsername(signUpRequest.getUsername()));
        exists.put("organizationNameExists", organizationRepository.existsByName(signUpRequest.getOrganizationName()));

        if (exists.containsValue(true)) {
            throw new AlreadyExistsException("Signing up failed", exists);
        }



        var organization = new Organization();
        organization.setName(signUpRequest.getOrganizationName());
        organization.setAddress(signUpRequest.getOrganizationAddress());
        organization.setPhoneNumber(signUpRequest.getOrganizationPhoneNumber());
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

            return AuthResponse.builder()
                    .token(generatedToken)
                    .build();
        } catch (AuthenticationException e) {
            throw new IncorrectCredentialsException("Incorrect username or password");
        }
    }

    public static User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

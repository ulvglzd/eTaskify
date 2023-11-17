package com.etaskify.etaskifybackend.dto.authDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private String username;
    private String email;
    private String password;
    private String organizationName;
    private String organizationAddress;
    private String organizationPhoneNumber;

}

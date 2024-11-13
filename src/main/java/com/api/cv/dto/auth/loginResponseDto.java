	package com.api.cv.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class loginResponseDto {

    private String access_token;
    private String refresh_token;
    private String token_type;
    private Integer expires_in;
    private String scope;
}

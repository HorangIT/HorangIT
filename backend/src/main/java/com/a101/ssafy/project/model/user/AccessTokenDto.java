package com.a101.ssafy.project.model.user;

import lombok.*;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenDto {
    private String email;
    private String password;
    private String accessToken;
    private String refreshToken;
}

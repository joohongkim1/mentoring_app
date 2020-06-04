package com.back.admin.web.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserJwtRequestDto {
    private String user_id_email;
    private String user_password;

    @Builder
    public UserJwtRequestDto(String user_id_email, String user_password){
        this.user_id_email=user_id_email;
        this.user_password=user_password;
    }
}
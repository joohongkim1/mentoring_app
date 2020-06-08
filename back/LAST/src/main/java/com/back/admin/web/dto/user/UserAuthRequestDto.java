package com.back.admin.web.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserAuthRequestDto {

    private String user_id_email;
    private int user_auth;

    @Builder
    public UserAuthRequestDto(String user_id_email, int user_auth) {
        this.user_id_email = user_id_email;
        this.user_auth = user_auth;
    }
}

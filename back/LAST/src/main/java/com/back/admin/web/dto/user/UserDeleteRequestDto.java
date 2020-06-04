package com.back.admin.web.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDeleteRequestDto {
    String user_id_email;
    UserDeleteRequestDto(String user_id_email) {
        this.user_id_email=user_id_email;
    }
}

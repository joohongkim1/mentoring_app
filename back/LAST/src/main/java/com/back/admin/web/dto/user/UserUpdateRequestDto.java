package com.back.admin.web.dto.user;

import com.back.admin.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String user_school;
    private String user_major;
    private String user_password;

    @Builder
    public UserUpdateRequestDto(User entity) {
        this.user_school = entity.getUser_school();
        this.user_major = entity.getUser_major();
        this.user_password = entity.getUser_password();
    }
}

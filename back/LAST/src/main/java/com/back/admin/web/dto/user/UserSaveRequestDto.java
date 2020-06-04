package com.back.admin.web.dto.user;

import com.back.admin.domain.user.Role;
import com.back.admin.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSaveRequestDto {
    private int user_auth;  // 0: 일반, 1: 우수 , 2: 스탭, 3: 관리
    private String user_name;
    private String user_school;
    private String user_major;
    private String user_id_email;
    private String user_password;
    private Long user_total_mileage;
    private Role role;

    public UserSaveRequestDto(User entity) {
        this.user_auth = entity.getUser_auth();
        this.user_name = entity.getUser_name();
        this.user_school = entity.getUser_school();
        this.user_major = entity.getUser_major();
        this.user_id_email = entity.getUser_id_email();
        this.user_password = entity.getUser_password();
        this.user_total_mileage = entity.getUser_total_mileage();
        this.role = entity.getRole();
    }

    public User toEntity() {
        return User.builder()
                .user_auth(user_auth)
                .user_name(user_name)
                .user_school(user_school)
                .user_major(user_major)
                .user_id_email(user_id_email)
                .user_password(user_password)
                .user_total_mileage(user_total_mileage)
                .role(role)
                .build();
    }
}

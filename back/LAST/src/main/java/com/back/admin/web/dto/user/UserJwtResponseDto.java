package com.back.admin.web.dto.user;

import com.back.admin.domain.user.Role;
import com.back.admin.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserJwtResponseDto {
    private Long user_no;
    private int user_auth;  // 0: 일반, 1: 우수 , 2: 스탭, 3: 관리
    private String user_name;
    private String user_school;
    private String user_major;
    private String user_id_email;
    private String user_password;
    private Long user_total_mileage;
    private Role role;

    public UserJwtResponseDto(User entity) {
        this.user_no = entity.getUser_no();
        this.user_auth = entity.getUser_auth();
        this.user_name = entity.getUser_name();
        this.user_school = entity.getUser_school();
        this.user_major = entity.getUser_major();
        this.user_id_email = entity.getUser_id_email();
        this.user_password = entity.getUser_password();
        this.user_total_mileage = entity.getUser_total_mileage();
        this.role = entity.getRole();
    }
}

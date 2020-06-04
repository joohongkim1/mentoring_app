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
    // private Long stu_auth;  // 0: 일반, 1: 우수 , 2: 스탭, 3: 관리
    private String user_school;
    private String user_major;
    private String user_password;
    // private Long stu_total_mileage;

    // 권한 설정과 마일리지는 관리자가 해야하는데 어떻게 해야할까??
    @Builder
    public UserUpdateRequestDto(User entity) {
        this.user_school = entity.getUser_school();
        this.user_major = entity.getUser_major();
        this.user_password = entity.getUser_password();
    }
}

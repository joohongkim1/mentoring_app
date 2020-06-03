package com.back.admin.web.dto.student;

import com.back.admin.domain.student.Student;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentUpdateRequestDto {
    // private Long stu_auth;  // 0: 일반, 1: 우수 , 2: 스탭, 3: 관리
    private String stu_id_email;
    private String stu_school;
    private String stu_major;
    private String stu_password;
    // private Long stu_total_mileage;

    // 권한 설정과 마일리지는 관리자가 해야하는데 어떻게 해야할까??
    @Builder
    public StudentUpdateRequestDto(Student entity) {
        this.stu_id_email = entity.getStu_id_email();
        this.stu_school = entity.getStu_school();
        this.stu_major = entity.getStu_major();
        this.stu_password = entity.getStu_password();
    }
}

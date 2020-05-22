package com.back.admin.web.dto.student;

import com.back.admin.domain.student.Student;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
public class StudentResponseDto {
    private Long stu_no;
    private int stu_auth;  // 0: 일반, 1: 우수 , 2: 스탭, 3: 관리
    private String stu_name;
    private String stu_school;
    private String stu_major;
    private String stu_id;
    private String stu_email;
    private String stu_password;
    private Long stu_total_mileage;

    @Builder
    public StudentResponseDto(Student entity) {
        this.stu_no = entity.getStu_no();
        this.stu_auth = entity.getStu_auth();
        this.stu_name = entity.getStu_name();
        this.stu_school = entity.getStu_school();
        this.stu_major = entity.getStu_major();
        this.stu_id = entity.getStu_id();
        this.stu_email = entity.getStu_email();
        this.stu_password = entity.getStu_password();
        this.stu_total_mileage = entity.getStu_total_mileage();
    }
}

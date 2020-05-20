package com.back.admin.web.dto.student;

import com.back.admin.domain.student.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StudentJwtResponseDto {
    private Long stu_no;
    private Long stu_auth;  // 0: 일반, 1: 우수 , 2: 스탭, 3: 관리
    private String stu_name;
    private String stu_school;
    private String stu_major;
    private String stu_id;
    private String stu_email;
    private Long stu_total_mileage;

    public StudentJwtResponseDto(Student entity) {
        this.stu_no = entity.getStu_no();
        this.stu_auth = entity.getStu_auth();
        this.stu_name = entity.getStu_name();
        this.stu_school = entity.getStu_school();
        this.stu_major = entity.getStu_major();
        this.stu_id = entity.getStu_id();
        this.stu_email = entity.getStu_email();
        this.stu_total_mileage = entity.getStu_auth();
    }
}

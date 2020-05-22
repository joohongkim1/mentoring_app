package com.back.admin.web.dto.student;

import com.back.admin.domain.student.Student;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentSaveRequestDto {
    private int stu_auth;  // 0: 일반, 1: 우수 , 2: 스탭, 3: 관리
    private String stu_name;
    private String stu_school;
    private String stu_major;
    private String stu_id;
    private String stu_email;
    private String stu_password;
    private Long stu_total_mileage;

    @Builder
    public StudentSaveRequestDto(Student entity) {
        this.stu_auth = entity.getStu_auth();
        this.stu_name = entity.getStu_name();
        this.stu_school = entity.getStu_school();
        this.stu_major = entity.getStu_major();
        this.stu_id = entity.getStu_id();
        this.stu_email = entity.getStu_email();
        this.stu_password = entity.getStu_password();
        this.stu_total_mileage = entity.getStu_total_mileage();
    }

    public Student toEntity() {
        return Student.builder()
                .stu_auth(stu_auth)
                .stu_name(stu_name)
                .stu_school(stu_school)
                .stu_major(stu_major)
                .stu_id(stu_id)
                .stu_email(stu_email)
                .stu_password(stu_password)
                .stu_total_mileage(stu_total_mileage)
                .build();
    }
}

package com.back.admin.web.dto.mentor;

import com.back.admin.domain.mentor.Mentor;
import com.back.admin.domain.student.Student;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MentorResponseDto {
    private Long mentor_no;
    private int mentor_auth;  // 0: 일반, 1: 우수 , 2: 스탭, 3: 관리
    private String mentor_name;
    private String mentor_identification_url;
    private Boolean mentor_check;
    private String mentor_company;
    private String mentor_job;
    private String mentor_school;
    private String mentor_major;
    private String mentor_id_email;
    private String mentor_password;
    private Long mentor_total_mileage;

    @Builder
    public MentorResponseDto(Mentor entity) {
        this.mentor_no = entity.getMentor_no();
        this.mentor_auth = entity.getMentor_auth();
        this.mentor_name = entity.getMentor_name();
        this.mentor_identification_url = entity.getMentor_identification_url();
        this.mentor_check = entity.getMentor_check();
        this.mentor_company = entity.getMentor_company();
        this.mentor_job = entity.getMentor_job();
        this.mentor_school = entity.getMentor_school();
        this.mentor_major = entity.getMentor_major();
        this.mentor_id_email = entity.getMentor_id_email();
        this.mentor_password = entity.getMentor_password();
        this.mentor_total_mileage = entity.getMentor_total_mileage();
    }
}

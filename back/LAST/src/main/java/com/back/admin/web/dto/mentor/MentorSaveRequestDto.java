package com.back.admin.web.dto.mentor;

import com.back.admin.domain.mentor.Mentor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MentorSaveRequestDto {
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
    public MentorSaveRequestDto(Mentor entity) {
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

    public Mentor toEntity() {
        return Mentor.builder()
                .mentor_auth(mentor_auth)
                .mentor_name(mentor_name)
                .mentor_company(mentor_company)
                .mentor_job(mentor_job)
                .mentor_school(mentor_school)
                .mentor_major(mentor_major)
                .mentor_id_email(mentor_id_email)
                .mentor_password(mentor_password)
                .mentor_total_mileage(mentor_total_mileage)
                .build();
    }
}

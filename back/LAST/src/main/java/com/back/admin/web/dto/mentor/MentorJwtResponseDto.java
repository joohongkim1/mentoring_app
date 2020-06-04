package com.back.admin.web.dto.mentor;

import com.back.admin.domain.mentor.Mentor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MentorJwtResponseDto {

    private Long mentor_no;
    private String mentor_identification_url;
    private Boolean mentor_check;
    private String mentor_company;
    private String mentor_job;

    public MentorJwtResponseDto(Mentor entity) {
        this.mentor_no = entity.getMentor_no();
        this.mentor_identification_url = entity.getMentor_identification_url();
        this.mentor_check = entity.getMentor_check();
        this.mentor_company = entity.getMentor_company();
        this.mentor_job = entity.getMentor_job();
    }
}

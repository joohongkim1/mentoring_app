package com.back.admin.web.dto.mentor;

import com.back.admin.domain.mentor.Mentor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MentorUpdateRequestDto {

    private String mentor_identification_url;
    private String mentor_company;
    private String mentor_job;

    @Builder
    public MentorUpdateRequestDto(Mentor entity) {
        this.mentor_identification_url = entity.getMentor_identification_url();
        this.mentor_company = entity.getMentor_company();
        this.mentor_job = entity.getMentor_job();
    }
    
}

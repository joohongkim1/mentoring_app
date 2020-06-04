package com.back.admin.web.dto.mentor;

import com.back.admin.domain.mentor.Mentor;
import com.back.admin.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MentorSaveRequestDto {

    private String mentor_identification_url;
    private String mentor_company;
    private String mentor_job;

    @Builder
    public MentorSaveRequestDto(Mentor entity) {
        this.mentor_identification_url = entity.getMentor_identification_url();
        this.mentor_company = entity.getMentor_company();
        this.mentor_job = entity.getMentor_job();
    }

    public Mentor toEntity(User user) {
        return Mentor.builder()
                .mentor_identification_url(mentor_identification_url)
                .mentor_company(mentor_company)
                .mentor_job(mentor_job)
                .mentor(user)
                .build();
    }
}

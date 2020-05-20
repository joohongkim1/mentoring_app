package com.back.admin.web.dto.experience;

import com.back.admin.domain.experience.Experience;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class ExperienceSaveRequestDto {
    private Date experience_start;
    private Date experience_end;
    private String experience_title;
    private String experience_content;

    @Builder
    public ExperienceSaveRequestDto(Experience entity) {
        this.experience_start = entity.getExperience_start();
        this.experience_end = entity.getExperience_end();
        this.experience_title = entity.getExperience_title();
        this.experience_content = entity.getExperience_content();
    }

    public Experience toEntity() {
        return Experience.builder()
                .experience_start(experience_start)
                .experience_end(experience_end)
                .experience_title(experience_title)
                .experience_content(experience_content)
                .build();
    }
}

package com.back.admin.web.dto.experience;

import com.back.admin.domain.experience.Experience;
import com.back.admin.domain.student.Student;
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
    public ExperienceSaveRequestDto(Date experience_start, Date experience_end,
                                    String experience_title, String experience_content) {
        this.experience_start = experience_start;
        this.experience_end = experience_end;
        this.experience_title = experience_title;
        this.experience_content = experience_content;
    }

    public Experience toEntity(Student stuexperience) {
        return Experience.builder()
                .experience_start(experience_start)
                .experience_end(experience_end)
                .experience_title(experience_title)
                .experience_content(experience_content)
                .stuexperience(stuexperience)
                .build();
    }
}

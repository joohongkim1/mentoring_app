package com.back.admin.web.dto.experience;

import com.back.admin.domain.experience.Experience;
import com.back.admin.domain.student.Student;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class ExperienceResponseDto {
    private Long experience_no;
    private Long stu_no;
    private Date experience_start;
    private Date experience_end;
    private String experience_title;
    private String experience_content;


    @Builder
    public ExperienceResponseDto(Experience entity) {
        this.stu_no = entity.getExperience_no();
        this.experience_no = entity.getExperience_no();
        this.experience_start = entity.getExperience_start();
        this.experience_end = entity.getExperience_end();
        this.experience_title = entity.getExperience_title();
        this.experience_content = entity.getExperience_content();
    }
}

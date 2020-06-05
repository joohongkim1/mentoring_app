package com.back.admin.web.dto.student;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentDeleteRequestDto {
    String stu_id_email;
    StudentDeleteRequestDto(String stu_id_email) {
        this.stu_id_email=stu_id_email;
    }
}

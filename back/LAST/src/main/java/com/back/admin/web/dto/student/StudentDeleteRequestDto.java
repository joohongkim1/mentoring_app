package com.back.admin.web.dto.student;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentDeleteRequestDto {
    String stu_id;
    StudentDeleteRequestDto(String stu_id) {
        this.stu_id=getStu_id();
    }
}

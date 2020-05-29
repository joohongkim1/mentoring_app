package com.back.admin.web.dto.student;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StudentAuthRequestDto {

    private String stu_id_email;
    private int stu_auth;

    @Builder
    public StudentAuthRequestDto(String stu_id_email, int stu_auth) {
        this.stu_id_email = stu_id_email;
        this.stu_auth = stu_auth;
    }
}

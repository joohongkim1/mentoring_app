package com.back.admin.web.dto.student;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StudentAuthRequestDto {

    private Long stu_no;
    private int stu_auth;

    @Builder
    public StudentAuthRequestDto(Long stu_no, int stu_auth) {
        this.stu_no = stu_no;
        this.stu_auth = stu_auth;
    }
}

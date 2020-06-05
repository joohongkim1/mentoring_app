package com.back.admin.web.dto.student;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentJwtRequestDto {
    private String stu_id_email;
    private String stu_password;

    @Builder
    public StudentJwtRequestDto(String stu_id_email,String stu_password){
        this.stu_id_email=stu_id_email;
        this.stu_password=stu_password;
    }
}
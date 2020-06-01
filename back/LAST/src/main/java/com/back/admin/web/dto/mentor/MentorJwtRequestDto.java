package com.back.admin.web.dto.mentor;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MentorJwtRequestDto {
    private String mentor_id_email;
    private String mentor_password;

    @Builder
    public MentorJwtRequestDto(String mentor_id_email,String mentor_password){
        this.mentor_id_email=mentor_id_email;
        this.mentor_password=mentor_password;
    }
}

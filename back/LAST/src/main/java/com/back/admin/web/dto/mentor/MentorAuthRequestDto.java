package com.back.admin.web.dto.mentor;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MentorAuthRequestDto {

    private String mentor_id_email;
    private int mentor_auth;

    @Builder
    public MentorAuthRequestDto(String mentor_id_email, int mentor_auth) {
        this.mentor_id_email = mentor_id_email;
        this.mentor_auth = mentor_auth;
    }
}

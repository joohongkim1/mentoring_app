package com.back.admin.web.dto.mentor;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MentorDeleteRequestDto {
    String mentor_id_email;
    MentorDeleteRequestDto(String mentor_id_email) {
        this.mentor_id_email = mentor_id_email;
    }

}

package com.back.admin.web.dto.mentor;

import com.back.admin.domain.mentor.Mentor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MentorUpdateRequestDto {
    // private Long mentor_auth;  // 0: 일반, 1: 우수 , 2: 스탭, 3: 관리
    private String mentor_job;
    private String mentor_major;
    private String mentor_password;
    // private Long mentor_total_mileage;

    // 권한 설정과 마일리지는 관리자가 해야하는데 어떻게 해야할까??
    @Builder
    public MentorUpdateRequestDto(Mentor entity) {
        this.mentor_job = entity.getMentor_job();
        this.mentor_major = entity.getMentor_major();
        this.mentor_password = entity.getMentor_password();
    }
    
}

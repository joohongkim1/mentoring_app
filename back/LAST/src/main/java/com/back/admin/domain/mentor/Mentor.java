package com.back.admin.domain.mentor;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Mentor{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mentor_no;

    @Column
    private String mentor_identification_url;  // 멘토 현직자 확인과정

    @Column
    private String mentor_company;

    @Column
    private String mentor_job;  // 멘토 직무


    @Builder
    public Mentor(String mentor_identification_url, String mentor_company, String mentor_job) {
        this.mentor_identification_url = mentor_identification_url;
        this.mentor_company = mentor_company;
        this.mentor_job = mentor_job;
    }

    // 회원가입 이후, 수정할 수 있는 정보들
    public void update(String mentor_identification_url,String mentor_company, String mentor_job) {
        this.mentor_identification_url = mentor_identification_url;
        this.mentor_company = mentor_company;
        this.mentor_job = mentor_job;
    }

}

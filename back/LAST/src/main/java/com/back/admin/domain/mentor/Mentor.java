package com.back.admin.domain.mentor;

import com.back.admin.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

    // 1:N = student:mentor
    @ManyToOne(optional = false)
    @JsonBackReference
    private User mentor;


    @Builder
    public Mentor(String mentor_identification_url, String mentor_company, String mentor_job, User mentor) {
        this.mentor_identification_url = mentor_identification_url;
        this.mentor_company = mentor_company;
        this.mentor_job = mentor_job;
        this.mentor = mentor;
    }

    public void update(String mentor_identification_url,String mentor_company, String mentor_job) {
        this.mentor_identification_url = mentor_identification_url;
        this.mentor_company = mentor_company;
        this.mentor_job = mentor_job;
    }

}

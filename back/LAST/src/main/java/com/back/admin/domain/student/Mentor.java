package com.back.admin.domain.student;

import com.back.admin.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Mentor extends BaseTimeEntity {
    @Id  // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto-increment
    // stu_no:1,2,3,4,올라가는 값인데, int보다 long이 큰 의미라서 int stu_no보다 Long stu_no을 보편적으로 사용합니다
    private Long mentor_no;

    @Column
    private Long mentor_auth;  // 4: 일반, 5: 우수

    @Column
    private String mentor_name;

    @Column
    private String mentor_identification_url;  // 멘토 현직자 확인과정

    @Column
    private Boolean mentor_check;  // 멘토 승인

    @Column
    private String mentor_company;

    @Column
    private String mentor_major;

    @Column
    private String mentor_job;  // 멘토 직무

    @Column
    private String mentor_id;

    @Column
    private String mentor_email;

    @Column
    private String mentor_password;

    @Column
    private Long mentor_total_mileage;


    @Builder
    public Mentor(Long mentor_auth, String mentor_name, String mentor_identification_url, Boolean mentor_check,
                  String mentor_company, String mentor_major, String mentor_job, String mentor_id,
                  String mentor_email, String mentor_password, Long mentor_total_mileage) {
        this.mentor_auth = mentor_auth;
        this.mentor_name = mentor_name;
        this.mentor_identification_url = mentor_identification_url;
        this.mentor_check = mentor_check;
        this.mentor_company = mentor_company;
        this.mentor_major = mentor_major;
        this.mentor_job = mentor_job;
        this.mentor_id = mentor_id;
        this.mentor_email = mentor_email;
        this.mentor_password = mentor_password;
        this.mentor_total_mileage = mentor_total_mileage;

    }

    // 회원가입 이후, 수정할 수 있는 정보들
    public void update(Long mentor_auth, String mentor_identification_url, Boolean mentor_check,
                       String mentor_company, String mentor_job, String mentor_password,
                       Long mentor_total_mileage) {
        this.mentor_auth = mentor_auth;
        this.mentor_identification_url = mentor_identification_url;
        this.mentor_check = mentor_check;
        this.mentor_company = mentor_company;
        this.mentor_job = mentor_job;
        this.mentor_password = mentor_password;
        this.mentor_total_mileage = mentor_total_mileage;
    }
}

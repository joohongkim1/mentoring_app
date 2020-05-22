package com.back.admin.domain.student;

import com.back.admin.domain.BaseTimeEntity;
import com.back.admin.domain.experience.Experience;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Student extends BaseTimeEntity {

    @Id  // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto-increment
    // stu_no:1,2,3,4,올라가는 값인데, int보다 long이 큰 의미라서 int stu_no보다 Long stu_no을 보편적으로 사용합니다
    private Long stu_no;

    @Column
    private Long stu_auth;  // 0: 일반, 1: 우수 , 2: 스탭, 3: 관리

    @Column
    private String stu_name;

    @Column
    private String stu_school;

    @Column
    private String stu_major;

    @Column
    private String stu_id;

    @Column
    private String stu_email;

    @Column
    private String stu_password;

    @Column
    private Long stu_total_mileage;

    // fk -> 1:N = student:experience
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "stu_no")
    @JsonManagedReference
    private List<Experience> experience;


    @Builder
    public Student(Long stu_auth, String stu_name, String stu_school, String stu_major, String stu_id,
                   String stu_email, String stu_password, Long stu_total_mileage) {
        this.stu_auth = stu_auth;
        this.stu_name = stu_name;
        this.stu_school = stu_school;
        this.stu_major = stu_major;
        this.stu_id = stu_id;
        this.stu_email = stu_email;
        this.stu_password = stu_password;
        this.stu_total_mileage = stu_total_mileage;
    }

    // 회원가입 이후, 수정할 수 있는 정보들
    // auth, mileage는 관리자만 수정 가능!!
    // 관리자 페이지 따로 만들어야할듯 -> student, mentor 마일리지 관리, 권한 관리
    public void update(String stu_school, String stu_major, String stu_password) {
//        this.stu_auth = stu_auth;
        this.stu_school = stu_school;
        this.stu_major = stu_major;
        this.stu_password = stu_password;
//        this.stu_total_mileage = stu_total_mileage;
    }
}
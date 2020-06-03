//package com.back.admin.domain.user;
//
//import com.back.admin.domain.experience.Experience;
//import com.back.admin.domain.sol_question.SolQuestion;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Getter
//@NoArgsConstructor
//@Entity
//public class User {
//    @Id  // pk
//    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto-increment
//    // stu_no:1,2,3,4,올라가는 값인데, int보다 long이 큰 의미라서 int stu_no보다 Long stu_no을 보편적으로 사용합니다
//    private Long user_no;
//
//    @Column
//    private int stu_auth;  // 0: 일반, 1: 우수 , 2: 스탭, 3: 관리
//
//    @Column
//    private String user_name;
//
//    @Column
//    private String user_school;
//
//    @Column
//    private String user_major;
//
//    @Column
//    private String user_id_email;  // email = id
//
//    @Column
//    private String user_password;
//
//    @Column
//    private Long user_total_mileage;
//
//    // fk -> 1:N = student:experience
//    @OneToMany(cascade=CascadeType.ALL, mappedBy = "userexperience")
//    @JsonManagedReference
//    private List<Experience> experience=new ArrayList<>();
//
//    // 1:N = student:sol_q
//    @OneToMany(cascade=CascadeType.ALL, mappedBy = "studentsolq")
//    @JsonManagedReference
//    private List<SolQuestion> solquestion=new ArrayList<>();
//}

package com.back.admin.domain.experience;

import com.back.admin.domain.student.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class Experience {
    @Id  // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto-increment
    // stu_no:1,2,3,4,올라가는 값인데, int보다 long이 큰 의미라서 int stu_no보다 Long stu_no을 보편적으로 사용합니다
    private Long experience_no;

//    @Column
//    private Long stu_no;  // 0: 일반, 1: 우수 , 2: 스탭, 3: 관리

    @Column
    private Date experience_start;

    @Column
    private Date experience_end;

    @Column
    private String experience_title;

    @Column(columnDefinition = "TEXT", length = 500)
    private String experience_content;

    // fk -> 1:N = user:experience
    @ManyToOne(optional = false)
    @JsonBackReference
    private Student stu_no;

    @Builder
    public Experience(Date experience_start, Date experience_end, String experience_title,
                      String experience_content, Student stu_no) {
        this.experience_start = experience_start;
        this.experience_end = experience_end;
        this.experience_title = experience_title;
        this.experience_content = experience_content;
        this.stu_no = stu_no;
    }

    public void update(Date experience_start, Date experience_end,
                       String experience_title, String experience_content) {
        this.experience_start = experience_start;
        this.experience_end = experience_end;
        this.experience_title = experience_title;
        this.experience_content = experience_content;
    }
}

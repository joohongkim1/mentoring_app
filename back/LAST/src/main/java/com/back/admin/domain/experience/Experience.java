package com.back.admin.domain.experience;

import com.back.admin.domain.board.Board;
import com.back.admin.domain.student.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Experience {
    @Id  // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto-increment
    // stu_no:1,2,3,4,올라가는 값인데, int보다 long이 큰 의미라서 int stu_no보다 Long stu_no을 보편적으로 사용합니다
    private Long experience_no;

    @Column
    private Date experience_start;

    @Column
    private Date experience_end;

    @Column
    private String experience_title;

    @Column(length = 500)
    private String experience_content;

    @Column(length = 500)
    private String experience_tag;

    // fk -> 1:N = student:experience
    @ManyToOne(optional = false)
    @JsonBackReference
    private Student stuexperience;

    // fk -> 1:N = experience:board
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "experienceboard")
    @JsonManagedReference
    private List<Board> boards=new ArrayList<>();

    @Builder
    public Experience(Date experience_start, Date experience_end, String experience_title,
                      String experience_content, String experience_tag, Student stuexperience) {
        this.experience_start = experience_start;
        this.experience_end = experience_end;
        this.experience_title = experience_title;
        this.experience_content = experience_content;
        this.experience_tag = experience_tag;
        this.stuexperience = stuexperience;
    }

    public void update(Date experience_start, Date experience_end, String experience_title,
                       String experience_content, String experience_tag) {
        this.experience_start = experience_start;
        this.experience_end = experience_end;
        this.experience_title = experience_title;
        this.experience_content = experience_content;
        this.experience_tag = experience_tag;
    }
}

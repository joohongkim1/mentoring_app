package com.back.admin.domain.sol_question;

import com.back.admin.domain.BaseTimeEntity;
import com.back.admin.domain.mentor.Mentor;
import com.back.admin.domain.sol_answer.SolAnswer;
import com.back.admin.domain.student.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class SolQuestion extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sol_q_no;

    @Column(length = 500)
    private String sol_q_title;

    @Column(length = 200)
    private String sol_q_want_job;

    @Column(length = 200)
    private String sol_q_time;  // time이 뭐였지?

    @Column(length = 200)
    private String sol_q_company;

    @Column(columnDefinition = "TEXT")
    private String sol_q_content;

    // 1:N = student:sol_q
    @ManyToOne(optional = false)
    @JsonBackReference
    private Student studentsolq;

    // 1:N = sol_q:sol_a
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "sol_q_a")
    @JsonManagedReference
    private List<SolAnswer> solAnswers=new ArrayList<>();

    @Builder
    public SolQuestion(String sol_q_title, String sol_q_want_job,
                       String sol_q_time, String sol_q_company, String sol_q_content, Student studentsolq) {
        this.sol_q_title=sol_q_title;
        this.sol_q_want_job=sol_q_want_job;
        this.sol_q_time=sol_q_time;
        this.sol_q_company=sol_q_company;
        this.sol_q_content=sol_q_content;
        this.studentsolq=studentsolq;
    }

    public void update(String sol_q_title, String sol_q_want_job,
                       String sol_q_time, String sol_q_company, String sol_q_content) {
        this.sol_q_title=sol_q_title;
        this.sol_q_want_job=sol_q_want_job;
        this.sol_q_time=sol_q_time;
        this.sol_q_company=sol_q_company;
        this.sol_q_content=sol_q_content;
    }
}

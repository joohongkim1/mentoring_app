package com.back.admin.domain.sol_answer;

import com.back.admin.domain.BaseTimeEntity;
import com.back.admin.domain.mentor.Mentor;
import com.back.admin.domain.sol_question.SolQuestion;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class SolAnswer extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sol_a_no;

//    @Column(length = 500)
//    private String sol_a_title;
//
//    @Column(length = 200)
//    private String sol_a_time;  // time이 뭐였지?
//
//    @Column(columnDefinition = "TEXT")
//    private String sol_a_content;

    // 1:N = sol_q:sol_a
    @ManyToOne(optional = false)
    @JsonBackReference
    private SolQuestion sol_q_a;

    // 1:N = mentor:sol_a
    @ManyToOne(optional = false)
    @JsonBackReference
    private Mentor mentorsolanswer;

    @Builder
    public SolAnswer(SolQuestion sol_q_a, Mentor mentorsolanswer) {
//        this.sol_a_title=sol_a_title;
//        this.sol_a_time=sol_a_time;
//        this.sol_a_content=sol_a_time;
        this.sol_q_a=sol_q_a;
        this.mentorsolanswer=mentorsolanswer;
    }
}

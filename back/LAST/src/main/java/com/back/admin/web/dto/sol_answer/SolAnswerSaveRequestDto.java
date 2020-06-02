package com.back.admin.web.dto.sol_answer;

import com.back.admin.domain.mentor.Mentor;
import com.back.admin.domain.sol_answer.SolAnswer;
import com.back.admin.domain.sol_question.SolQuestion;
import lombok.Builder;

public class SolAnswerSaveRequestDto {
    private Long sol_a_no;

    @Builder
    public SolAnswerSaveRequestDto (SolAnswer entity) {
        this.sol_a_no=entity.getSol_a_no();
    }

    public SolAnswer toEntity(SolQuestion solQuestion, Mentor mentor) {
        return SolAnswer.builder()
                .sol_q_a(solQuestion)
                .mentorsolanswer(mentor)
                .build();
    }
}

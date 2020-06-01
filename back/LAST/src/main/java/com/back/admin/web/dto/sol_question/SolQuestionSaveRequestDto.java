package com.back.admin.web.dto.sol_question;

import com.back.admin.domain.so_question.SolQuestion;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SolQuestionSaveRequestDto {

    private String sol_q_title;
    private String sol_q_want_job;
    private String sol_q_time;  // time이 뭐였지?
    private String sol_q_company;
    private String sol_q_content;

    @Builder
    public SolQuestionSaveRequestDto(SolQuestion entity) {
        this.sol_q_title=entity.getSol_q_title();
        this.sol_q_want_job=entity.getSol_q_want_job();
        this.sol_q_time=entity.getSol_q_time();
        this.sol_q_company=entity.getSol_q_company();
        this.sol_q_content=entity.getSol_q_content();
    }

    public SolQuestion toEntity(SolQuestion solQuestion) {
        return SolQuestion.builder()
                .sol_q_title(sol_q_title)
                .sol_q_want_job(sol_q_want_job)
                .sol_q_time(sol_q_company)
                .sol_q_content(sol_q_content)
                .build();
    }

}
package com.back.admin.web.dto.sol_answer;

import com.back.admin.domain.sol_answer.SolAnswer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SolAnswerResponseDto {

    private Long sol_a_no;

    @Builder
    public SolAnswerResponseDto (SolAnswer entity) {
        this.sol_a_no=entity.getSol_a_no();
    }

}

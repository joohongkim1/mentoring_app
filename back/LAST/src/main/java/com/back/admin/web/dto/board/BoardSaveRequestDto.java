package com.back.admin.web.dto.board;

import com.back.admin.domain.board.Board;
import com.back.admin.domain.experience.Experience;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardSaveRequestDto {
    private String board_question;
    private String board_content;
    private String board_company;
    private Long experience_no;
    private String board_keyword;


    @Builder
    public BoardSaveRequestDto(Board entity) {
        this.board_question=entity.getBoard_question();
        this.board_content=entity.getBoard_content();
        this.board_company=entity.getBoard_company();
        this.experience_no=entity.getExperienceboard().getExperience_no();
        this.board_keyword=entity.getBoard_keyword();
    }

    public Board toEntity(Experience experience) {
        return Board.builder()
                .board_question(board_question)
                .board_content(board_content)
                .board_company(board_company)
                .board_keyword(board_keyword)
                .experienceboard(experience)
                .build();
    }


}

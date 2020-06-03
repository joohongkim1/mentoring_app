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
    private String board_when;
    private Long experience_no;


    @Builder
    public BoardSaveRequestDto(Board entity) {
        this.board_question=entity.getBoard_question();
        this.board_content=entity.getBoard_content();
        this.board_when=entity.getBoard_when();
        this.experience_no=entity.getExperienceboard().getExperience_no();
    }

    public Board toEntity(Experience experience) {
        return Board.builder()
                .board_question(board_question)
                .board_content(board_content)
                .board_when(board_when)
                .experienceboard(experience)
                .build();
    }


}

package com.back.admin.web.dto.board;

import com.back.admin.domain.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardResponseDto {

    private Long board_no;
    private String board_question;
    private String board_content;
    private String board_when;

    @Builder
    public BoardResponseDto(Board entity) {
        this.board_no=entity.getBoard_no();
        this.board_question=entity.getBoard_question();
        this.board_content=entity.getBoard_content();
        this.board_when = entity.getBoard_when();
    }
}

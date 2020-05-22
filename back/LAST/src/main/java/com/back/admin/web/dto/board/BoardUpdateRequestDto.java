package com.back.admin.web.dto.board;

import com.back.admin.domain.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardUpdateRequestDto {
    private String board_question;
    private String board_content;

    @Builder
    public BoardUpdateRequestDto(Board entity) {
        this.board_question=entity.getBoard_question();
        this.board_content=entity.getBoard_content();
    }
}

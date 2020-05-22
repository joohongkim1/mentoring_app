package com.back.admin.service;

import com.back.admin.domain.board.Board;
import com.back.admin.domain.board.BoardRepository;
import com.back.admin.domain.experience.Experience;
import com.back.admin.domain.experience.ExperienceRepository;
import com.back.admin.web.dto.board.BoardResponseDto;
import com.back.admin.web.dto.board.BoardSaveRequestDto;
import com.back.admin.web.dto.board.BoardUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final ExperienceRepository experienceRepository;

    // 모든 자소서 보여주기 selectAll
    public List<Board> selectAll() {
        return boardRepository.findAll();
    }


    // 특정 학생의 자소서 보여주기 findBoardByStu_id
    @Transactional
    public List<BoardResponseDto> findBoardByStu_no(Long stu_no) {
        return boardRepository.findByStu_no(stu_no);
    }


    // 자소서 저장 save
    @Transactional
    public Long save(Long experience_no, BoardSaveRequestDto boardSaveRequestDto) {
        Experience experience=experienceRepository.findByExperience_no(experience_no);
        return boardRepository.save(boardSaveRequestDto.toEntity(experience)).getBoard_no();
    }


    // 자소서 수정 update
    @Transactional
    public boolean update(Long board_no, BoardUpdateRequestDto boardUpdateRequestDto) {
        Board board = boardRepository.findByBoard_no(board_no);
        board.update(boardUpdateRequestDto.getBoard_question(), boardUpdateRequestDto.getBoard_content());
        return true;
    }

    // 자소서 삭제 delete
    @Transactional
    public void delete(Long board_no){
        Board board = boardRepository.findByBoard_no(board_no);
        boardRepository.delete(board);
    }
}

package com.back.admin.service;

import com.back.admin.domain.board.Board;
import com.back.admin.domain.board.BoardRepository;
import com.back.admin.domain.experience.Experience;
import com.back.admin.domain.experience.ExperienceRepository;
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


    public List<Board> selectAll() {
        return boardRepository.findAll();
    }


    @Transactional
    public Board findBoardByUser_no(Long user_no) {
        return boardRepository.findByStu_no(user_no);
    }


    @Transactional
    public boolean save(Long experience_no, BoardSaveRequestDto boardSaveRequestDto) {
        Experience experience=experienceRepository.findByExperience_no(experience_no);
        boardRepository.save(boardSaveRequestDto.toEntity(experience));
        return true;
    }


    @Transactional
    public boolean update(Long board_no, Long user_no, BoardUpdateRequestDto boardUpdateRequestDto) {
        Board board = boardRepository.findByBoard_no(board_no);
        Long board_user_id = board.getExperienceboard().getStudentexperience().getUser_no();
        if (board_user_id.equals(user_no)) {
            board.update(boardUpdateRequestDto.getBoard_question(), boardUpdateRequestDto.getBoard_content(),
                    boardUpdateRequestDto.getBoard_company(),boardUpdateRequestDto.getBoard_keyword());
            return true;
        } else {
            return false;
        }
    }


    @Transactional
    public boolean delete(Long board_no, Long user_no){
        Board board = boardRepository.findByBoard_no(board_no);
        Long board_user = board.getExperienceboard().getStudentexperience().getUser_no();
        if (board_user.equals(user_no)) {
            boardRepository.delete(board);
            return true;
        } else {
            return false;
        }
    }


    @Transactional
    public Board findByCompany(String board_company) {
        return boardRepository.findByBoard_company(board_company);
    }


    @Transactional
    public Board findByKeyword(String board_keyword) {
        return boardRepository.findByBoard_keyword(board_keyword);
    }

}

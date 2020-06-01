package com.back.admin.service;

import com.back.admin.domain.so_question.SolQuestion;
import com.back.admin.domain.so_question.SolQuestionRepository;
import com.back.admin.web.dto.sol_question.SolQuestionSaveRequestDto;
import com.back.admin.web.dto.sol_question.SolQuestionUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SolQuestionService {
    private final SolQuestionRepository solQuestionRepository;

    // 모든 질문 보여주기 selectAll
    public List<SolQuestion> selectAll() {
        return solQuestionRepository.findAll();
    }


    // 특정 학생의 질문 보여주기 findBoardByStu_id
    @Transactional
    public SolQuestion findByStu_no(Long stu_no) {
        return solQuestionRepository.findByStu_no(stu_no);
    }


    // 질문 저장 save
    @Transactional
    public boolean save(Long stu_no, SolQuestionSaveRequestDto solQuestionSaveRequestDto) {
        SolQuestion solQuestion = solQuestionRepository.findByStu_no(stu_no);
        solQuestionRepository.save(solQuestionSaveRequestDto.toEntity(solQuestion));
        return true;
    }


    // 질문 수정 update
    @Transactional
    public boolean update(Long sol_q_no, Long stu_no, SolQuestionUpdateRequestDto solQuestionUpdateRequestDto) {
        SolQuestion solQuestion = solQuestionRepository.findBySol_q_no(sol_q_no);
        Long sol_q_stu_id = solQuestion.getStudentsolq().getStu_no();
        if (sol_q_stu_id.equals(stu_no)) { //수정 권한이 있어
            solQuestion.update(solQuestionUpdateRequestDto.getSol_q_title(), solQuestionUpdateRequestDto.getSol_q_company(),
                    solQuestionUpdateRequestDto.getSol_q_want_job(), solQuestionUpdateRequestDto.getSol_q_time(),
                    solQuestionUpdateRequestDto.getSol_q_content());
            return true;
        } else { //수정 권한이 없어
            return false;
        }
    }

    // 질문 삭제 delete
    @Transactional
    public boolean delete(Long sol_q_no, Long stu_no){
        SolQuestion solQuestion = solQuestionRepository.findBySol_q_no(sol_q_no);
        Long sol_q_stu = solQuestion.getStudentsolq().getStu_no();
        if (sol_q_stu.equals(stu_no)) {
            solQuestionRepository.delete(solQuestion);
            return true;
        } else {
            return false;
        }
    }

}

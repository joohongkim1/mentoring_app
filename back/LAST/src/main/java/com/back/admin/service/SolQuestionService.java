package com.back.admin.service;

import com.back.admin.domain.sol_question.SolQuestion;
import com.back.admin.domain.sol_question.SolQuestionRepository;
import com.back.admin.domain.user.User;
import com.back.admin.domain.user.UserRepository;
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
    private final UserRepository userRepository;

    // 모든 질문 보여주기 selectAll
    public List<SolQuestion> selectAll() {
        return solQuestionRepository.findAll();
    }


    // 특정 학생의 질문 보여주기 findBoardByStu_id
    @Transactional
    public SolQuestion findByStu_no(Long user_no) {
        return solQuestionRepository.findByUser_no(user_no);
    }

    // 특정 회사의 질문 보여주기
    @Transactional
    public SolQuestion findByCompany(String sol_q_company) {
        return solQuestionRepository.findByCompany(sol_q_company);
    }

    // 특정 sol_q_want_job(직무)의 질문 보여주기
    @Transactional
    public SolQuestion findByWant_job(String sol_q_want_job) {
        return solQuestionRepository.findByWant_job(sol_q_want_job);
    }


    // 질문 저장 save
    @Transactional
    public boolean save(Long user_no, SolQuestionSaveRequestDto solQuestionSaveRequestDto) {
        User student = userRepository.findByUser_no(user_no);
        solQuestionRepository.save(solQuestionSaveRequestDto.toEntity(student));
        return true;
    }


    // 질문 수정 update
    @Transactional
    public boolean update(Long sol_q_no, Long user_no, SolQuestionUpdateRequestDto solQuestionUpdateRequestDto) {
        SolQuestion solQuestion = solQuestionRepository.findBySol_q_no(sol_q_no);
        Long sol_q_stu_id = solQuestion.getStudentsolq().getUser_no();
        if (sol_q_stu_id.equals(user_no)) { //수정 권한이 있어
            solQuestion.update(solQuestionUpdateRequestDto.getSol_q_title(), solQuestionUpdateRequestDto.getSol_q_company(),
                    solQuestionUpdateRequestDto.getSol_q_want_job(), solQuestionUpdateRequestDto.getSol_q_content(),
                    solQuestionUpdateRequestDto.getSol_q_tag());
            return true;
        } else { //수정 권한이 없어
            return false;
        }
    }

    // 질문 삭제 delete
    @Transactional
    public boolean delete(Long sol_q_no, Long user_no){
        SolQuestion solQuestion = solQuestionRepository.findBySol_q_no(sol_q_no);
        Long sol_q_stu = solQuestion.getStudentsolq().getUser_no();
        if (sol_q_stu.equals(user_no)) {
            solQuestionRepository.delete(solQuestion);
            return true;
        } else {
            return false;
        }
    }

}

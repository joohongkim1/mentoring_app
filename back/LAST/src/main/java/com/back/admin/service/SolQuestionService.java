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


    public List<SolQuestion> selectAll() {
        return solQuestionRepository.findAll();
    }


    @Transactional
    public SolQuestion findByUser_no(Long user_no) {
        return solQuestionRepository.findByUser_no(user_no);
    }


    @Transactional
    public SolQuestion findByCompany(String sol_q_company) {
        return solQuestionRepository.findByCompany(sol_q_company);
    }


    @Transactional
    public SolQuestion findByWant_job(String sol_q_want_job) {
        return solQuestionRepository.findByWant_job(sol_q_want_job);
    }


    @Transactional
    public boolean save(Long user_no, SolQuestionSaveRequestDto solQuestionSaveRequestDto) {
        User student = userRepository.findByUser_no(user_no);
        solQuestionRepository.save(solQuestionSaveRequestDto.toEntity(student));
        return true;
    }


    @Transactional
    public boolean update(Long sol_q_no, Long user_no, SolQuestionUpdateRequestDto solQuestionUpdateRequestDto) {
        SolQuestion solQuestion = solQuestionRepository.findBySol_q_no(sol_q_no);
        Long sol_q_stu_id = solQuestion.getStudentsolq().getUser_no();
        if (sol_q_stu_id.equals(user_no)) {
            solQuestion.update(solQuestionUpdateRequestDto.getSol_q_title(), solQuestionUpdateRequestDto.getSol_q_company(),
                    solQuestionUpdateRequestDto.getSol_q_want_job(), solQuestionUpdateRequestDto.getSol_q_content(),
                    solQuestionUpdateRequestDto.getSol_q_tag());
            return true;
        } else {
            return false;
        }
    }


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

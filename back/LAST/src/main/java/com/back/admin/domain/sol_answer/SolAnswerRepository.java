package com.back.admin.domain.sol_answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SolAnswerRepository extends JpaRepository<SolAnswer,Long> {

    // 모든 질문 다 보여주기
    @Query("select a from SolAnswer a")
    List<SolAnswer> findAll();

    // 특정 질문 찾기 findBySolQ_no
    @Query("select a from SolAnswer a where a.sol_a_no=:sol_a_no")
    SolAnswer findBySol_a_no(@Param("sol_a_no") Long sol_a_no);

    // 특정 회사의 질문 보여주기 findBoardByStu_id
    @Query("select a from SolAnswer a where a.sol_q_a.sol_q_company=:sol_q_company")
    SolAnswer findByCompany(@Param("sol_q_company") String sol_q_company);

    // 특정 sol_q_want_job(직무)의 질문 보여주기 findBoardByStu_id
    @Query("select a from SolAnswer a where a.sol_q_a.sol_q_want_job=:sol_q_want_job")
    SolAnswer findByWant_job(@Param("sol_q_want_job") String sol_q_want_job);


}

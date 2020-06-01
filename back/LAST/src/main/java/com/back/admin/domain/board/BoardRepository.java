package com.back.admin.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {

    // 모든 자소서 다 보여주기
    @Query("select b from Board b")
    List<Board> findAll();

    // 특정 학생의 자소서 보여주기 findBoardByStu_id
    @Query("select b from Board b where b.experienceboard.stu_no=:stu_no")
    Board findByStu_no(@Param("stu_no") Long stu_no);

    // 특정 자소서 찾기 findByBoard_no
    @Query("select b from Board b where b.board_no=:board_no")
    Board findByBoard_no(@Param("board_no") Long board_no);
}

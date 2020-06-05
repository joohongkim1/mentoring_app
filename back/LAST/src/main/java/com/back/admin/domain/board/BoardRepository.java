package com.back.admin.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {

    @Query("select b from Board b")
    List<Board> findAll();

    @Query("select b from Board b where b.experienceboard.studentexperience.user_no=:user_no")
    Board findByStu_no(@Param("user_no") Long user_no);

    @Query("select b from Board b where b.board_no=:board_no")
    Board findByBoard_no(@Param("board_no") Long board_no);
}

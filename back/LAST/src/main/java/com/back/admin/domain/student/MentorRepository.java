package com.back.admin.domain.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// Query문 작성하는 곳!
public interface MentorRepository extends JpaRepository<Mentor,Long> {
    // stu_id 찾기
    @Query("select m from Mentor m where m.mentor_id=:mentor_id")
    Mentor findBymentor_id(@Param("mentor_id") String mentor_id);

    // stu_no 찾기 -> 나중에 혹시 몰라서 미리 작성함
    @Query("select m from Mentor m where m.mentor_no=:mentor_no")
    Mentor findBymentor_no(@Param("mentor_no") Long mentor_no);

    // 회원가입시 이메일 중복 확인
    @Query("select m from Mentor m where m.mentor_email=:mentor_email")
    List<Mentor> findByEmail(@Param("mentor_email") String mentor_email);

    // 회원가입시 아이디 중복 확인
    @Query("select m from Mentor m where m.mentor_id=:mentor_id")
    List<Mentor> checkBymentor_id(@Param("mentor_id") String mentor_id);

    // 아이디 찾기
    @Query("select m from Mentor m where m.mentor_name=:mentor_name and m.mentor_email=:mentor_email")
    List<Mentor> findByNameEmail(@Param("mentor_name") String mentor_name, @Param("mentor_email") String mentor_email);

    // password수정하기
    @Modifying
    @Query("UPDATE Mentor m set m.mentor_password =:mentor_password where m.mentor_id =:mentor_id")
    void updatePass(@Param("mentor_id") String mentor_id, @Param("mentor_password") String mentor_password);

}

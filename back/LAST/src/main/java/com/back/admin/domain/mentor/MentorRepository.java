package com.back.admin.domain.mentor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// Query문 작성하는 곳!
public interface MentorRepository extends JpaRepository<Mentor,Long> {
    // mentor_id 찾기
    @Query("select m from Mentor m where m.mentor_id_email=:mentor_id_email")
    Mentor findBymentor_id_email(@Param("mentor_id_email") String mentor_id_email);

    // mentor_no 찾기 -> 나중에 혹시 몰라서 미리 작성함
    @Query("select m from Mentor m where m.mentor_no=:mentor_no")
    Mentor findBymentor_no(@Param("mentor_no") Long mentor_no);

    // 회원가입시 이메일 중복 확인
//    @Query("select m from Mentor m where m.mentor_email=:mentor_email")
//    List<Mentor> findByEmail(@Param("mentor_email") String mentor_email);

    // 회원가입시 아이디 중복 확인
    @Query("select m from Mentor m where m.mentor_id_email=:mentor_id_email")
    List<Mentor> checkBymentor_id_email(@Param("mentor_id_email") String mentor_id_email);

    // 아이디 찾기
    @Query("select m from Mentor m where m.mentor_name=:mentor_name and m.mentor_id_email=:mentor_id_email")
    List<Mentor> findByNameEmail(@Param("mentor_name") String mentor_name, @Param("mentor_id_email") String mentor_id_email);

    // password 수정하기
    @Modifying
    @Query("UPDATE Mentor m set m.mentor_password =:mentor_password where m.mentor_id_email =:mentor_id_email")
    void updatePass(@Param("mentor_id_email") String mentor_id_email, @Param("mentor_password") String mentor_password);


    // mentor_auth 변경
    @Modifying
    @Query("update Mentor s set s.mentor_auth=:mentor_auth where s.mentor_id_email=:mentor_id_email")
    void change_mentor_auth(@Param("mentor_id_email") String mentor_id_email,@Param("mentor_auth") int mentor_auth);


    // 관리자페이지에서 mentor_auth 별로 보여주기
    @Query("select s from Mentor s where s.mentor_auth=:mentor_auth")
    List<Mentor> show_by_mentor_auth(@Param("mentor_auth") int mentor_auth);
}

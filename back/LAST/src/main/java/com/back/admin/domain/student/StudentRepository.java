package com.back.admin.domain.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// Query문 작성하는 곳!
public interface StudentRepository extends JpaRepository<Student,Long> {
    // stu_id 찾기
    @Query("select s from Student s where s.stu_id_email=:stu_id_email")
    Student findBystu_id_email(@Param("stu_id_email") String stu_id_email);

    // stu_no 찾기 -> 나중에 혹시 몰라서 미리 작성함
    @Query("select s from Student s where s.stu_no=:stu_no")
    Student findBystu_no(@Param("stu_no") Long stu_no);

//    // 회원가입시 이메일 중복 확인
//    @Query("select s from Student s where s.stu_id_email=:stu_email")
//    List<Student> findByEmail(@Param("stu_email") String stu_email);

    // 회원가입시 아이디 중복 확인
    @Query("select s from Student s where s.stu_id_email=:stu_id_email")
    List<Student> checkBystu_id_email(@Param("stu_id_email") String stu_id_email);

    // 아이디 찾기
    @Query("select s from Student s where s.stu_name=:stu_name and s.stu_id_email=:stu_id_email")
    List<Student> findByNameEmail(@Param("stu_name") String stu_name, @Param("stu_id_email") String stu_id_email);

    // password 수정
    @Modifying  // 수정하는 값이 생기는 경우 무조건 붙여야한다!
    @Query("UPDATE Student s set s.stu_password =:stu_password where s.stu_id_email =:stu_id_email")
    void updatePass(@Param("stu_id_email") String stu_id_email, @Param("stu_password") String stu_password);

    // stu_auth 변경
    @Modifying
    @Query("update Student s set s.stu_auth=:stu_auth where s.stu_id_email=:stu_id_email")
    void change_stu_auth(@Param("stu_id_email") String stu_id_email,@Param("stu_auth") int stu_auth);

    // 관리자페이지에서 stu_auth 별로 보여주기
    @Query("select s from Student s where s.stu_auth=:stu_auth")
    List<Student> show_by_stu_auth(@Param("stu_auth") int stu_auth);

}

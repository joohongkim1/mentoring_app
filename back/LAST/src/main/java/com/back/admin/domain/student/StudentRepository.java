package com.back.admin.domain.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// Query문 작성하는 곳!
public interface StudentRepository extends JpaRepository<Student,Long> {
    // stu_id 찾기
    @Query("select s from Student s where s.stu_id=:stu_id")
    Student findBystu_id(@Param("stu_id") String stu_id);

    // stu_no 찾기 -> 나중에 혹시 몰라서 미리 작성함
    @Query("select s from Student s where s.stu_no=:stu_no")
    Student findBystu_no(@Param("stu_no") Long stu_no);

    // 회원가입시 이메일 중복 확인
    @Query("select s from Student s where s.stu_email=:stu_email")
    List<Student> findByEmail(@Param("stu_email") String stu_email);

    // 회원가입시 아이디 중복 확인
    @Query("select s from Student s where s.stu_id=:stu_id")
    List<Student> checkBystu_id(@Param("uid") String stu_id);

    // 아이디 찾기
    @Query("select s from Student s where s.stu_name=:stu_name and s.stu_email=:stu_email")
    List<Student> findByNameEmail(@Param("stu_name") String stu_name, @Param("stu_email") String stu_email);


    @Modifying  // password수정하기?? 정확하게 알기!!
    @Query("UPDATE Student s set s.stu_password =:stu_password where s.stu_id =:stu_id")
    void updatePass(@Param("stu_id") String stu_id, @Param("stu_password") String stu_password);

}

package com.back.admin.domain.experience;

import com.back.admin.domain.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience,Long> {

    // 경험 날짜 빠른 순서대로 보여주기
    @Query("select e from Experience e order by e.experience_start asc")
    List<Experience> findAllByAsc();

    // 학생별 경험 보여주기
    @Query("select e from Experience e where e.stu_id=:stu_id")
    Long findExperienceByStu_id(@Param("stu_id") String stu_id);

}

package com.back.admin.domain.experience;

import com.back.admin.web.dto.experience.ExperienceResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience,Long> {

    // 경험 날짜 빠른 순서대로 보여주기
//    @Query("select e from Experience e order by e.experience_start asc")
//    List<Experience> findAllByAsc();

    // 학생별 경험 보여주기
    @Query("select e from Experience e where e.stu_no=:stu_no")
    List<ExperienceResponseDto> findByStu_no(@Param("stu_no") Long stu_no);

    // 하나하나경험 보여주기
    @Query("select e from Experience e where e.experience_no=:experience_no")
    Experience findByexperience_no(@Param("experience_no") Long experience_no);

    // 해당 경험 찾기 findByExperience_no
    @Query("select e from Experience e where e.experience_no=:experience_no")
    Experience findByExperience_no(@Param("experience_no") Long experience_no);

}

package com.back.admin.domain.experience;

import com.back.admin.web.dto.experience.ExperienceResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience,Long> {

    @Query("select e from Experience e where e.studentexperience.user_no=:user_no")
    List<ExperienceResponseDto> findByStu_no(@Param("user_no") Long user_no);

    @Query("select e from Experience e where e.experience_no=:experience_no")
    Experience findByExperience_no(@Param("experience_no") Long experience_no);

}

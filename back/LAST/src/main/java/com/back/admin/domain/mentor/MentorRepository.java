package com.back.admin.domain.mentor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// Query문 작성하는 곳!
public interface MentorRepository extends JpaRepository<Mentor,Long> {

    // mentor_no 찾기 -> 나중에 혹시 몰라서 미리 작성함
    @Query("select m from Mentor m where m.mentor_no=:mentor_no")
    Mentor findBymentor_no(@Param("mentor_no") Long mentor_no);

}

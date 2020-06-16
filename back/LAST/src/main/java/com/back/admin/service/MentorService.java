package com.back.admin.service;

import com.back.admin.domain.mentor.Mentor;
import com.back.admin.domain.mentor.MentorRepository;
import com.back.admin.domain.user.User;
import com.back.admin.domain.user.UserRepository;
import com.back.admin.web.dto.mentor.MentorSaveRequestDto;
import com.back.admin.web.dto.mentor.MentorUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorService {

    private final MentorRepository mentorRepository;
    private final UserRepository userRepository;

    public List<Mentor> selectAll() {
        return mentorRepository.findAll();
    }

    @Transactional
    public Mentor findByMentor_no(Long mentor_no) {
        return mentorRepository.findBymentor_no(mentor_no);
    }

    @Transactional
    public boolean save(Long user_no, MentorSaveRequestDto mentorSaveRequestDto) {
        User user = userRepository.findByUser_no(user_no);
        mentorRepository.save(mentorSaveRequestDto.toEntity(user));
        return true;
    }

    @Transactional
    public boolean update(Long mentor_no, Long user_no, MentorUpdateRequestDto mentorUpdateRequestDto) {
        Mentor mentor = mentorRepository.findBymentor_no(mentor_no);
        Long mentor_id = mentor.getMentor().getUser_no();
        if (mentor_id.equals(user_no)) {
            mentor.update(mentorUpdateRequestDto.getMentor_identification_url(),
                    mentorUpdateRequestDto.getMentor_company(), mentorUpdateRequestDto.getMentor_job());
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public boolean delete(Long mentor_no, Long user_no){
        Mentor mentor = mentorRepository.findBymentor_no(mentor_no);
        Long mentor_id = mentor.getMentor().getUser_no();
        if (mentor_id.equals(user_no)) {
            mentorRepository.delete(mentor);
            return true;
        } else {
            return false;
        }
    }
}

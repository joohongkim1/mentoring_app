package com.back.admin.service;

import com.back.admin.domain.experience.Experience;
import com.back.admin.domain.experience.ExperienceRepository;
import com.back.admin.domain.user.User;
import com.back.admin.domain.user.UserRepository;
import com.back.admin.web.dto.experience.ExperienceResponseDto;
import com.back.admin.web.dto.experience.ExperienceSaveRequestDto;
import com.back.admin.web.dto.experience.ExperienceUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final UserRepository userRepository;


    @Transactional
    public List<Experience> selectAll() {
        return experienceRepository.findAll();
    }


    @Transactional
    public Experience findByExperience(Long experience_no) {
        return experienceRepository.findByExperience_no(experience_no);
    }


    @Transactional
    public List<ExperienceResponseDto> findExperienceByStu_no(Long user_no) {
        return experienceRepository.findByStu_no(user_no);
    }


    @Transactional
    public void save(ExperienceSaveRequestDto experienceSaveRequestDto, Long user_no) {
        User student = userRepository.findByUser_no(user_no);
        experienceRepository.save(experienceSaveRequestDto.toEntity(student));
    }


    @Transactional
    public boolean update(Long experience_no, String user_id_email, ExperienceUpdateRequestDto experienceUpdateRequestDto) {
        Experience experience = experienceRepository.findByExperience_no(experience_no);
        String exp_user_id = experience.getStudentexperience().getUser_id_email();
        if (exp_user_id.equals(user_id_email)) {
            experience.update(experienceUpdateRequestDto.getExperience_start(), experienceUpdateRequestDto.getExperience_end(),
                    experienceUpdateRequestDto.getExperience_title(), experienceUpdateRequestDto.getExperience_content(),
                    experienceUpdateRequestDto.getExperience_tag());
            return true;
        } else {
            return false;
        }
    }


    @Transactional
    public boolean delete(Long experience_no, String user_id_email){
        Experience experience = experienceRepository.findByExperience_no(experience_no);
        String exp_user_id = experience.getStudentexperience().getUser_id_email();
        if (exp_user_id.equals(user_id_email)) {
            experienceRepository.delete(experience);
            return true;
        } else {
            return false;
        }
    }

}

package com.back.admin.service;

import com.back.admin.domain.experience.Experience;
import com.back.admin.domain.experience.ExperienceRepository;
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

    // 관리자가 모든 학생의 경험을 확인하기
    public List<Experience> selectAll() {
        return experienceRepository.findAll();
    }


    // 학생 개인의 경험 확인하기
    @Transactional
    public List<ExperienceResponseDto> findExperienceByStu_id(Long stu_no) {
        return experienceRepository.findByStu_no(stu_no);
    }

    // 개인의 경험을 연도별로 정리해주기


    // 경험 저장
    @Transactional
    public Long save(Long experience_no, ExperienceSaveRequestDto experienceSaveRequestDto) {
        Experience experience = experienceRepository.findByExperience_no(experience_no);
        return experienceRepository.save(experienceSaveRequestDto.toEntity()).getExperience_no();
    }


    // 경험 수정
    @Transactional
    public Long update(Long experience_no, ExperienceUpdateRequestDto experienceUpdateRequestDto) {
        Experience experience = experienceRepository.findByExperience_no(experience_no);
        experience.update(experienceUpdateRequestDto.getExperience_start(), experienceUpdateRequestDto.getExperience_end(),
                        experienceUpdateRequestDto.getExperience_title(), experienceUpdateRequestDto.getExperience_content());
        return experience_no;
    }


    // 경험 삭제
    @Transactional
    public void delete(Long experience_no){
        Experience experience = experienceRepository.findByExperience_no(experience_no);
        experienceRepository.delete(experience);
    }

}

package com.back.admin.web.dto;

import com.back.admin.service.ExperienceService;
import com.back.admin.service.jwt.JwtService;
import com.back.admin.service.jwt.UnauthorizedException;
import com.back.admin.web.dto.experience.ExperienceResponseDto;
import com.back.admin.web.dto.experience.ExperienceSaveRequestDto;
import com.back.admin.web.dto.experience.ExperienceUpdateRequestDto;
import com.back.admin.web.dto.student.StudentJwtResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/last/experience")
@RequiredArgsConstructor
public class ExperienceController {
    private final ExperienceService experienceService;
    private final JwtService jwtService;

    // 모든 경험 보여주기
    @ApiOperation("모든 학생의 경험을 보여준다")
    @GetMapping("/all")
    public List<Exception> selectAll() {
        return experienceService.selectAll();
    }

    // 특정 학생의 경험 보여주기
    @ApiOperation("특정 학생의 경험을 보여주기")
    @GetMapping("/{stu_no}")  // stu_no로 할지 stu_id로 할지 결정이 필요할것같아여~
    public List<ExperienceResponseDto> selectAll(@PathVariable Long stu_no) {
        return experienceService.findExperienceByStu_id(stu_no);
    }

    // 경험 저장
    @ApiOperation("경험 저장")
    @PostMapping("/save")
    public Map save(@RequestBody ExperienceSaveRequestDto experienceSaveRequestDto) {

    }



    // 경험 수정하기
    @ApiOperation("경험 수정 -> Authorization필요")
    @PutMapping("/update/experience/{experience_no}")
    public void update(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, @RequestBody ExperienceUpdateRequestDto experienceUpdateRequestDto) {
        String jwt = httpServletRequest.getHeader("Authorization");
        //유효성 검사
        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        StudentJwtResponseDto student=jwtService.getUser(jwt);
        Map<String,String> map=new HashMap<>();
        if(experienceUpdateRequestDto.getStu_no().equals(student.getStu_no())){ //수정할 권한이 있으면
            experienceService.experienceUpdate(experience_no,experienceUpdateRequestDto);
        }else throw new UnauthorizedException(); // 예외
    }

    // 경험 삭제하기



}

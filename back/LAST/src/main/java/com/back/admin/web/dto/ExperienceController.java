package com.back.admin.web.dto;

import com.back.admin.domain.experience.Experience;
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
    public List<Experience> selectAll() {
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
    @PostMapping("/save/{experience_no}")
    public Map save(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                    @PathVariable Long experience_no, @RequestBody ExperienceSaveRequestDto experienceSaveRequestDto) {
        String jwt = httpServletRequest.getHeader("Authorization");
        //유효성 검사
        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        StudentJwtResponseDto student=jwtService.getUser(jwt);
        Map<String,String> map=new HashMap<>();

        Long id = experienceService.save(experience_no, experienceSaveRequestDto); {
            map.put("result", "저장이 완료되었습니다~");
        }
        return map;
    }


    // 경험 수정하기
    @ApiOperation("경험 수정 -> Authorization필요")
    @PutMapping("/update/experience/{experience_no}")
    public Map update(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                      @RequestBody ExperienceUpdateRequestDto experienceUpdateRequestDto, @PathVariable Long experience_no) {
        String jwt = httpServletRequest.getHeader("Authorization");
        //유효성 검사
        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        StudentJwtResponseDto student=jwtService.getUser(jwt);
        Map<String,String> map=new HashMap<>();

            // 앞에꺼는 student타입이고 equals 뒤에있는 것은 long타입 -> 타입 맞춰줄 필요가 있다.
        if(experienceUpdateRequestDto.getStu_no().equals(student.getStu_no())){ //수정할 권한이 있으면
            experienceService.update(experience_no,experienceUpdateRequestDto);
            map.put("result","저장이 완료되었습니다~");
        }else map.put("result","저장에 실패하였습니다.");
        return map;
    }


    // 경험 삭제하기
    @ApiOperation("경험 삭제 -> Authorization필요(권한이 있을때 삭제)")
    @DeleteMapping("/delete/{experience_no}")
    public void delete(@PathVariable Long experience_no,
                      HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        experienceService.delete(experience_no);
    }
}

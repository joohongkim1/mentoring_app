package com.back.admin.web.dto;

import com.back.admin.domain.experience.Experience;
import com.back.admin.domain.student.Student;
import com.back.admin.service.ExperienceService;
import com.back.admin.service.StudentService;
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
@RequestMapping("/api/v2")
@RequiredArgsConstructor
public class ExperienceController {
    private final ExperienceService experienceService;
    private final StudentService studentService;
    private final JwtService jwtService;
    private Student student;


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
        return experienceService.findExperienceByStu_no(stu_no);
    }


    // 경험 저장
    @ApiOperation("경험 저장 -> 권한 있을 때")
    @PostMapping("/save/{stu_no}")
    public Map save(@PathVariable Long stu_no, HttpServletRequest httpServletRequest,
                    HttpServletResponse httpServletResponse, @RequestBody ExperienceSaveRequestDto experienceSaveRequestDto) {
        String jwt = httpServletRequest.getHeader("Authorization");
        //유효성 검사
        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        StudentJwtResponseDto user = jwtService.getUser(jwt);

        if (user.getStu_no().equals(stu_no)) {
            experienceService.save(experienceSaveRequestDto, stu_no);
        }
        Map<String, String> map = new HashMap<>();
        map.put("result", "경험이 저장되었습니다~");
        System.out.println("경험이 저장되었습니다~");

        return map;
    }


    // 경험 수정하기
    // service단에서 boolean으로 맞춰둠. equals부분 확인필요!!!
    @ApiOperation("경험 수정 -> 권한 있을 때")
    @PutMapping("/{exprience_no}")
    public Map update(@PathVariable Long exprience_no, HttpServletRequest httpServletRequest , @RequestBody ExperienceUpdateRequestDto experienceUpdateRequestDto) {
        String jwt = httpServletRequest.getHeader("Authorization");
        //유효성 검사
        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        StudentJwtResponseDto user=jwtService.getUser(jwt);
        Map<String,String> map=new HashMap<>();

        boolean state=experienceService.update(exprience_no,user.getStu_id_email(), experienceUpdateRequestDto);
        if(state){

            map.put("result","리뷰가 수정되었습니다~");
        }else{
            map.put("result","수정중 오류가 발생했습니다.");
        }
        return map;
    }


    // 경험 삭제하기
    @ApiOperation("경험 삭제 -> Authorization필요")
    @DeleteMapping("/{experience_no}")
    public Map delete(@PathVariable Long experience_no, HttpServletRequest httpServletRequest){
        String jwt = httpServletRequest.getHeader("Authorization");
        //유효성 검사
        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        StudentJwtResponseDto user=jwtService.getUser(jwt);

        Map<String,String> map=new HashMap<>();
        boolean experience=experienceService.delete(experience_no,user.getStu_id_email());
        if(experience){
            map.put("result","경험이 삭제되었습니다~");
        }else{
            map.put("result","삭제중 오류가 발생했습니다.");
        }
        return map;
    }
}

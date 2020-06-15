package com.back.admin.web;

import com.back.admin.domain.experience.Experience;
import com.back.admin.service.ExperienceService;
import com.back.admin.service.jwt.JwtService;
import com.back.admin.service.jwt.UnauthorizedException;
import com.back.admin.web.dto.experience.ExperienceResponseDto;
import com.back.admin.web.dto.experience.ExperienceSaveRequestDto;
import com.back.admin.web.dto.experience.ExperienceUpdateRequestDto;
import com.back.admin.web.dto.user.UserJwtResponseDto;
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
@RequestMapping("/api/e1")
@RequiredArgsConstructor
public class ExperienceController {
    private final ExperienceService experienceService;
    private final JwtService jwtService;


    @ApiOperation("모든 학생의 경험을 보여준다")
    @GetMapping()
    public List<Experience> selectAll() {
        return experienceService.selectAll();
    }


    @ApiOperation("특정 학생의 경험을 보여주기")
    @GetMapping("/{user_no}")
    public List<ExperienceResponseDto> selectAll(@PathVariable Long user_no) {
        return experienceService.findExperienceByStu_no(user_no);
    }


    @ApiOperation("경험 저장 -> 권한 있을 때")
    @PostMapping("/{user_no}")
    public Map save(@PathVariable Long user_no, HttpServletRequest httpServletRequest,
                    @RequestBody ExperienceSaveRequestDto experienceSaveRequestDto) {
        String jwt = httpServletRequest.getHeader("Authorization");
        //유효성 검사
        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        UserJwtResponseDto user = jwtService.getUser(jwt);

        if (user.getUser_no().equals(user_no)) {
            experienceService.save(experienceSaveRequestDto, user_no);
        }
        Map<String, String> map = new HashMap<>();
        map.put("result", "경험이 저장되었습니다~");
        System.out.println("경험이 저장되었습니다~");

        return map;
    }


    @ApiOperation("경험 수정 -> 권한 있을 때")
    @PutMapping("/{exprience_no}")
    public Map update(@PathVariable Long exprience_no, HttpServletRequest httpServletRequest,
                      @RequestBody ExperienceUpdateRequestDto experienceUpdateRequestDto) {
        String jwt = httpServletRequest.getHeader("Authorization");
        //유효성 검사
        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        UserJwtResponseDto user=jwtService.getUser(jwt);
        Map<String,String> map=new HashMap<>();

        boolean state=experienceService.update(exprience_no,user.getUser_id_email(), experienceUpdateRequestDto);
        if(state){

            map.put("result","리뷰가 수정되었습니다~");
        }else{
            map.put("result","수정중 오류가 발생했습니다.");
        }
        return map;
    }


    @ApiOperation("경험 삭제 -> Authorization필요")
    @DeleteMapping("/{experience_no}")
    public Map delete(@PathVariable Long experience_no, HttpServletRequest httpServletRequest){
        String jwt = httpServletRequest.getHeader("Authorization");
        //유효성 검사
        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        UserJwtResponseDto user=jwtService.getUser(jwt);

        Map<String,String> map=new HashMap<>();
        boolean experience=experienceService.delete(experience_no,user.getUser_id_email());
        if(experience){
            map.put("result","경험이 삭제되었습니다~");
        }else{
            map.put("result","삭제중 오류가 발생했습니다.");
        }
        return map;
    }

}


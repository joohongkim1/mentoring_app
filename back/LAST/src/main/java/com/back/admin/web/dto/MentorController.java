package com.back.admin.web.dto;

import com.back.admin.domain.mentor.Mentor;
import com.back.admin.service.MentorService;
import com.back.admin.service.jwt.JwtService;
import com.back.admin.service.jwt.UnauthorizedException;
import com.back.admin.web.dto.mentor.MentorSaveRequestDto;
import com.back.admin.web.dto.mentor.MentorUpdateRequestDto;
import com.back.admin.web.dto.sol_question.SolQuestionSaveRequestDto;
import com.back.admin.web.dto.sol_question.SolQuestionUpdateRequestDto;
import com.back.admin.web.dto.user.UserJwtResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/m1")
@RequiredArgsConstructor
public class MentorController {
    private final MentorService mentorService;
    private final JwtService jwtService;


    @ApiOperation("특정 학생의 질문을 보여주기")
    @GetMapping("/{mentor_no}")  // stu_no로 할지 stu_id로 할지 결정이 필요할것같아여~
    public Mentor selectAll(@PathVariable Long mentor_no) {
        return mentorService.findByMentor_no(mentor_no);
    }


    @ApiOperation("멘토 신청 -> 권한 있을 때")
    @PostMapping("/{user_no}")
    public Map save(HttpServletRequest httpServletRequest,
                    @PathVariable Long user_no, @RequestBody MentorSaveRequestDto mentorSaveRequestDto) {
        String jwt = httpServletRequest.getHeader("Authorization");

        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        UserJwtResponseDto student=jwtService.getUser(jwt);
        Map<String,String> map=new HashMap<>();

        if (mentorService.save(user_no, mentorSaveRequestDto)) {
            map.put("result", "멘토 신청이 되었습니다~");
            System.out.println("신청이 되었습니다~");
        } else {
            map.put("result", "멘토 신청에 실패했습니다...ㅠ");
            System.out.println("멘토 신청에 실패했습니다...ㅠ");
        }
        return map;
    }


    @ApiOperation("멘토 정보 수정 -> 권한 있을 때")
    @PutMapping("/{mentor_no}")
    public Map update(HttpServletRequest httpServletRequest, @PathVariable Long mentor_no,
                      @RequestBody MentorUpdateRequestDto mentorUpdateRequestDto) {
        String jwt = httpServletRequest.getHeader("Authorization");

        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        UserJwtResponseDto user=jwtService.getUser(jwt);
        Map<String,String> map=new HashMap<>();

        boolean question=mentorService.update(mentor_no,user.getUser_no(), mentorUpdateRequestDto);
        if(question){

            map.put("result","멘토 정보가 수정되었습니다~");
        }else{
            map.put("result","수정중 오류가 발생했습니다.");
        }
        return map;
    }


    @ApiOperation("질문 삭제 -> Authorization필요(권한이 있을때 삭제?)")
    @DeleteMapping("/{mentor_no}")
    public Map delete(@PathVariable Long mentor_no, HttpServletRequest httpServletRequest){
        String jwt = httpServletRequest.getHeader("Authorization");

        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        UserJwtResponseDto user=jwtService.getUser(jwt);

        Map<String,String> map=new HashMap<>();
        boolean question=mentorService.delete(mentor_no, user.getUser_no());
        if(question){
            map.put("result","멘토 정보가 삭제되었습니다~");
        }else{
            map.put("result","멘토 정보 삭제중 오류가 발생했습니다.");
        }
        return map;
    }
}

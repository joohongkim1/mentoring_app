package com.back.admin.web;

import com.back.admin.domain.sol_question.SolQuestion;
import com.back.admin.service.SolQuestionService;
import com.back.admin.service.jwt.JwtService;
import com.back.admin.service.jwt.UnauthorizedException;
import com.back.admin.web.dto.sol_question.SolQuestionSaveRequestDto;
import com.back.admin.web.dto.sol_question.SolQuestionUpdateRequestDto;
import com.back.admin.web.dto.user.UserJwtResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v4")
@RequiredArgsConstructor
public class SolQuestionController {

    private final SolQuestionService solQuestionService;
    private final JwtService jwtService;


    @ApiOperation("모든 학생의 질문을 보여준다")
    @GetMapping()
    public List<SolQuestion> selectAll() {
        return solQuestionService.selectAll();
    }


    @ApiOperation("특정 학생의 질문을 보여주기")
    @GetMapping("/{user_no}")  // stu_no로 할지 stu_id로 할지 결정이 필요할것같아여~
    public SolQuestion selectAll(@PathVariable Long user_no) {
        return solQuestionService.findByUser_no(user_no);
    }


    @ApiOperation("특정 회사 질문을 보여주기")
    @GetMapping("/{sol_q_company}")  // stu_no로 할지 stu_id로 할지 결정이 필요할것같아여~
    public SolQuestion findByCompany(@PathVariable String sol_q_company) {
        return solQuestionService.findByCompany(sol_q_company);
    }


    @ApiOperation("특정 직무 질문을 보여주기")
    @GetMapping("/{sol_q_want_job}")  // stu_no로 할지 stu_id로 할지 결정이 필요할것같아여~
    public SolQuestion findByWant_job(@PathVariable String sol_q_want_job) {
        return solQuestionService.findByWant_job(sol_q_want_job);
    }


    @ApiOperation("질문 저장 -> 권한 있을 때")
    @PostMapping("/{user_no}")
    public Map save(HttpServletRequest httpServletRequest,
                    @PathVariable Long user_no, @RequestBody SolQuestionSaveRequestDto solQuestionSaveRequestDto) {
        String jwt = httpServletRequest.getHeader("Authorization");

        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        UserJwtResponseDto user=jwtService.getUser(jwt);
        Map<String,String> map=new HashMap<>();

        if (user.getUser_no().equals(user_no)) {
            solQuestionService.save(user_no, solQuestionSaveRequestDto);
            map.put("result", "질문이 저장되었습니다~");
            System.out.println("질문이 저장되었습니다~");
        } else {
            map.put("result", "질문 저장에 실패했습니다...ㅠ");
            System.out.println("질문 저장에 실패했습니다...ㅠ");
        }

        return map;
    }


    @ApiOperation("질문 수정 -> 권한 있을 때")
    @PutMapping("/{sol_q_no}")
    public Map update(HttpServletRequest httpServletRequest, @PathVariable Long sol_q_no,
                      @RequestBody SolQuestionUpdateRequestDto solQuestionUpdateRequestDto) {
        String jwt = httpServletRequest.getHeader("Authorization");

        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        UserJwtResponseDto user=jwtService.getUser(jwt);
        Map<String,String> map=new HashMap<>();

        boolean question=solQuestionService.update(sol_q_no,user.getUser_no(), solQuestionUpdateRequestDto);
        if(question){

            map.put("result","질문이 수정되었습니다~");
        }else{
            map.put("result","수정중 오류가 발생했습니다.");
        }
        return map;
    }


    @ApiOperation("질문 삭제 -> Authorization필요(권한이 있을때 삭제?)")
    @DeleteMapping("/{sol_q_no}")
    public Map delete(@PathVariable Long sol_q_no, HttpServletRequest httpServletRequest){
        String jwt = httpServletRequest.getHeader("Authorization");

        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        UserJwtResponseDto user=jwtService.getUser(jwt);

        Map<String,String> map=new HashMap<>();
        boolean question=solQuestionService.delete(sol_q_no, user.getUser_no());
        if(question){
            map.put("result","자소서가 삭제되었습니다~");
        }else{
            map.put("result","자소서 삭제중 오류가 발생했습니다.");
        }
        return map;
    }
}

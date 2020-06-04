package com.back.admin.web.dto;

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
import javax.servlet.http.HttpServletResponse;
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


    // 모든 질문 보여주기
    @ApiOperation("모든 학생의 질문을 보여준다")
    @GetMapping("/all")
    public List<SolQuestion> selectAll() {
        return solQuestionService.selectAll();
    }


    // 특정 학생의 질문 보여주기
    @ApiOperation("특정 학생의 질문을 보여주기")
    @GetMapping("/{stu_no}")  // stu_no로 할지 stu_id로 할지 결정이 필요할것같아여~
    public SolQuestion selectAll(@PathVariable Long stu_no) {
        return solQuestionService.findByStu_no(stu_no);
    }


    // 특정 회사의 질문 보여주기
    @ApiOperation("특정 회사 질문을 보여주기")
    @GetMapping("/{sol_q_company}")  // stu_no로 할지 stu_id로 할지 결정이 필요할것같아여~
    public SolQuestion findByCompany(@PathVariable String sol_q_company) {
        return solQuestionService.findByCompany(sol_q_company);
    }


    // 특정 sol_q_want_job(직무)의 질문 보여주기
    @ApiOperation("특정 직무 질문을 보여주기")
    @GetMapping("/{sol_q_want_job}")  // stu_no로 할지 stu_id로 할지 결정이 필요할것같아여~
    public SolQuestion findByWant_job(@PathVariable String sol_q_want_job) {
        return solQuestionService.findByWant_job(sol_q_want_job);
    }


    // 질문 저장
    @ApiOperation("질문 저장 -> 권한 있을 때")
    @PostMapping("/{stu_no}")
    public Map save(HttpServletRequest httpServletRequest,
                    @PathVariable Long stu_no, @RequestBody SolQuestionSaveRequestDto solQuestionSaveRequestDto) {
        String jwt = httpServletRequest.getHeader("Authorization");
        //유효성 검사
        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        UserJwtResponseDto student=jwtService.getUser(jwt);
        Map<String,String> map=new HashMap<>();

        if (solQuestionService.save(stu_no, solQuestionSaveRequestDto)) {
            map.put("result", "질문이 저장되었습니다~");
            System.out.println("질문이 저장되었습니다~");
        } else {
            map.put("result", "질문 저장에 실패했습니다...ㅠ");
            System.out.println("질문 저장에 실패했습니다...ㅠ");
        }

        return map;
    }


    // 질문 수정
    @ApiOperation("질문 수정 -> 권한 있을 때")
    @PutMapping("/{sol_q_no}")
    public Map update(HttpServletRequest httpServletRequest, @PathVariable Long sol_q_no,
                      @RequestBody SolQuestionUpdateRequestDto solQuestionUpdateRequestDto) {
        String jwt = httpServletRequest.getHeader("Authorization");
        //유효성 검사
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

    // 질문 삭제
    @ApiOperation("질문 삭제 -> Authorization필요(권한이 있을때 삭제?)")
    @DeleteMapping("/{sol_q_no}")
    public Map delete(@PathVariable Long sol_q_no, HttpServletRequest httpServletRequest){
        String jwt = httpServletRequest.getHeader("Authorization");
        //유효성 검사
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

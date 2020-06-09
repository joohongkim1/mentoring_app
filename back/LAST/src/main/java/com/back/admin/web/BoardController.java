package com.back.admin.web;

import com.back.admin.domain.board.Board;
import com.back.admin.domain.experience.Experience;
import com.back.admin.service.BoardService;
import com.back.admin.service.ExperienceService;
import com.back.admin.service.jwt.JwtService;
import com.back.admin.service.jwt.UnauthorizedException;
import com.back.admin.web.dto.board.BoardSaveRequestDto;
import com.back.admin.web.dto.board.BoardUpdateRequestDto;
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
@RequestMapping("/api/b1")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final ExperienceService experienceService;
    private final JwtService jwtService;


    @ApiOperation("모든 학생의 자소서를 보여준다")
    @GetMapping("/all")
    public List<Board> selectAll() {
        return boardService.selectAll();
    }


    @ApiOperation("특정 학생의 자소서를 보여주기")
    @GetMapping("/{user_no}")  // stu_no로 할지 stu_id로 할지 결정이 필요할것같아여~
    public Board selectAll(@PathVariable Long user_no) {
        return boardService.findBoardByUser_no(user_no);
    }


    @ApiOperation("자소서 저장 -> 권한 있을 때")
    @PostMapping("/{experience_no}")
    public Map save(HttpServletRequest httpServletRequest,
                    @PathVariable Long experience_no, @RequestBody BoardSaveRequestDto boardSaveRequestDto) {
        String jwt = httpServletRequest.getHeader("Authorization");
        //유효성 검사
        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        UserJwtResponseDto user=jwtService.getUser(jwt);
        Map<String,String> map=new HashMap<>();
        Experience experience = experienceService.findByExperience(experience_no);

        if (user.getUser_no().equals(experience.getStudentexperience().getUser_no())) {
            boardService.save(experience_no, boardSaveRequestDto);
            map.put("result", "자소서가 저장되었습니다~");
            System.out.println("자소서가 저장되었습니다~");
        } else {
            map.put("result", "자소서 저장에 실패했습니다...ㅠ");
            System.out.println("자소서 저장에 실패했습니다...ㅠ");
        }
        return map;
    }


    @ApiOperation("자소서 수정 -> 권한 있을 때")
    @PutMapping("/{board_no}")
    public Map update(HttpServletRequest httpServletRequest,
                      @RequestBody BoardUpdateRequestDto boardUpdateRequestDto, @PathVariable Long board_no) {
        String jwt = httpServletRequest.getHeader("Authorization");
        //유효성 검사
        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        UserJwtResponseDto user=jwtService.getUser(jwt);
        Map<String,String> map=new HashMap<>();

        boolean state=boardService.update(board_no,user.getUser_no(), boardUpdateRequestDto);
        if(state){

            map.put("result","리뷰가 수정되었습니다~");
        }else{
            map.put("result","수정중 오류가 발생했습니다.");
        }
        return map;
    }


    @ApiOperation("경험 삭제 -> Authorization필요(권한이 있을때 삭제?)")
    @DeleteMapping("/{board_no}")
    public Map delete(@PathVariable Long board_no, HttpServletRequest httpServletRequest){
        String jwt = httpServletRequest.getHeader("Authorization");
        //유효성 검사
        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        UserJwtResponseDto user=jwtService.getUser(jwt);

        Map<String,String> map=new HashMap<>();
        boolean board=boardService.delete(board_no, user.getUser_no());
        if(board){
            map.put("result","자소서가 삭제되었습니다~");
        }else{
            map.put("result","자소서 삭제중 오류가 발생했습니다.");
        }
        return map;
    }


    @ApiOperation("특정 회사+년도로 자소서를 보여주기")
    @GetMapping("/{board_company}")
    public Board findByCompany(@PathVariable String board_company) {
        return boardService.findByCompany(board_company);
    }


    @ApiOperation("특정 키워드로 자소서를 보여주기")
    @GetMapping("/{board_keyword}")
    public Board findByKeyword(@PathVariable String board_keyword) {
        return boardService.findByKeyword(board_keyword);
    }

}

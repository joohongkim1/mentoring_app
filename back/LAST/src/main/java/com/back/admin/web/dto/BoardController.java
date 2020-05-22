package com.back.admin.web.dto;

import com.back.admin.domain.board.Board;
import com.back.admin.service.BoardService;
import com.back.admin.service.jwt.JwtService;
import com.back.admin.service.jwt.UnauthorizedException;
import com.back.admin.web.dto.board.BoardResponseDto;
import com.back.admin.web.dto.board.BoardSaveRequestDto;
import com.back.admin.web.dto.board.BoardUpdateRequestDto;
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
@RequestMapping("/last/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    // 인증을 해야하나???
    private final JwtService jwtService;


    // 모든 자소서 보여주기
    @ApiOperation("모든 학생의 자소서를 보여준다")
    @GetMapping("/all")
    public List<Board> selectAll() {
        return boardService.selectAll();
    }


    // 특정 학생의 자소서 보여주기
    @ApiOperation("특정 학생의 자소서를 보여주기")
    @GetMapping("/{stu_no}")  // stu_no로 할지 stu_id로 할지 결정이 필요할것같아여~
    public List<BoardResponseDto> selectAll(@PathVariable Long stu_no) {
        return boardService.findBoardByStu_no(stu_no);
    }


    // 자소서 저장
    @ApiOperation("자소서 저장 -> 권한 있을 때")
    @PostMapping("/save/{experience_no}")
    public Map save(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                    @PathVariable Long board_no, @RequestBody BoardSaveRequestDto boardSaveRequestDto) {
        String jwt = httpServletRequest.getHeader("Authorization");
        //유효성 검사
        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        StudentJwtResponseDto student=jwtService.getUser(jwt);
        Map<String,String> map=new HashMap<>();

        Long id = boardService.save(board_no, boardSaveRequestDto); {
            map.put("result", "저장이 완료되었습니다~");
        }
        return map;
    }


    // 자소서 수정
    @ApiOperation("자소서 수정 -> 권한 있을 때")
    @PutMapping("/update/experience/{board_no}")
    public Boolean update(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                      @RequestBody BoardUpdateRequestDto boardUpdateRequestDto, @PathVariable Long board_no) {
        String jwt = httpServletRequest.getHeader("Authorization");
        //유효성 검사
        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        StudentJwtResponseDto student=jwtService.getUser(jwt);
        Map<String,String> map=new HashMap<>();

        // 앞에꺼는 student타입이고 equals 뒤에있는 것은 long타입 -> 타입 맞춰줄 필요가 있다.
//        if(boardUpdateRequestDto.ge().equals(student.getStu_no())){ //수정할 권한이 있으면
        if (boardService.update(board_no,boardUpdateRequestDto)) {
            return true;
        }else return false;
    }

    // 자소서 삭제
    @ApiOperation("경험 삭제 -> Authorization필요(권한이 있을때 삭제?)")
    @DeleteMapping("/delete/{experience_no}")
    public void delete(@PathVariable Long board_no,
                       HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        boardService.delete(board_no);
    }

}

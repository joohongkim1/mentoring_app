package com.back.admin.web.dto;

import com.back.admin.domain.student.Student;
import com.back.admin.service.StudentService;
import com.back.admin.service.jwt.CookieManage;
import com.back.admin.service.jwt.JwtService;
import com.back.admin.service.jwt.UnauthorizedException;
import com.back.admin.web.dto.student.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/last/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final JwtService jwtService;

    // 쿠키
    private CookieManage cm = new CookieManage();


    //모든 유저의 정보를 드린다.
    @ApiOperation("모든 유저의 정보를 출력합니다.")
    @GetMapping("/all")
    public List<Student> selectAll() {
        return studentService.selectAll();
    }


    // 회원 가입
    @ApiOperation("회원가입 진행")
    @PostMapping(value = "/signup", consumes = "application/json")
    public void signUp(@RequestBody StudentSaveRequestDto studentSaveRequestDto) {
        String secPass = encrypt(studentSaveRequestDto.getStu_password());
        studentSaveRequestDto.setStu_password(secPass);
        studentService.signUp(studentSaveRequestDto);
    }

    // 아이디 중복 확인(회원가입시)
    @ApiOperation("회원가입시 아이디 중복 확인")
    @PostMapping("/checkid/{stu_id}")
    public boolean checkId(@PathVariable String stu_id) {
        return studentService.checkId(stu_id);
    }

//    // 아이디 찾기
//    @PostMapping("/findid")
//    public Map findId(@RequestBody UserFindIdRequestDto userFindIdRequestDto) {
//        Map<String, String> map = new HashMap<>();
//        map.put("id", studentService.findId(userFindIdRequestDto.getUname(), userFindIdRequestDto.getUemail()));
//        return map;
//    }

    // 비밀번호 찾기
    @ApiOperation("비밀번호 찾기")
    @PostMapping("/findpass/{stu_id}/{stu_email}")
    public Map findPass(@PathVariable String stu_id, @PathVariable String stu_email) {
        Map<String, String> map = new HashMap<>();
        map.put("email", studentService.findPass(stu_id, stu_email));
        return map;
    }

    // 로그인
    @ApiOperation("로그인 -> Authorization 발행")
    @PostMapping("/signin")
    public Map signIn(@RequestBody StudentJwtRequestDto studentJwtRequestDto, HttpServletResponse response, HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        String secPass = encrypt(studentJwtRequestDto.getStu_password());
        StudentJwtResponseDto studentJwtResponseDto = studentService.signIn(studentJwtRequestDto.getStu_id(), secPass);
        if (studentJwtResponseDto != null && request.getCookies() == null) {
            String token = jwtService.create(studentJwtResponseDto);
            cm.CookieMake(request, response, token);
            map.put("token", token);
            return map;
        }
        map.put("token", request.getCookies()[0].getValue());
        return map;
    }


    // 회원 정보 수정
    @ApiOperation("회원정보 수정 -> Authorization필요(하면 500에러남)")
    @PutMapping("/update")
    public void update(HttpServletResponse response, HttpServletRequest request, @RequestBody StudentUpdateRequestDto studentUpdateRequestDto) {
        String jwt = request.getHeader("Authorization");
        if (!jwtService.isUsable(jwt)) return;
        StudentJwtResponseDto student = jwtService.getUser(jwt);
        studentService.update(student.getStu_id(), studentUpdateRequestDto);
        // 기존 토큰 죽이기
        cm.CookieDelete(request, response);
        //토큰 재발행
        System.out.println("토큰을 재발행합니다.");
        String token = jwtService.create(new StudentJwtResponseDto(studentService.findByuid(student.getStu_id())));
        cm.CookieMake(request, response, token);
    }


    // 로그아웃
    @ApiOperation("로그아웃 -> Authorization 필요")
    @GetMapping("/logout")
    public void logOut(HttpServletResponse response, HttpServletRequest request) {
        cm.CookieDelete(request, response);
    }


    // 삭제
    @ApiOperation("회원 탈퇴 -> Authorization필요(하면 500에러남)")
    @DeleteMapping("/delete")
    public void delete(@RequestBody StudentDeleteRequestDto studentDeleteRequestDto, HttpServletResponse response, HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        //유효성 검사
        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        StudentJwtResponseDto student = jwtService.getUser(jwt);

        if (student.getStu_id().equals(studentDeleteRequestDto.getStu_id())) {
            studentService.delete(student.getStu_id());
            Cookie cookie = request.getCookies()[0];
            cookie.setValue(null);
            cookie.setPath("/"); // <- 여기 잘 모르겠음
            cookie.setMaxAge(0);//나이 0살 - 죽은거야
            response.addCookie(cookie);
        } else throw new UnauthorizedException(); // 예외
    }


    // 암호화
    public static String encrypt(String rawpass) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(rawpass.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    static String Static_access_Token = null;


    // 학생 상태 변경 -> 일반:0, 우수:1
    @ApiOperation("학생 상태 변경 -> 일반:0, 우수:1")
    @PostMapping("/manage/stu_auth")
    public Student change_stu_auth(@RequestBody StudentAuthRequestDto studentAuthRequestDto) {
        Long stu_no = studentAuthRequestDto.getStu_no();
        int stu_auth = studentAuthRequestDto.getStu_auth();
        studentService.change_stu_auth(stu_no, stu_auth);
        return studentService.findBystu_no(stu_no);
    }

    // 학생 상태에 따른 리스트 보여주기
    @ApiOperation("학생 상태에 따른 리스트 보여주기")
    @PostMapping("/manage/{stu_auth}")
    public List<StudentResponseDto> show_by_stu_auth(@PathVariable int stu_auth) {
        return studentService.show_by_stu_auth(stu_auth);
    }


}

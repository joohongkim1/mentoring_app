package com.back.admin.web.dto;

import com.back.admin.domain.mentor.Mentor;
import com.back.admin.service.MentorService;
import com.back.admin.service.jwt.CookieManage;
import com.back.admin.service.jwt.JwtService;
import com.back.admin.service.jwt.UnauthorizedException;
import com.back.admin.web.dto.mentor.*;
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
@RequestMapping("/api/m1")
@RequiredArgsConstructor
public class MentorController {
    private final MentorService mentorService;
    private final JwtService jwtService;

    // 쿠키
    private CookieManage cm = new CookieManage();


    //모든 유저의 정보를 드린다.
    @ApiOperation("모든 유저의 정보를 출력합니다.")
    @GetMapping("/all")
    public List<Mentor> selectAll() {
        return mentorService.selectAll();
    }


    // 회원 가입
    @ApiOperation("회원가입 진행")
    @PostMapping(value = "/signup", consumes = "application/json")
    public void signUp(@RequestBody MentorSaveRequestDto mentorSaveRequestDto) {
        String secPass = encrypt(mentorSaveRequestDto.getMentor_password());
        mentorSaveRequestDto.setMentor_password(secPass);
        mentorService.signUp(mentorSaveRequestDto);
    }

    // 아이디 중복 확인(회원가입시)
    @ApiOperation("회원가입시 아이디 중복 확인")
    @PostMapping("/checkid/{mentor_id_email}")
    public boolean checkBymentor_id_email(@PathVariable String mentor_id_email) {
        return mentorService.checkBymentor_id_email(mentor_id_email);
    }

    // 아이디 찾기
//    @PostMapping("/findid")
//    public Map findId(@RequestBody UserFindIdRequestDto userFindIdRequestDto) {
//        Map<String, String> map = new HashMap<>();
//        map.put("id", mentorService.findId(userFindIdRequestDto.getUname(), userFindIdRequestDto.getUemail()));
//        return map;
//    }

    // 비밀번호 찾기 -> 이메일로 보내주기
    @ApiOperation("비밀번호 찾기")
    @PostMapping("/findpass/{mentor_id_email}")
    public Map findPass(@PathVariable String mentor_id_email) {
        Map<String, String> map = new HashMap<>();
        map.put("email", mentorService.findPass(mentor_id_email));
        return map;
    }

    // 로그인
    @ApiOperation("로그인 -> Authorization 발행")
    @PostMapping("/signin")
    public Map signIn(@RequestBody MentorJwtRequestDto mentorJwtRequestDto, HttpServletResponse response, HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        String secPass = encrypt(mentorJwtRequestDto.getMentor_password());
        MentorJwtResponseDto mentorJwtResponseDto = mentorService.signIn(mentorJwtRequestDto.getMentor_id_email(), secPass);
        if (mentorJwtResponseDto != null && request.getCookies() == null) {

            map.put("token", request.getCookies()[0].getValue());
            map.put("result", "성공");
            System.out.println("기존");
            System.out.println("token");
            System.out.println(request.getCookies()[0].getValue());
            return map;
        }
        String token = jwtService.create(mentorJwtResponseDto);
        cm.CookieMake(request, response, token);
        map.put("token", token);
        map.put("result", "성공");
        System.out.println("새롭게");
        System.out.println("token");
        System.out.println(token);
        return map;
    }


    // 회원 정보 수정
    @ApiOperation("회원정보 수정 -> Authorization필요")
    @PutMapping("")
    public void update(HttpServletResponse response, HttpServletRequest request,
                       @RequestBody MentorUpdateRequestDto mentorUpdateRequestDto) {
        String jwt = request.getHeader("Authorization");
        if (!jwtService.isUsable(jwt)) return;
        MentorJwtResponseDto mentor = jwtService.getMentor(jwt);
        // 비밀번호 encrypt(암호화 과정 필요)
        mentorService.update(mentor.getMentor_id_email(), mentorUpdateRequestDto);
        // 기존 토큰 죽이기
        cm.CookieDelete(request, response);
        //토큰 재발행
        System.out.println("토큰을 재발행합니다.");
        String token = jwtService.create(new MentorJwtResponseDto(mentorService.findBymentor_id(mentor.getMentor_id_email())));
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
    @DeleteMapping()
    public void delete(@RequestBody MentorDeleteRequestDto mentorDeleteRequestDto, HttpServletResponse response, HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        //유효성 검사
        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        MentorJwtResponseDto mentor = jwtService.getMentor(jwt);

        if (mentor.getMentor_id_email().equals(mentorDeleteRequestDto.getMentor_id_email())) {
            mentorService.delete(mentor.getMentor_id_email());
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
    @PutMapping("/mentor_auth")
    public Mentor change_mentor_auth(@RequestBody MentorAuthRequestDto mentorAuthRequestDto) {
        String mentor_id_email = mentorAuthRequestDto.getMentor_id_email();
        int mentor_auth = mentorAuthRequestDto.getMentor_auth();
        mentorService.change_mentor_auth(mentor_id_email, mentor_auth);
        System.out.println(mentorService.findBymentor_id_email(mentor_id_email));
        return mentorService.findBymentor_id_email(mentor_id_email);
    }

    // 학생 상태에 따른 리스트 보여주기
    @ApiOperation("학생 상태에 따른 리스트 보여주기")
    @PostMapping("/manage/{mentor_auth}")
    public List<MentorResponseDto> show_by_mentor_auth(@PathVariable int mentor_auth) {
        return mentorService.show_by_mentor_auth(mentor_auth);
    }
}

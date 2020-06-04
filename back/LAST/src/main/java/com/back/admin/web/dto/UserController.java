package com.back.admin.web.dto;

import com.back.admin.domain.user.User;
import com.back.admin.service.UserService;
import com.back.admin.service.jwt.CookieManage;
import com.back.admin.service.jwt.JwtService;
import com.back.admin.service.jwt.UnauthorizedException;
import com.back.admin.web.dto.user.*;
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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    // 쿠키
    private CookieManage cm = new CookieManage();


    //모든 유저의 정보를 드린다.
    @ApiOperation("모든 유저의 정보를 출력합니다.")
    @GetMapping("/all")
    public List<User> selectAll() {
        return userService.selectAll();
    }


    // 회원 가입
    @ApiOperation("회원가입 진행")
    @PostMapping(value = "/signup", consumes = "application/json")
    public void signUp(@RequestBody UserSaveRequestDto userSaveRequestDto) {
        String secPass = encrypt(userSaveRequestDto.getUser_password());
        userSaveRequestDto.setUser_password(secPass);
        userService.signUp(userSaveRequestDto);
    }

    // 아이디 중복 확인(회원가입시)
    @ApiOperation("회원가입시 아이디 중복 확인")
    @PostMapping("/checkid/{stu_id_email}")
    public boolean checkBystu_id_email(@PathVariable String user_id_email) {
        return userService.checkBystu_id_email(user_id_email);
    }

    // 비밀번호 찾기 -> 이메일로 보내주기
    @ApiOperation("비밀번호 찾기")
    @PostMapping("/findpass/{stu_id_email}")
    public Map findPass(@PathVariable String user_id_email) {
        Map<String, String> map = new HashMap<>();
        map.put("email", userService.findPass(user_id_email));
        return map;
    }

    // 로그인
    @ApiOperation("로그인 -> Authorization 발행")
    @PostMapping("/signin")
    public Map signIn(@RequestBody UserJwtRequestDto userJwtRequestDto, HttpServletResponse response, HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        String secPass = encrypt(userJwtRequestDto.getUser_password());
        UserJwtResponseDto userJwtResponseDto = userService.signIn(userJwtRequestDto.getUser_id_email(), secPass);
        if (userJwtResponseDto != null && request.getCookies() == null) {

            map.put("token", request.getCookies()[0].getValue());
            map.put("result", "성공");
            System.out.println("기존");
            System.out.println("token");
            System.out.println(request.getCookies()[0].getValue());
            return map;
        }
        String token = jwtService.create(userJwtResponseDto);
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
    @PutMapping()
    public Map update(HttpServletResponse response, HttpServletRequest request,
                      @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        Map<String, String> map = new HashMap<>();
        String jwt = request.getHeader("Authorization");
        if (!jwtService.isUsable(jwt)) {
            map.put("token", "불가능(실패)");
            return map;
        }

        UserJwtResponseDto student = jwtService.getUser(jwt);
        // 비밀번호 encrypt(암호화 과정 필요)
        userService.update(student.getUser_id_email(), userUpdateRequestDto);

        // 기존 토큰 죽이기
        cm.CookieDelete(request, response);
        //토큰 재발행
        System.out.println("토큰을 재발행합니다.");
        String token = jwtService.create(new UserJwtResponseDto(userService.findBystu_id(student.getUser_id_email())));
        cm.CookieMake(request, response, token);
        map.put("token", token);
        return map;

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
    public void delete(@RequestBody UserDeleteRequestDto userDeleteRequestDto,
                       HttpServletResponse response, HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        //유효성 검사
        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        UserJwtResponseDto student = jwtService.getUser(jwt);

        if (student.getUser_id_email().equals(userDeleteRequestDto.getUser_id_email())) {
            userService.delete(student.getUser_id_email());
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
    @PutMapping("/change/auth")
    public User change_stu_auth(@RequestBody UserAuthRequestDto userAuthRequestDto) {
        String stu_id_email = userAuthRequestDto.getUser_id_email();
        int stu_auth = userAuthRequestDto.getUser_auth();
        userService.change_stu_auth(stu_id_email, stu_auth);
        System.out.println(userService.findBystu_id_email(stu_id_email));
        return userService.findBystu_id_email(stu_id_email);
    }

    // 학생 상태에 따른 리스트 보여주기
    @ApiOperation("학생 상태에 따른 리스트 보여주기")
    @PostMapping("/manage/{stu_auth}")
    public List<UserResponseDto> show_by_stu_auth(@PathVariable int stu_auth) {
        return userService.show_by_stu_auth(stu_auth);
    }


}

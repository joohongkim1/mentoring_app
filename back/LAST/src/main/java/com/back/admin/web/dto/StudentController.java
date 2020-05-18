package com.back.admin.web.dto;

import com.back.admin.service.StudentService;
import com.back.admin.service.jwt.JwtService;
import com.back.admin.web.dto.student.StudentSaveRequestDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.util.HashMap;
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
    @PostMapping(value = "/signup", consumes = "application/json")
    public void signUp(@RequestBody StudentSaveRequestDto studentSaveRequestDto) {
        String secPass = encrypt(studentSaveRequestDto.getStu_password());
        studentSaveRequestDto.setStu_password(secPass);
        studentService.signUp(studentSaveRequestDto);
    }

    // 아이디 중복 확인(회원가입시)
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
    @PostMapping("/findpass/{uid}/{uemail}")
    public Map findPass(@PathVariable String uid, @PathVariable String uemail) {
        Map<String, String> map = new HashMap<>();
        map.put("email", userService.findPass(uid, uemail));
        return map;
    }

    // 회원 정보 수정 -> mypage에서 pass, nickname, phone 변경 가능
    @PutMapping("/update")
    public void update(HttpServletResponse response, HttpServletRequest request, @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        String jwt = request.getHeader("Authorization");
        if (!jwtService.isUsable(jwt)) return;
        UserJwtResponsetDto user = jwtService.getUser(jwt);
        userService.update(user.getUid(), userUpdateRequestDto);
        // 기존 토큰 죽이기

        cm.CookieDelete(request, response);
        //토큰 재발행
        System.out.println("토큰을 재발행합니다.");
        String token = jwtService.create(new UserJwtResponsetDto(userService.findByuid(user.getUid())));
        cm.CookieMake(request, response, token);
    }

    // 삭제
    @DeleteMapping("/delete")
    public void delete(@RequestBody UserDeleteRequestDto userDeleteRequestDto, HttpServletResponse response, HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        //유효성 검사
        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        UserJwtResponsetDto user = jwtService.getUser(jwt);

        if (user.getUid().equals(userDeleteRequestDto.getUid())) {
            userService.delete(user.getUid());
            Cookie cookie = request.getCookies()[0];
            cookie.setValue(null);
            cookie.setPath("/"); // <- 여기 잘 모르겠음
            cookie.setMaxAge(0);//나이 0살 - 죽은거야
            response.addCookie(cookie);
        } else throw new UnauthorizedException(); // 예외
    }

    // 로그인
    @ApiOperation("로그인하면서 토큰을 발행")
    @PostMapping("/signin")
    public Map signIn(@RequestBody UserJwtRequestDto userJwtRequestDto, HttpServletResponse response, HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        String secPass = encrypt(userJwtRequestDto.getUpass());
        UserJwtResponsetDto userJwtResponsetDto = userService.signIn(userJwtRequestDto.getUid(), secPass);
        if (userJwtResponsetDto != null && request.getCookies() == null) {
            String token = jwtService.create(userJwtResponsetDto);
            cm.CookieMake(request, response, token);
            map.put("token", token);
            return map;
        }
        map.put("token", request.getCookies()[0].getValue());
        return map;
    }

    @GetMapping("/logout")
    public void logOut(HttpServletResponse response, HttpServletRequest request) {
        cm.CookieDelete(request, response);
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










}

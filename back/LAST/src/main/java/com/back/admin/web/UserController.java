package com.back.admin.web;

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
@RequestMapping("/api/u1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
//    private final KakaoPayService kakaoPayService;
//    private static int TotalPayMoney;
//    private static String orderuserID;

    private CookieManage cm = new CookieManage();

//    static void init(){
//        TotalPayMoney=0;
//        orderuserID=null;
//    }

    @ApiOperation("모든 유저의 정보를 출력합니다.")
    @GetMapping()
    public List<User> selectAll() {
        return userService.selectAll();
    }


    @ApiOperation("회원가입 진행")
    @PostMapping(value = "/signup", consumes = "application/json")
    public void signUp(@RequestBody UserSaveRequestDto userSaveRequestDto) {
        String secPass = encrypt(userSaveRequestDto.getUser_password());
        userSaveRequestDto.setUser_password(secPass);
        userService.signUp(userSaveRequestDto);
    }


    @ApiOperation("회원가입시 아이디 중복 확인")
    @PostMapping("/checkid/{stu_id_email}")
    public boolean checkBystu_id_email(@PathVariable String user_id_email) {
        return userService.checkByuser_id_email(user_id_email);
    }


    @ApiOperation("비밀번호 찾기")
    @PostMapping("/findpass/{stu_id_email}")
    public Map findPass(@PathVariable String user_id_email) {
        Map<String, String> map = new HashMap<>();
        map.put("email", userService.findPass(user_id_email));
        return map;
    }


    @ApiOperation("로그인 -> Authorization 발행")
    @PostMapping("/signin")
    public Map signIn(@RequestBody UserJwtRequestDto userJwtRequestDto, HttpServletResponse response, HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        String secPass = encrypt(userJwtRequestDto.getUser_password());
        UserJwtResponseDto userJwtResponseDto = userService.signIn(userJwtRequestDto.getUser_id_email(), secPass);
        if (userJwtResponseDto != null && request.getCookies() == null) {

            map.put("token", request.getCookies()[0].getValue());
            map.put("result", "성공");
            return map;
        }
        String token = jwtService.create(userJwtResponseDto);
        cm.CookieMake(request, response, token);
        map.put("token", token);
        map.put("result", "성공");
        return map;
    }


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

        UserJwtResponseDto user = jwtService.getUser(jwt);
        // 비밀번호 encrypt(암호화 과정 필요)
        userService.update(user.getUser_id_email(), userUpdateRequestDto);

        // 기존 토큰 죽이기
        cm.CookieDelete(request, response);
        //토큰 재발행
        System.out.println("토큰을 재발행합니다.");
        String token = jwtService.create(new UserJwtResponseDto(userService.findByuser_id_email(user.getUser_id_email())));
        cm.CookieMake(request, response, token);
        map.put("token", token);
        return map;
    }


    @ApiOperation("로그아웃 -> Authorization 필요")
    @GetMapping("/logout")
    public void logOut(HttpServletResponse response, HttpServletRequest request) {
        cm.CookieDelete(request, response);
    }


    @ApiOperation("회원 탈퇴 -> Authorization필요(하면 500에러남)")
    @DeleteMapping()
    public void delete(@RequestBody UserDeleteRequestDto userDeleteRequestDto,
                       HttpServletResponse response, HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        //유효성 검사
        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
        UserJwtResponseDto user = jwtService.getUser(jwt);

        if (user.getUser_id_email().equals(userDeleteRequestDto.getUser_id_email())) {
            userService.delete(user.getUser_id_email());
            Cookie cookie = request.getCookies()[0];
            cookie.setValue(null);
            cookie.setPath("/"); // <- 여기 잘 모르겠음
            cookie.setMaxAge(0);//나이 0살 - 죽은거야
            response.addCookie(cookie);
        } else throw new UnauthorizedException(); // 예외
    }


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


    @ApiOperation("학생 상태 변경 -> 일반:0, 우수:1")
    @PutMapping("/change/auth")
    public User change_user_auth(@RequestBody UserAuthRequestDto userAuthRequestDto) {
        String user_id_email = userAuthRequestDto.getUser_id_email();
        int user_auth = userAuthRequestDto.getUser_auth();
        userService.change_user_auth(user_id_email, user_auth);
        System.out.println(userService.findByuser_id_email(user_id_email));
        return userService.findByuser_id_email(user_id_email);
    }


    @ApiOperation("학생 상태에 따른 리스트 보여주기")
    @PostMapping("/manage/{user_auth}")
    public List<UserResponseDto> show_by_user_auth(@PathVariable int user_auth) {
        return userService.show_by_user_auth(user_auth);
    }


//    @ApiOperation("유저가 카페에서 메뉴를 주문하는 기능입니다.")
//    @PostMapping("/latte/order")
//    public KakaoPayReadyVO save(@RequestBody OrderedRequestDto orderedRequestDto, HttpServletRequest httpServletRequest){
//        //요청할 때 들어오는 값 :메뉴번호(1개 필수),사이즈번호(1개 필수),옵션번호들(0개~여러개),총 가격- 이 모든게 리스트형태로 들어옴
//        String jwt = httpServletRequest.getHeader("Authorization");
//        //유효성 검사
//        if (!jwtService.isUsable(jwt)) throw new UnauthorizedException(); // 예외
//
//        UserJwtResponsetDto user=jwtService.getUser(jwt);
//
//        //현재 orderDetailRequestDtos에는 메뉴, 옵션 등의 정보가 담겨 있다.
//        //현재 초기 코드는 메뉴만 있다고 가정.
//
//        // 사용자확인
//        User orderuser=userService.findByuid(user.getUid());
//
//        System.out.println("현재 주문하는 유저는 : "+orderuser.getUname()+"님 입니다.");
//        Long cur_ooid=orderedService.save(orderuser,orderedRequestDto); //주문하기
//
//        //--------------------------위에서는 기존 orderDetail에 관련된 정보만 썼음---------------------
//        //아직 각 메뉴별 사이즈,옵션 정보 사용하지 않은 상태이다. 활용 가능
//        init();
//        TotalPayMoney=orderedRequestDto.getOprice();
//        orderuserID=orderuser.getUid();
//        ooid=cur_ooid;
//
//        return kakaoPayService.kakaoPayReady(orderuser,cur_ooid,orderedRequestDto); //카카오 페이
//    }
//
//
//    @GetMapping("/kakaoPaySuccess")
//    public KakaoPayApprovalRequestDto kakaoPaySuccess(@RequestParam("pg_token") String pg_token) {
//        log.info("kakaoPaySuccess get............................................");
//        log.info("kakaoPaySuccess pg_token : " + pg_token);
//        //프론트에서 이 상태를 봐야함.
//        return kakaoPayService.kakaoPayInfo(pg_token,ooid,orderuserID,TotalPayMoney);
//    }

}

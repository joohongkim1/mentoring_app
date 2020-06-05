package com.back.admin.service;

import com.back.admin.domain.user.User;
import com.back.admin.domain.user.UserRepository;
import com.back.admin.web.dto.user.UserJwtResponseDto;
import com.back.admin.web.dto.user.UserResponseDto;
import com.back.admin.web.dto.user.UserSaveRequestDto;
import com.back.admin.web.dto.user.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    JavaMailSender javaMailSender;

    // 관리자가 모든 학생을 확인할 때 사용할 것
    public List<User> selectAll() {
        return userRepository.findAll();
    }


    // 회원 가입
    @Transactional
    public boolean signUp(UserSaveRequestDto userSaveRequestDto) {
        System.out.println(userSaveRequestDto);
        // insert 전에 테이블을 검색해서 중복된 이메일이 있는지 확인한다.

        //우리 회원가입 로직은 이메일로만 중복검사를 실행합니다.!!!
        if (checkBystu_id_email(userSaveRequestDto.getUser_id_email())) //이미 이메일이 있으면
            return false;
        userRepository.save(userSaveRequestDto.toEntity());
        return true;
    }


    // 아이디 중복 확인 (있으면 true, 없으면 false)
    @Transactional
    public boolean checkBystu_id_email(String user_id_email) {
        List<User> user = userRepository.checkByUser_id_email(user_id_email);
        if (user.size() > 0) return true;
        else return false;
    }

    // 아이디 찾기
    @Transactional
    public String findId(String user_name, String user_email) {
        List<User> student = userRepository.findByNameEmail(user_name, user_email);
        if (student.size() == 1) {
            return student.get(0).getUser_id_email();
        } else {
            return "해당하는 정보가 없습니다.";
        }
    }

    // 비밀번호 찾기
    @Transactional
    public String findPass(String user_id_email) {
        if (!checkBystu_id_email(user_id_email))
            return "존재하지 않는 ID 입니다.";

        User student = userRepository.findByUser_id_email(user_id_email);

        if (student.getUser_id_email().equals(user_id_email)) {

            // 비밀번호 생성
            String new_pass = generatePass(10);
//            // 이메일로 비밀번호 쏴주고!!
            MailService mailService = new MailService();
            mailService.setJavaMailSender(javaMailSender);
            mailService.sendSimpleMessage(user_id_email, "[자취멘] 비밀번호 재설정", "비밀번호: " + new_pass);
            // 테이블에 있는 회원 비밀번호 그걸로 수정!!!!! -> 암호화
            userRepository.updatePass(user_id_email, SHA256Util.getEncrypt(new_pass));
        } else {
            new IllegalArgumentException("존재하지 않는 이메일입니다.");
        }
        return student.getUser_id_email();
    }

    // 비밀번호 생성 메소드
    public String generatePass(int length) {
        StringBuilder sb = new StringBuilder();
        char[] charSet = new char[]{
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
                , 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M'
                , 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
                , 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm'
                , 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
        };
        for (int i = 0; i < length; i++) {
            int index = (int) (charSet.length * Math.random());
            sb.append(charSet[index]);
        }

        return sb.toString();
    }

    //바뀐 유저 데이터에 대해서 토큰을 재발행 할 때 JwtUserRequest를 만들기 위한 작업으로 필요함.
    //DB까지 가지않고 서비스를 이용하여 끌고옴
    @Transactional
    public User findBystu_id(String user_id_email) {
        return userRepository.findByUser_id_email(user_id_email);
    }


    // 회원 정보 수정
    @Transactional
    public void update(String user_id_email, UserUpdateRequestDto userUpdateRequestDto) {
        User student = userRepository.findByUser_id_email(user_id_email);
        if (student == null) {
            throw new IllegalArgumentException("해당 사용자 없음");
        }

        assert student != null;  // 우리가 not null 안해놔서 붙인것!!
        student.update( userUpdateRequestDto.getUser_school(),
                userUpdateRequestDto.getUser_major(), encrypt(userUpdateRequestDto.getUser_password()));
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


    // 탈퇴(삭제)
    @Transactional
    public void delete(String user_id_email) {
        User student = userRepository.findByUser_id_email(user_id_email);
        if (student == null)
            new IllegalArgumentException("해당 사용자가 없습니다.");

        assert student != null;
        userRepository.delete(student);
    }


    // 로그인
    @Transactional
    public UserJwtResponseDto signIn(String user_id_email, String user_password) {
        User student = userRepository.findByUser_id_email(user_id_email);
        if (student == null) {
            assert student != null;

            System.out.println("사용자가 없습니다");
        }

        assert student != null;
        if (student.getUser_password().equals(user_password)) {
            return new UserJwtResponseDto(student);
        } else {
            System.out.println("아이디/비밀번호가 일치하지 않습니다.");
            return null;
        }

    }


    // 학생 상태 변경 -> 일반:0, 우수:1
    @Transactional
    public void change_stu_auth(String user_id_email,int user_auth){
        userRepository.change_User_auth(user_id_email,user_auth);
    }


    // stu_no로 학생 정보 가지고오기 , 이메일로 아이디 찾기
    @Transactional
    public User findBystu_no(Long user_no){
        return userRepository.findByUser_no(user_no);
    }

    // stu_id_email로 학생 정보 가지고오기
    @Transactional
    public User findBystu_id_email(String user_id_email){
        return userRepository.findByUser_id_email(user_id_email);
    }


    // 학생 상태에 따른 리스트 보여주기
    @Transactional
    public List<UserResponseDto> show_by_stu_auth(int user_auth) {
        return userRepository.findByUser_auth(user_auth).stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }
}

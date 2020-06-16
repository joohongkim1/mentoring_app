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

    public List<User> selectAll() {
        return userRepository.findAll();
    }

    @Transactional
    public boolean signUp(UserSaveRequestDto userSaveRequestDto) {
        System.out.println(userSaveRequestDto);

        if (checkByuser_id_email(userSaveRequestDto.getUser_id_email()))
            return false;
        userRepository.save(userSaveRequestDto.toEntity());
        return true;
    }

    @Transactional
    public boolean checkByuser_id_email(String user_id_email) {
        List<User> user = userRepository.checkByUser_id_email(user_id_email);
        if (user.size() > 0) return true;
        else return false;
    }

    @Transactional
    public String findId(String user_name, String user_email) {
        List<User> user = userRepository.findByNameEmail(user_name, user_email);
        if (user.size() == 1) {
            return user.get(0).getUser_id_email();
        } else {
            return "해당하는 정보가 없습니다.";
        }
    }

    @Transactional
    public String findPass(String user_id_email) {
        if (!checkByuser_id_email(user_id_email))
            return "존재하지 않는 ID 입니다.";

        User user = userRepository.findByUser_id_email(user_id_email);

        if (user.getUser_id_email().equals(user_id_email)) {
            String new_pass = generatePass(10);
            MailService mailService = new MailService();
            mailService.setJavaMailSender(javaMailSender);
            mailService.sendSimpleMessage(user_id_email, "[자취멘] 비밀번호 재설정", "비밀번호: " + new_pass);
            userRepository.updatePass(user_id_email, SHA256Util.getEncrypt(new_pass));
        } else {
            new IllegalArgumentException("존재하지 않는 이메일입니다.");
        }
        return user.getUser_id_email();
    }

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

    @Transactional
    public User findByuser_id_email(String user_id_email) {
        return userRepository.findByUser_id_email(user_id_email);
    }

    @Transactional
    public void update(String user_id_email, UserUpdateRequestDto userUpdateRequestDto) {
        User user = userRepository.findByUser_id_email(user_id_email);
        if (user == null) {
            throw new IllegalArgumentException("해당 사용자 없음");
        }

        assert user != null;
        user.update( userUpdateRequestDto.getUser_school(),
                userUpdateRequestDto.getUser_major(), encrypt(userUpdateRequestDto.getUser_password()));
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

    static String Static_access_Token = null;

    @Transactional
    public void delete(String user_id_email) {
        User user = userRepository.findByUser_id_email(user_id_email);
        if (user == null)
            new IllegalArgumentException("해당 사용자가 없습니다.");

        assert user != null;
        userRepository.delete(user);
    }

    @Transactional
    public UserJwtResponseDto signIn(String user_id_email, String user_password) {
        User user = userRepository.findByUser_id_email(user_id_email);
        if (user == null) {
            assert user != null;
            System.out.println("사용자가 없습니다");
        }

        assert user != null;
        if (user.getUser_password().equals(user_password)) {
            return new UserJwtResponseDto(user);
        } else {
            System.out.println("아이디/비밀번호가 일치하지 않습니다.");
            return null;
        }

    }

    @Transactional
    public void change_user_auth(String user_id_email,int user_auth){
        userRepository.change_User_auth(user_id_email,user_auth);
    }

    @Transactional
    public List<UserResponseDto> show_by_user_auth(int user_auth) {
        return userRepository.findByUser_auth(user_auth).stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }
}

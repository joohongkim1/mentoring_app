package com.back.admin.service;

import com.back.admin.domain.mentor.Mentor;
import com.back.admin.domain.mentor.MentorRepository;
import com.back.admin.web.dto.mentor.MentorJwtResponseDto;
import com.back.admin.web.dto.mentor.MentorResponseDto;
import com.back.admin.web.dto.mentor.MentorSaveRequestDto;
import com.back.admin.web.dto.mentor.MentorUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MentorService {
    private final MentorRepository mentorRepository;

    @Autowired
    JavaMailSender javaMailSender;

    // 관리자가 모든 학생을 확인할 때 사용할 것
    public List<Mentor> selectAll() {
        return mentorRepository.findAll();
    }


    // 회원 가입
    @Transactional
    public boolean signUp(MentorSaveRequestDto mentorSaveRequestDto) {
        System.out.println(mentorSaveRequestDto);
        // insert 전에 테이블을 검색해서 중복된 이메일이 있는지 확인한다.

        //우리 회원가입 로직은 이메일로만 중복검사를 실행합니다.!!!
        if (checkBymentor_id_email(mentorSaveRequestDto.getMentor_id_email())) //이미 이메일이 있으면
            return false;
        mentorRepository.save(mentorSaveRequestDto.toEntity());
        return true;
    }


    // 아이디 중복 확인 (있으면 true, 없으면 false)
    @Transactional
    public boolean checkBymentor_id_email(String mentor_id_email) {
        List<Mentor> mentor = mentorRepository.checkBymentor_id_email(mentor_id_email);
        if (mentor.size() > 0) return true;
        else return false;
    }

    // 아이디 찾기
    @Transactional
    public String findId(String mentor_name, String mentor_email) {
        List<Mentor> mentor = mentorRepository.findByNameEmail(mentor_name, mentor_email);
        if (mentor.size() == 1) {
            return mentor.get(0).getMentor_id_email();
        } else {
            return "해당하는 정보가 없습니다.";
        }
    }

    // 비밀번호 찾기
    @Transactional
    public String findPass(String mentor_id_email) {
        if (!checkBymentor_id_email(mentor_id_email))
            return "존재하지 않는 ID 입니다.";

        Mentor mentor = mentorRepository.findBymentor_id_email(mentor_id_email);

        if (mentor.getMentor_id_email().equals(mentor_id_email)) {

            // 비밀번호 생성
            String new_pass = generatePass(10);
//            // 이메일로 비밀번호 쏴주고!!
            MailService mailService = new MailService();
            mailService.setJavaMailSender(javaMailSender);
            mailService.sendSimpleMessage(mentor_id_email, "[자취멘] 비밀번호 재설정", "비밀번호: " + new_pass);
            // 테이블에 있는 회원 비밀번호 그걸로 수정!!!!! -> 암호화
            mentorRepository.updatePass(mentor_id_email, SHA256Util.getEncrypt(new_pass));
        } else {
            new IllegalArgumentException("존재하지 않는 이메일입니다.");
        }
        return mentor.getMentor_id_email();
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
    public Mentor findBymentor_id(String mentor_id_email) {
        return mentorRepository.findBymentor_id_email(mentor_id_email);
    }

    @Transactional
    public Mentor findByUuid(Long mentor_no) {
        return mentorRepository.findBymentor_no(mentor_no);
    }


    // 회원 정보 수정
    @Transactional
    public String update(String mentor_id_email, MentorUpdateRequestDto mentorUpdateRequestDto) {
        Mentor mentor = mentorRepository.findBymentor_id_email(mentor_id_email);
        if (mentor == null) {
            throw new IllegalArgumentException("해당사람 없음");
        }
        assert mentor != null;  // 우리가 not null 안해놔서 붙인것!!
        mentor.update(mentorUpdateRequestDto.getMentor_job(), mentorUpdateRequestDto.getMentor_major(),
                mentorUpdateRequestDto.getMentor_password());
        return mentor_id_email;
    }


    // 탈퇴(삭제)
    @Transactional
    public void delete(String mentor_id_email) {
        Mentor mentor = mentorRepository.findBymentor_id_email(mentor_id_email);
        if (mentor == null)
            new IllegalArgumentException("해당 사용자가 없습니다.");

        assert mentor != null;
        mentorRepository.delete(mentor);
    }

    // 로그인
    @Transactional
    public MentorJwtResponseDto signIn(String mentor_id_email, String mentor_password) {
        Mentor mentor = mentorRepository.findBymentor_id_email(mentor_id_email);
        if (mentor == null) {
            assert mentor != null;

            System.out.println("사용자가 없습니다");
        }

        assert mentor != null;
        if (mentor.getMentor_password().equals(mentor_password)) {
            return new MentorJwtResponseDto(mentor);
        } else {
            System.out.println("아이디/비밀번호가 일치하지 않습니다.");
            return null;
        }

    }


    // 학생 상태 변경 -> 일반:0, 우수:1
    @Transactional
    public void change_mentor_auth(String mentor_id_email,int mentor_auth){
        mentorRepository.change_mentor_auth(mentor_id_email,mentor_auth);
    }


    // mentor_no로 학생 정보 가지고오기 , 이메일로 아이디 찾기
    @Transactional
    public Mentor findBymentor_no(Long mentor_no){
        return mentorRepository.findBymentor_no(mentor_no);
    }

    // mentor_id_email로 학생 정보 가지고오기
    @Transactional
    public Mentor findBymentor_id_email(String mentor_id_email){
        return mentorRepository.findBymentor_id_email(mentor_id_email);
    }


    // 학생 상태에 따른 리스트 보여주기
    @Transactional
    public List<MentorResponseDto> show_by_mentor_auth(int mentor_auth) {
        return mentorRepository.show_by_mentor_auth(mentor_auth).stream()
                .map(MentorResponseDto::new)
                .collect(Collectors.toList());
    }
}

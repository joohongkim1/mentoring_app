package com.back.admin.service;

import com.back.admin.domain.student.Student;
import com.back.admin.domain.student.StudentRepository;
import com.back.admin.web.dto.student.StudentJwtResponseDto;
import com.back.admin.web.dto.student.StudentSaveRequestDto;
import com.back.admin.web.dto.student.StudentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    JavaMailSender javaMailSender;

    // 관리자가 모든 학생을 확인할 때 사용할 것
    public List<Student> selectAll() {
        return studentRepository.findAll();
    }

    // 이메일로 아이디 찾기
    @Transactional
    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email).get(0);
    }

    // 회원 가입
    @Transactional
    public boolean signUp(StudentSaveRequestDto studentSaveRequestDto) {
        System.out.println(studentSaveRequestDto);
        // insert 전에 테이블을 검색해서 중복된 이메일이 있는지 확인한다.

        //우리 회원가입 로직은 이메일로만 중복검사를 실행합니다.!!!
        if (checkEmail(studentSaveRequestDto.getStu_email())) //이미 이메일이 있으면
            return false;
        studentRepository.save(studentSaveRequestDto.toEntity());
        return true;
    }

    // 회원가입시 이메일 중복 확인
    @Transactional
    public boolean checkEmail(String stu_email) {
        List<Student> student = studentRepository.findByEmail(stu_email);
        if (student.size() > 0) return true; //있으면 1
        else return false; //없으면 0
    }

    // 아이디 중복 확인 (있으면 true, 없으면 false)
    @Transactional
    public boolean checkId(String stu_id) {
        List<Student> student = studentRepository.checkBystu_id(stu_id);
        if (student.size() > 0) return true;
        else return false;
    }

    // 아이디 찾기
    @Transactional
    public String findId(String stu_name, String stu_email) {
        List<Student> student = studentRepository.findByNameEmail(stu_name, stu_email);
        if (student.size() == 1) {
            return student.get(0).getStu_id();
        } else {
            return "해당하는 정보가 없습니다.";
        }
    }

    // 비밀번호 찾기
    @Transactional
    public String findPass(String stu_id, String stu_email) {
        if (!checkId(stu_id))
            return "존재하지 않는 ID 입니다.";

        Student student = studentRepository.findBystu_id(stu_id);

        if (student.getStu_email().equals(stu_email)) {

            // 비밀번호 생성
            String new_pass = generatePass(10);
//            // 이메일로 비밀번호 쏴주고!!
            MailService mailService = new MailService();
            mailService.setJavaMailSender(javaMailSender);
            mailService.sendSimpleMessage(stu_email, "[자취멘] 비밀번호 재설정", "비밀번호: " + new_pass);
            // 테이블에 있는 회원 비밀번호 그걸로 수정!!!!! -> 암호화
            studentRepository.updatePass(stu_id, SHA256Util.getEncrypt(new_pass));
        } else {
            new IllegalArgumentException("존재하지 않는 이메일입니다.");
        }
        return student.getStu_email();
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
    public Student findByuid(String stu_id) {
        return studentRepository.findBystu_id(stu_id);
    }

    @Transactional
    public Student findByUuid(Long stu_no) {
        return studentRepository.findBystu_no(stu_no);
    }


    // 회원 정보 수정
    @Transactional
    public String update(String stu_id, StudentUpdateRequestDto studentUpdateRequestDto) {
        Student student = studentRepository.findBystu_id(stu_id);
        if (student == null)
            new IllegalArgumentException("해당 사용자가 없습니다.");

        assert student != null;  // 우리가 not null 안해놔서 붙인것!!
        student.update(studentUpdateRequestDto.getStu_school(), studentUpdateRequestDto.getStu_major(),
                studentUpdateRequestDto.getStu_password());
        return stu_id;
    }

    // 탈퇴(삭제)
    @Transactional
    public void delete(String stu_id) {
        Student student = studentRepository.findBystu_id(stu_id);
        if (student == null)
            new IllegalArgumentException("해당 사용자가 없습니다.");

        assert student != null;
        studentRepository.delete(student);
    }

    // 로그인
    @Transactional
    public StudentJwtResponseDto signIn(String stu_id, String stu_password) {
        Student student = studentRepository.findBystu_id(stu_id);
        if (student == null)
            new IllegalArgumentException("해당 사용자가 없습니다.");


        assert student != null;
        if (student.getStu_password().equals(stu_password)) {
            return new StudentJwtResponseDto(student);
        } else {
            new IllegalArgumentException("아이디/비밀번호가 일치하지 않습니다.");
            return null;
        }
    }

}
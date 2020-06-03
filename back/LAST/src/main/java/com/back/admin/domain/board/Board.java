package com.back.admin.domain.board;

import com.back.admin.domain.experience.Experience;
import com.back.admin.domain.student.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class Board {
    @Id  // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto-increment
    private Long board_no;

    @Column(length = 500)
    private String board_question;

    @Column(columnDefinition = "TEXT")
    private String board_content;

    @Column(columnDefinition = "TEXT")
    private String board_when;


    // fix
    // experience:board = 1:N
    @ManyToOne(optional = false)
    @JsonBackReference
    private Experience experienceboard;

    @Builder
    public Board(String board_question, String board_content, String board_when, Experience experienceboard) {
        this.board_question = board_question;
        this.board_content = board_content;
        this.experienceboard = experienceboard;
        this.board_when = board_when;
    }

    // 자소서 update할때 자소서 문항도 update? 아니면 제공해줄 것?
    public void update(String board_question, String board_content, String board_when) {
        this.board_question = board_question;
        this.board_content = board_content;
        this.board_when = board_when;
    }
}

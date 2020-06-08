package com.back.admin.domain.board;

import com.back.admin.domain.experience.Experience;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long board_no;

    @Column(length = 500)
    private String board_question;

    @Column(columnDefinition = "TEXT")
    private String board_content;

    @Column(columnDefinition = "TEXT")
    private String board_company;

    @Column
    private String board_keyword;

    // fix
    // experience:board = 1:N
    @ManyToOne(optional = false)
    @JsonBackReference
    private Experience experienceboard;


    @Builder
    public Board(String board_question, String board_content, String board_company,
                 String board_keyword, Experience experienceboard) {
        this.board_question = board_question;
        this.board_content = board_content;
        this.experienceboard = experienceboard;
        this.board_company = board_company;
        this.board_keyword = board_keyword;
    }

    public void update(String board_question, String board_content,
                       String board_company, String board_keyword) {
        this.board_question = board_question;
        this.board_content = board_content;
        this.board_company = board_company;
        this.board_keyword = board_keyword;
    }
}

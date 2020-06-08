package com.back.admin.domain.experience;

import com.back.admin.domain.board.Board;
import com.back.admin.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long experience_no;

    @Column
    private Date experience_start;

    @Column
    private Date experience_end;

    @Column
    private String experience_title;

    @Column(length = 500)
    private String experience_content;

    @Column(length = 500)
    private String experience_tag;

    // fk -> 1:N = student:experience
    @ManyToOne(optional = false)
    @JsonBackReference
    private User studentexperience;

    // fk -> 1:N = experience:board
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "experienceboard")
    @JsonManagedReference
    private List<Board> boards=new ArrayList<>();


    @Builder
    public Experience(Date experience_start, Date experience_end, String experience_title,
                      String experience_content, String experience_tag, User studentexperience) {
        this.experience_start = experience_start;
        this.experience_end = experience_end;
        this.experience_title = experience_title;
        this.experience_content = experience_content;
        this.experience_tag = experience_tag;
        this.studentexperience = studentexperience;
    }

    public void update(Date experience_start, Date experience_end, String experience_title,
                       String experience_content, String experience_tag) {
        this.experience_start = experience_start;
        this.experience_end = experience_end;
        this.experience_title = experience_title;
        this.experience_content = experience_content;
        this.experience_tag = experience_tag;
    }
}

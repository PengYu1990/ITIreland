package com.hugo.itireland.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime ctime;
    private LocalDateTime utime;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "comment_user_fk"))
    private User user;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(
            name = "post_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "comment_post_fk"))
    private Post post;
}

package com.hugo.itireland.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "comments")
@DynamicUpdate
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String content;
    private LocalDateTime ctime;
    private LocalDateTime utime;

    // 0 normal, -1 delete

    @Column(name="state")
    private int state;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "comment_user_fk"))
    private User user;

    @ManyToOne
    @JoinColumn(
            name = "parent_comment_id",
            referencedColumnName = "id",
            nullable = true,
            foreignKey = @ForeignKey(name = "comment_comment_fk"))
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", fetch = FetchType.EAGER)
    @OrderBy("ctime DESC")
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(
            name = "post_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "comment_post_fk"))
    private Post post;
}

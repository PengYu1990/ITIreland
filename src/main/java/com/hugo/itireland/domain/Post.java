package com.hugo.itireland.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "posts")
@DynamicUpdate
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "post_user_fk"))
    private User user;
    private String title;

    @Column(columnDefinition = "text")
    private String content;

    private LocalDateTime ctime;
    private LocalDateTime utime;
    private int views;
    private int thumbs;
    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "posts_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag"),
            foreignKey = @ForeignKey(name="post_tag_fk"),
            inverseForeignKey = @ForeignKey(name="tag_post_fk")
    )
    private List<Tag> tags;

    @ManyToOne()
    @JoinColumn(
            name = "category",
            referencedColumnName = "category",
            nullable = false,
            foreignKey = @ForeignKey(name = "post_category_fk"))
    private Category category;

    @OneToMany(mappedBy = "post")
    @OrderBy("utime DESC")
    private List<Comment> comments;


}

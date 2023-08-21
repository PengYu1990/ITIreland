package com.hugo.itireland.domain;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Downvote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "post_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "post_id_fk")
    )
    private Post post;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "user_id_fk")
    )
    private User user;

    public Downvote(Post post, User user) {
        this.post = post;
        this.user = user;
    }

    public Downvote() {
    }
}

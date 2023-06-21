package com.hugo.itireland.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Following {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "following_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "following_id_fk")
    )
    private User following;

    @ManyToOne
    @JoinColumn(
            name = "follower_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "follower_id_fk")
    )
    private User follower;


    private LocalDateTime ctime;

    public Following(User following, User follower, LocalDateTime ctime) {
        this.following = following;
        this.follower = follower;
    }

    public Following() {

    }
}

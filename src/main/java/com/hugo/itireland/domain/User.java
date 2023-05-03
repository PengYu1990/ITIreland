package com.hugo.itireland.domain;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

/**
 * User Domain class
 */
@Data
@Entity
@Table(name = "users")
@DynamicUpdate
public class User {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;

    @Column(columnDefinition = "text")
    private String profile;

    //0:active, 1:disabled, 2:delete
    private int state;

    private int credits;

    private int level;

    private String headShotUrl;

    private LocalDateTime ctime;


}

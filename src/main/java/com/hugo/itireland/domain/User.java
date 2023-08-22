package com.hugo.itireland.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * User Domain class
 */
@Data
@Entity
@Table(name = "users")
@DynamicUpdate
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;

    @Column(columnDefinition = "text")
    private String profile;

    private String profileImageName;

    //0:active, -1:delete, -2:disabled
    private int state;

    private int credits;

    private int level;

    private String headShotUrl;

    private LocalDateTime ctime;

    private String location;


    @Enumerated(EnumType.STRING)
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return state == 0;
    }

}

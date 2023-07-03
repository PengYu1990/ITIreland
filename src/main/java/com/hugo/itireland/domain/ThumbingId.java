package com.hugo.itireland.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class ThumbingId implements Serializable {
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "user_id")
    private Long userId;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ThumbingId that = (ThumbingId) o;
//        return Objects.equals(postId, that.postId) && Objects.equals(userId, that.userId);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(postId, userId);
//    }
}

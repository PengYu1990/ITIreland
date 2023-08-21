package com.hugo.itireland.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@Table(name = "tags")
@DynamicUpdate
public class Tag {
    @Id
    private String tag;

    // 0 normal, -1 delete
    private int state;

}

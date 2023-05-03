package com.hugo.itireland.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@DynamicUpdate
public class Tag {
    @Id
    private String tag;

}

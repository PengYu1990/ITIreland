package com.hugo.itireland.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Tag {
    @Id
    private String tag;

}

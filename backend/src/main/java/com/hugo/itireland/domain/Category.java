package com.hugo.itireland.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@Table(name = "categories")
@DynamicUpdate
public class Category {

    public Category(){}
    public Category(String category){
        this.category = category;
    }
    @Id
    private String category;

    // 0 normal, -1 delete
    private int state;

    private int sort;

}

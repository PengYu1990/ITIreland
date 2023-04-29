package com.hugo.itireland.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Category {

    public Category(){}
    public Category(String category){
        this.category = category;
    }
    @Id
    private String category;

}

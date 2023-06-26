package com.example.ratingcommentservice.model.outer;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    private long id;
    private String username;
}

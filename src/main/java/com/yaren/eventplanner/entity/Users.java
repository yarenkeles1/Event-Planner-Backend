package com.yaren.eventplanner.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String fullName;

    @Column(unique = true, length = 100)
    private String email;

    @Column(length = 100)
    private String password;

}
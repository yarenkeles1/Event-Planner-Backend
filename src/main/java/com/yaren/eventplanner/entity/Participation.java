package com.yaren.eventplanner.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Users user;

    @ManyToOne
    private Event event;

}
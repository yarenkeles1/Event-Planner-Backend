package com.yaren.eventplanner.entity;

import com.yaren.eventplanner.util.EventStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String title;

    @Column(length = 200)
    private String location;

    @Column(length = 1000)
    private String description;

    @Column(length = 50)
    private String category;

    private java.time.LocalDate date;

    private java.time.LocalTime time;

    @ManyToOne
    private Users owner;

    @Enumerated(EnumType.STRING)
    private EventStatus status = EventStatus.PAUSED;

    private String imageUrl;

}
package com.example.hospitalProject.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private int id;

    @Column(name = "room_number", nullable = false, unique = true)
    private String roomNumber;

    @Column(name = "room_type")
    private String roomType;

    @Column(name = "status")
    private String status;

    // department_id
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments;
}

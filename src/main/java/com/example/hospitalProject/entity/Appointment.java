package com.example.hospitalProject.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private int id;

    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime appointmentDate;

    @Column(name = "appointment_time", nullable = false)
    private LocalDateTime appointmentTime;

    @Column(name = "reason")
    private String reason;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // patient_id
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    // doctor_id
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    // room_id
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Invoice invoice;
}

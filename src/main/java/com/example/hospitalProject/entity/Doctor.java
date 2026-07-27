package com.example.hospitalProject.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private int id;

    @Column(name = "full_name", length = 100, nullable = false)
    private String fullName;

    @Column(name = "specialization", length = 100)
    private String specialization;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "status")
    private String status;

    // department_id
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}

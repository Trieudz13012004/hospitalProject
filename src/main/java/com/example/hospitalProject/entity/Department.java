package com.example.hospitalProject.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private int id;

    @Column(name = "department_name", length = 100, nullable = false, unique = true)
    private String departmentName;

    @Column(name = "description")
    private String description;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Doctor> doctors;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Room> rooms;
}

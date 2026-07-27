package com.example.hospitalProject.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "medicines")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicine_id")
    private int id;

    @Column(name = "medicine_name", length = 150, nullable = false)
    private String medicineName;

    @Column(name = "unit")
    private String unit;

    @Column(name = "price")
    private double price;

    @Column(name = "stock_quantity")
    private int stockQuantity;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InvoiceMedicine> invoiceMedicines;
}

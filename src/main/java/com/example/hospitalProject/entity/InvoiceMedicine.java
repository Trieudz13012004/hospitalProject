package com.example.hospitalProject.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "invoice_medicines")
public class InvoiceMedicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "unit_price", nullable = false)
    private double unitPrice;

    @Column(name = "subtotal", nullable = false)
    private double subtotal;

    // invoice_id
    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    // medicine_id
    @ManyToOne
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;
}

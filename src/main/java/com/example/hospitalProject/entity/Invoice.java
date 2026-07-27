package com.example.hospitalProject.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private int id;

    @Column(name = "issue_date")
    private LocalDateTime issueDate;

    @Column(name = "examination_fee")
    private double examinationFee;

    @Column(name = "medicine_fee")
    private double medicineFee;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "payment_status")
    private String paymentStatus;

    // appointment_id
    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    private Appointment appointment;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InvoiceMedicine> invoiceMedicines;
}

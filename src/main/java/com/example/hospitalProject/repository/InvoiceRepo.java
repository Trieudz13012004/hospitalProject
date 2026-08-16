package com.example.hospitalProject.repository;

import com.example.hospitalProject.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice, Integer> {
    @Query("""
    SELECT DISTINCT i
    FROM Invoice i
    JOIN i.appointment a
    JOIN a.patient p
    WHERE
        LOWER(p.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR CAST(i.id AS string) LIKE CONCAT('%', :keyword, '%')
        OR CAST(a.id AS string) LIKE CONCAT('%', :keyword, '%')
        OR CAST(p.id AS string) LIKE CONCAT('%', :keyword, '%')
""")
    List<Invoice> searchInvoices(@Param("keyword") String keyword);
    @Query("""
    SELECT i
    FROM Invoice i
    WHERE i.id = :id
""")
    List<Invoice> findByInvoiceId(@Param("id") int id);
    @Query("""
    SELECT i
    FROM Invoice i
    JOIN i.appointment a
    WHERE a.id = :id
""")
    List<Invoice> findByAppointmentId(@Param("id") int id);
    @Query("""
    SELECT i
    FROM Invoice i
    JOIN i.appointment a
    JOIN a.patient p
    WHERE p.id = :id
""")
    List<Invoice> findByPatientId(@Param("id") int id);
}

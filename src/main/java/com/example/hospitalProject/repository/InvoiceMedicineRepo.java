package com.example.hospitalProject.repository;

import com.example.hospitalProject.entity.InvoiceMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceMedicineRepo extends JpaRepository<InvoiceMedicine, Integer> {
}

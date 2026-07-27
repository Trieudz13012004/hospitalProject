package com.example.hospitalProject.repository;

import com.example.hospitalProject.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepo extends JpaRepository<Medicine, Integer> {
}

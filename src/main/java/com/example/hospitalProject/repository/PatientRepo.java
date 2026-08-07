package com.example.hospitalProject.repository;

import com.example.hospitalProject.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Integer> {
    List<Patient> findByFullNameContainingIgnoreCaseOrPhoneContainingOrEmailContaining(
            String fullName,
            String phone,
            String email);
}

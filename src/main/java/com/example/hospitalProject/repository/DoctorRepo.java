package com.example.hospitalProject.repository;

import com.example.hospitalProject.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Integer> {
    List<Doctor> findByDepartmentId(int departmentId);
}

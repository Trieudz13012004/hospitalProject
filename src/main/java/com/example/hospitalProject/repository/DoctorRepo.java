package com.example.hospitalProject.repository;

import com.example.hospitalProject.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Integer> {
    List<Doctor> findByDepartmentId(int departmentId);
    @Query("""
    SELECT d
    FROM Doctor d
    LEFT JOIN d.department dp
    WHERE
        (   :keyword IS NULL
            OR :keyword = ''
            OR LOWER(d.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(d.specialization) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(d.phone) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(d.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(dp.departmentName) LIKE LOWER(CONCAT('%', :keyword, '%')))
        AND (
            :departmentId IS NULL
            OR dp.id = :departmentId
        )
        AND (
            :status IS NULL
            OR :status = ''
            OR d.status = :status
        )
""")
    List<Doctor> searchDoctors(
            @Param("keyword") String keyword,
            @Param("departmentId") Integer departmentId,
            @Param("status") String status
    );
}

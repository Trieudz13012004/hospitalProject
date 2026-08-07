package com.example.hospitalProject.repository;

import com.example.hospitalProject.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepo extends JpaRepository<Room, Integer> {
    // Lấy danh sách phòng theo khoa
    List<Room> findByDepartment_Id(int departmentId);

    // Lấy danh sách phòng trống
    List<Room> findByStatusIgnoreCase(String status);
}

package com.example.hospitalProject.repository;

import com.example.hospitalProject.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByDoctorId(int doctorId);
    List<Appointment> findByPatient_Id(int patientId);

    // Không cho trùng phòng
    boolean existsByRoomIdAndAppointmentDateAndAppointmentTime(
            int roomId,
            LocalDate date,
            LocalTime time
    );

    // Không cho khám 2 người cùng giờ
    boolean existsByDoctorIdAndAppointmentDateAndAppointmentTime(
            int doctorId,
            LocalDate date,
            LocalTime time
    );

    // Không cho bệnh nhân đặt 2 lịch cùng giờ
    boolean existsByPatientIdAndAppointmentDateAndAppointmentTime(
            int patientId,
            LocalDate date,
            LocalTime time
    );
}

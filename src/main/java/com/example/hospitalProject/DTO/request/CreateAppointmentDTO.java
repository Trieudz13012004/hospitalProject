package com.example.hospitalProject.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateAppointmentDTO {
    @NotNull(message = "Mã bênh nhân không được để trống")
    private Integer patientId;

    @NotNull(message = "Mã bác sĩ không được để trống")
    private Integer doctorId;

    @NotNull(message = "Mã phòng khám không được để trống")
    private Integer roomId;

    @NotNull(message = "Ngày khám không được để trống")
    private LocalDate appointmentDate;

    @NotNull(message = "Giờ khám không được để trống")
    private LocalTime appointmentTime;

    @NotBlank(message = "Lý do khám không được để trống")
    private String reason;

    public CreateAppointmentDTO() {
    }

    public CreateAppointmentDTO(Integer patientId, Integer doctorId, Integer roomId, LocalDate appointmentDate, LocalTime appointmentTime, String reason) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.roomId = roomId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.reason = reason;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

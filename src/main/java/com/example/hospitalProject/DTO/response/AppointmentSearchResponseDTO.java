package com.example.hospitalProject.DTO.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AppointmentSearchResponseDTO {
    private int appointmentId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String diagnosis;
    private String status;
    private String reason;
    private LocalDateTime createdAt;

    private int doctorId;
    private String doctorName;

    private String roomNumber;
    private String roomType;

    private int patientId;
    private String patientName;

    public AppointmentSearchResponseDTO() {
    }

    public AppointmentSearchResponseDTO(int appointmentId, LocalDate appointmentDate, LocalTime appointmentTime, String diagnosis, String status, String reason, LocalDateTime createdAt, int doctorId, String doctorName, String roomNumber, String roomType, int patientId, String patientName) {
        this.appointmentId = appointmentId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.diagnosis = diagnosis;
        this.status = status;
        this.reason = reason;
        this.createdAt = createdAt;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.patientId = patientId;
        this.patientName = patientName;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
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

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}

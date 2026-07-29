package com.example.hospitalProject.DTO.response;

public class DoctorDTO {
    private int doctorId;
    private String fullName;
    private String specialization;
    private String email;
    private String phone;
    private String status;

    public DoctorDTO() {
    }

    public DoctorDTO(int doctorId, String fullName, String specialization, String email, String phone, String status) {
        this.doctorId = doctorId;
        this.fullName = fullName;
        this.specialization = specialization;
        this.email = email;
        this.phone = phone;
        this.status = status;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

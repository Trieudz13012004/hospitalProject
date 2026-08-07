package com.example.hospitalProject.DTO.response;

public class DoctorResponseDTO {
    private int doctorId;
    private String fullName;
    private String specialization;
    private String departmentName;
    private String phone;
    private String email;
    private String status;

    public DoctorResponseDTO() {
    }

    public DoctorResponseDTO(int doctorId, String fullName, String specialization, String departmentName, String phone, String email, String status) {
        this.doctorId = doctorId;
        this.fullName = fullName;
        this.specialization = specialization;
        this.departmentName = departmentName;
        this.phone = phone;
        this.email = email;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

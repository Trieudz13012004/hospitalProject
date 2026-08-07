package com.example.hospitalProject.DTO.request;

import jakarta.validation.constraints.NotBlank;

public class CreateDoctorDTO {
    @NotBlank(message = "Tên bác sĩ không được để trống")
    private String fullName;
    @NotBlank(message = "Chuyên khoa không được để trống")
    private String specialization;
    @NotBlank(message = "Số điện thoại bác sĩ không được để trống")
    private String phone;
    @NotBlank(message = "Email không được để trống")
    private String email;
    @NotBlank(message = "Tên khoa không được để trống")
    private String departmentName;
    @NotBlank(message = "Trạng thái không được để trống")
    private String status;

    public CreateDoctorDTO() {
    }

    public CreateDoctorDTO(String fullName, String specialization, String phone, String email, String departmentName, String status) {
        this.fullName = fullName;
        this.specialization = specialization;
        this.phone = phone;
        this.email = email;
        this.departmentName = departmentName;
        this.status = status;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

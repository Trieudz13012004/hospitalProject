package com.example.hospitalProject.DTO.request;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class CreatePatientDTO {
    @NotBlank(message = "Tên bệnh nhân không được để trống")
    private String fullName;
    @NotBlank(message = "Ngày sinh không được để trống")
    private LocalDateTime dateOfBirth;
    @NotBlank(message = "Giới tính không được để trống")
    private String gender;
    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;
    @NotBlank(message = "Email không được để trống")
    private String email;
    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;

    public CreatePatientDTO() {
    }

    public CreatePatientDTO(String fullName, LocalDateTime dateOfBirth, String gender, String phone, String email, String address) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

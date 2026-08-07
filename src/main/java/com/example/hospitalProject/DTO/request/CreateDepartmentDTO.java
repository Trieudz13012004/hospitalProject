package com.example.hospitalProject.DTO.request;

import jakarta.validation.constraints.NotBlank;

public class CreateDepartmentDTO {
    @NotBlank(message = "Tên khoa không được để trống")
    private String departmentName;
    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;
    @NotBlank(message = "Mô tả không được để trống")
    private String description;

    public CreateDepartmentDTO() {
    }

    public CreateDepartmentDTO(String departmentName, String phone, String description) {
        this.departmentName = departmentName;
        this.phone = phone;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

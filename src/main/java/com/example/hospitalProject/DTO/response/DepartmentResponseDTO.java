package com.example.hospitalProject.DTO.response;

public class DepartmentResponseDTO {
    private int departmentId;
    private String departmentName;
    private String description;
    private String phone;
    private int numberOfDoctors;

    public DepartmentResponseDTO() {
    }

    public DepartmentResponseDTO(int departmentId, String departmentName, String description, String phone, int numberOfDoctors) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.description = description;
        this.phone = phone;
        this.numberOfDoctors = numberOfDoctors;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getNumberOfDoctors() {
        return numberOfDoctors;
    }

    public void setNumberOfDoctors(int numberOfDoctors) {
        this.numberOfDoctors = numberOfDoctors;
    }
}

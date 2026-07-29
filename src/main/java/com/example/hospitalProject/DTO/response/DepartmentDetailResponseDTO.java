package com.example.hospitalProject.DTO.response;

import java.util.List;

public class DepartmentDetailResponseDTO {
    private int departmentId;
    private String departmentName;
    private String description;
    private String phone;
    private List<DoctorDTO> doctors;

    public DepartmentDetailResponseDTO() {
    }

    public DepartmentDetailResponseDTO(int departmentId, String departmentName, String description, String phone, List<DoctorDTO> doctors) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.description = description;
        this.phone = phone;
        this.doctors = doctors;
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

    public List<DoctorDTO> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<DoctorDTO> doctors) {
        this.doctors = doctors;
    }
}

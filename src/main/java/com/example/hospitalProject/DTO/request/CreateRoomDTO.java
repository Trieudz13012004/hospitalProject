package com.example.hospitalProject.DTO.request;

import jakarta.validation.constraints.NotBlank;

public class CreateRoomDTO {
    @NotBlank(message = "Số phòng không được để trống")
    private String roomNumber;
    @NotBlank(message = "Loại phòng không được để trống")
    private String roomType;
    @NotBlank(message = "Trạng thái không được để trống")
    private String status;
    @NotBlank(message = "Tên khoa không được để trống")
    private String departmentName;

    public CreateRoomDTO() {
    }

    public CreateRoomDTO(String roomNumber, String roomType, String status, String departmentName) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.status = status;
        this.departmentName = departmentName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}

package com.example.hospitalProject.DTO.request;

public class CreateRoomDTO {
    private String roomNumber;
    private String roomType;
    private String status;
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

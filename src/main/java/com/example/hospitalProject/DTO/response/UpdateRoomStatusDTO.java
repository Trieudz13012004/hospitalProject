package com.example.hospitalProject.DTO.response;

public class UpdateRoomStatusDTO {
    private String status;

    public UpdateRoomStatusDTO() {
    }

    public UpdateRoomStatusDTO(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

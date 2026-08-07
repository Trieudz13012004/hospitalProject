package com.example.hospitalProject.service;

import com.example.hospitalProject.DTO.request.CreateRoomDTO;
import com.example.hospitalProject.DTO.response.RoomResponseDTO;
import com.example.hospitalProject.DTO.response.UpdateRoomStatusDTO;
import com.example.hospitalProject.entity.Department;
import com.example.hospitalProject.entity.Room;
import com.example.hospitalProject.repository.DepartmentRepo;
import com.example.hospitalProject.repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private DepartmentRepo departmentRepo;


    // GET /api/rooms
    // Danh sách tất cả phòng
    public List<RoomResponseDTO> getAllRooms() {
        List<Room> rooms = roomRepo.findAll();
        return rooms.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // GET  /api/rooms/{id}
    public RoomResponseDTO getRoomById(int roomId) {
        Room room = roomRepo.findById(roomId).orElseThrow(() -> new RuntimeException(
                "Không tìm thấy phòng với ID: " + roomId));
        return convertToDTO(room);
    }

    // GET /api/rooms/department/{departmentId}
    // Danh sách phòng theo khoa
    public List<RoomResponseDTO> getRoomsByDepartment(int departmentId) {
        // Kiểm tra khoa có tồn tại không
        departmentRepo.findById(departmentId).orElseThrow(() -> new RuntimeException(
                    "Không tìm thấy khoa với ID: " + departmentId));
        List<Room> rooms = roomRepo.findByDepartment_Id(departmentId);
        return rooms.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // GET /api/rooms/available
    // Danh sách phòng trống
    public List<RoomResponseDTO> getAvailableRooms() {
        List<Room> rooms = roomRepo.findByStatusIgnoreCase("Available");
        return rooms.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // DELETE  /api/rooms/{id}
    public void deleteRoom(int roomId) {
        Room room = roomRepo.findById(roomId).orElseThrow(() -> new RuntimeException(
                                "Không tìm thấy phòng với ID: " + roomId));
        roomRepo.delete(room);
    }

    // POST /api/rooms
    // Thêm phòng
    public RoomResponseDTO createRoom(CreateRoomDTO dto) {
        // Tìm khoa
        Department department = departmentRepo.findByDepartmentName(dto.getDepartmentName()).orElseThrow(() ->
                        new RuntimeException("Không tìm thấy khoa với ID: " + dto.getDepartmentName()));

        // Tạo phòng mới
        Room room = new Room();

        room.setRoomNumber(dto.getRoomNumber());
        room.setRoomType(dto.getRoomType());
        room.setStatus(dto.getStatus());
        room.setDepartment(department);

        // Lưu database
        Room savedRoom = roomRepo.save(room);

        // Trả về DTO
        return convertToDTO(savedRoom);
    }

    // PUT /api/rooms/{id}/status
    // Sửa trạng thái phòng

    public RoomResponseDTO updateRoomStatus(int id, UpdateRoomStatusDTO dto) {
        // Tìm phòng
        Room room = roomRepo.findById(id).orElseThrow(() -> new RuntimeException(
                                "Không tìm thấy phòng với ID: " + id));

        // Cập nhật trạng thái
        room.setStatus(dto.getStatus());

        // Lưu database
        Room updatedRoom = roomRepo.save(room);

        // Trả về DTO
        return convertToDTO(updatedRoom);
    }

    // Convert Room Entity -> RoomResponseDTO
    private RoomResponseDTO convertToDTO(Room room) {
        RoomResponseDTO dto = new RoomResponseDTO();

        dto.setRoomId(room.getId());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setRoomType(room.getRoomType());
        dto.setStatus(room.getStatus());

        // Lấy tên khoa
        if (room.getDepartment() != null) {
            dto.setDepartmentName(room.getDepartment().getDepartmentName());
        }
        return dto;
    }

    // GET /api/rooms/search
    public List<RoomResponseDTO> searchRooms(String keyword, String departmentName, String status) {
        List<Room> rooms = roomRepo.findAll();

        // Chuẩn hóa dữ liệu tìm kiếm
        String keywordValue = keyword == null ? "" : keyword.trim().toLowerCase();
        String departmentValue = departmentName == null ? "" : departmentName.trim().toLowerCase();
        String statusValue = status == null ? "" : status.trim().toLowerCase();
        return rooms.stream()

                // Tìm theo số phòng hoặc loại phòng
                .filter(room -> {
                    if (keywordValue.isEmpty()) {
                        return true;
                    }

                    String roomNumber = room.getRoomNumber() == null ? "" : room.getRoomNumber().toLowerCase();
                    String roomType = room.getRoomType() == null ? "" : room.getRoomType().toLowerCase();
                    return roomNumber.contains(keywordValue) || roomType.contains(keywordValue);
                })

                // Lọc theo khoa
                .filter(room -> {
                    if (departmentValue.isEmpty()) {
                        return true;
                    }

                    if (room.getDepartment() == null) {
                        return false;
                    }

                    String roomDepartmentName = room.getDepartment().getDepartmentName() == null
                                    ? ""
                                    : room.getDepartment()
                                    .getDepartmentName()
                                    .toLowerCase();
                    return roomDepartmentName.equals(departmentValue);
                })
                // Lọc theo trạng thái
                .filter(room -> {
                    if (statusValue.isEmpty()) {
                        return true;
                    }
                    String roomStatus = room.getStatus() == null ? "" : room.getStatus().toLowerCase();
                    return roomStatus.equals(statusValue);
                })
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private RoomResponseDTO mapToDTO(Room room) {
        RoomResponseDTO dto = new RoomResponseDTO();
        dto.setRoomId(room.getId());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setRoomType(room.getRoomType());
        dto.setStatus(room.getStatus());
        if (room.getDepartment() != null) {
            dto.setDepartmentName(
                    room.getDepartment().getDepartmentName()
            );
        }
        return dto;
    }

}

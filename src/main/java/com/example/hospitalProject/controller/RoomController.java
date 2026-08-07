package com.example.hospitalProject.controller;

import com.example.hospitalProject.DTO.request.CreateRoomDTO;
import com.example.hospitalProject.DTO.response.RoomResponseDTO;
import com.example.hospitalProject.DTO.response.UpdateRoomStatusDTO;
import com.example.hospitalProject.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping()
    public ResponseEntity<List<RoomResponseDTO>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> getRoomById(@PathVariable int id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<RoomResponseDTO>> getRoomsByDepartment(@PathVariable int departmentId) {
        return ResponseEntity.ok(roomService.getRoomsByDepartment(departmentId));
    }

    @GetMapping("/available")
    public ResponseEntity<List<RoomResponseDTO>> getAvailableRooms() {
        return ResponseEntity.ok(roomService.getAvailableRooms());
    }

    // GET /api/rooms/search
    @GetMapping("/search")
    public List<RoomResponseDTO> searchRooms(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "") String departmentName,
            @RequestParam(required = false, defaultValue = "") String status) {
        return roomService.searchRooms(keyword, departmentName, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable int id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok("Xóa phòng thành công với ID: " + id);
    }

    @PostMapping
    public ResponseEntity<RoomResponseDTO> createRoom(@RequestBody CreateRoomDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(dto));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<RoomResponseDTO> updateRoomStatus(@PathVariable int id, @RequestBody UpdateRoomStatusDTO dto) {
        return ResponseEntity.ok(roomService.updateRoomStatus(id, dto));
    }

}

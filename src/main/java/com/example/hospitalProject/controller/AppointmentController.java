package com.example.hospitalProject.controller;

import com.example.hospitalProject.DTO.request.CreateAppointmentDTO;
import com.example.hospitalProject.DTO.response.*;
import com.example.hospitalProject.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    // GET /api/appointments
    @GetMapping
    public List<AppointmentListResponseDTO> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    // GET /api/appointments/{id}
    @GetMapping("/{id}")
    public AppointmentDetailResponseDTO getAppointmentDetail(@PathVariable int id) {
        return appointmentService.getAppointmentDetail(id);
    }

    //  GET  /api/appointments/search
    @GetMapping("/search")
    public List<AppointmentSearchResponseDTO> searchAppointments(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate appointmentDate,
            @RequestParam(required = false, defaultValue = "") String doctor,
            @RequestParam(required = false, defaultValue = "") String status
    ) {
        return appointmentService.searchAppointments(
                keyword,
                appointmentDate,
                doctor,
                status
        );
    }

    // DELETE /api/appointments/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable int id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok("Xóa lịch khám thành công.");
    }

    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody CreateAppointmentDTO dto) {
        AppointmentAddResponseDTO response = appointmentService.createAppointment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

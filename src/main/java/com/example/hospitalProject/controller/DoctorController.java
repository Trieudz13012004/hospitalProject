package com.example.hospitalProject.controller;

import com.example.hospitalProject.DTO.request.CreateDoctorDTO;
import com.example.hospitalProject.DTO.response.DoctorDetailResponseDTO;
import com.example.hospitalProject.DTO.response.DoctorResponseDTO;
import com.example.hospitalProject.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    // GET /api/doctors
    @GetMapping
    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/search")
    public List<DoctorResponseDTO> searchDoctors(@RequestParam(required = false) String keyword,
                                                 @RequestParam(required = false) Integer departmentId,
                                                 @RequestParam(required = false) String status) {
        return doctorService.searchDoctors(keyword, departmentId, status);
    }

    // GET /api/doctors/{id}
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDetailResponseDTO> getDoctorDetail(@PathVariable int id) {
        DoctorDetailResponseDTO response = doctorService.getDoctorDetail(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable String id) {
        doctorService.deleteDoctor(Integer.parseInt(id));
        return ResponseEntity.ok("Doctor deleted successfully");
    }

    // POST /api/doctors
    @PostMapping
    public ResponseEntity<DoctorResponseDTO> createDoctor(@Valid @RequestBody CreateDoctorDTO dto) {
        DoctorResponseDTO doctor = doctorService.createDoctor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(doctor);
    }

    // PUT /api/doctors/{id}
    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> updateDoctor(@PathVariable int id, @Valid @RequestBody CreateDoctorDTO dto) {
        DoctorResponseDTO response = doctorService.updateDoctor(id, dto);
        return ResponseEntity.ok(response);
    }

    // [Dễ + Trung bình] — endpoint mới
    // GET http://localhost:8080/api/doctors/page?page=0&size=5
    @GetMapping("/page")
    public Page<DoctorResponseDTO> getAllDoctorsPage(
            @PageableDefault(sort = "fullName", direction = Sort.Direction.ASC
            ) Pageable pageable
    ) {
        return doctorService.getAllDoctorsPage(pageable);
    }

    // [Thử thách] — endpoint mới
    // GET /api/doctors/by-specialization?specialization=Tim mạch can thiệp&page=0&size=10&sort=createdAt,desc
    @GetMapping("/by-specialization")
    public Page<DoctorResponseDTO> getDoctorsBySpecialization(
            @RequestParam String specialization,
            Pageable pageable
    ) {
        return doctorService.getDoctorsBySpecialization(
                specialization,
                pageable
        );
    }
}

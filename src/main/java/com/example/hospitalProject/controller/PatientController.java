package com.example.hospitalProject.controller;

import com.example.hospitalProject.DTO.request.CreatePatientDTO;
import com.example.hospitalProject.DTO.response.PatientDetailResponseDTO;
import com.example.hospitalProject.DTO.response.PatientResponseDTO;
import com.example.hospitalProject.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;

    // GET  /api/patients
    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    // GET /api/patients/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PatientDetailResponseDTO> getPatientDetail(@PathVariable int id) {
        PatientDetailResponseDTO response = patientService.getPatientDetail(id);
        return ResponseEntity.ok(response);
    }

    // POST /api/patients
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@RequestBody CreatePatientDTO dto) {
        PatientResponseDTO response = patientService.createPatient(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // PUT /api/patients/{id}
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable int id, @RequestBody CreatePatientDTO dto) {
        PatientResponseDTO response = patientService.updatePatient(id, dto);
        return ResponseEntity.ok(response);
    }

    // DELETE /api/patients/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable int id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok("Xóa bệnh nhân thành công");
    }

    // GET /api/patients/search?keyword=
    // Tìm kiếm theo tên, SĐT, email
    @GetMapping("/search")
    public ResponseEntity<List<PatientResponseDTO>> searchPatients(@RequestParam String keyword) {
        return ResponseEntity.ok(patientService.searchPatients(keyword));
    }
}

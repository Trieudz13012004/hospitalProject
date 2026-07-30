package com.example.hospitalProject.controller;

import com.example.hospitalProject.DTO.request.CreateDepartmentDTO;
import com.example.hospitalProject.DTO.response.DepartmentDetailResponseDTO;
import com.example.hospitalProject.DTO.response.DepartmentResponseDTO;
import com.example.hospitalProject.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentResponseDTO>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDetailResponseDTO> getDepartmentDetail(@PathVariable String id) {
        return ResponseEntity.ok(departmentService.getDepartmentDetail(Integer.parseInt(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable String id) {
        departmentService.deleteDepartment(Integer.parseInt(id));
        return ResponseEntity.ok("Department deleted successfully");
    }

    @PostMapping
    public ResponseEntity<DepartmentResponseDTO> createDepartment(@RequestBody CreateDepartmentDTO dto) {
        DepartmentResponseDTO department = departmentService.createDepartment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(department);
    }

    // PUT /api/departments/{id}
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> updateDepartment(@PathVariable int id, @Valid @RequestBody CreateDepartmentDTO dto) {
        DepartmentResponseDTO response = departmentService.updateDepartment(id, dto);
        return ResponseEntity.ok(response);
    }
}

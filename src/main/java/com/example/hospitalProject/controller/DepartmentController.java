package com.example.hospitalProject.controller;

import com.example.hospitalProject.DTO.response.DepartmentDetailResponseDTO;
import com.example.hospitalProject.DTO.response.DepartmentResponseDTO;
import com.example.hospitalProject.entity.Department;
import com.example.hospitalProject.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<DepartmentResponseDTO>> getDepartment() {
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
}

package com.example.hospitalProject.service;

import com.example.hospitalProject.DTO.request.CreateDepartmentDTO;
import com.example.hospitalProject.DTO.response.DepartmentDetailResponseDTO;
import com.example.hospitalProject.DTO.response.DepartmentResponseDTO;
import com.example.hospitalProject.DTO.response.DoctorDTO;
import com.example.hospitalProject.entity.Department;
import com.example.hospitalProject.entity.Doctor;
import com.example.hospitalProject.repository.DepartmentRepo;
import com.example.hospitalProject.repository.DoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepo departmentRepo;
    @Autowired
    private DoctorRepo doctorRepo;

    // GET  /api/departments
    public List<DepartmentResponseDTO> getAllDepartments() {
        List<Department> departments = departmentRepo.findAll();
        List<DepartmentResponseDTO> responses = new ArrayList<>();
        for (Department department : departments) {
            DepartmentResponseDTO response = new DepartmentResponseDTO();

            response.setDepartmentId(department.getId());
            response.setDepartmentName(department.getDepartmentName());
            response.setPhone(department.getPhone());
            response.setDescription(department.getDescription());

            // Đếm bác sĩ của khoa
            int numberOfDoctors = doctorRepo.findByDepartmentId(department.getId()).size();
            response.setNumberOfDoctors(numberOfDoctors);
            responses.add(response);
        }
        return responses;
    }

    // GET department detail
    // GET  /api/departments/{id}
    public DepartmentDetailResponseDTO getDepartmentDetail(int id) {
        // Lấy department dựa vào id
        Department department = departmentRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department Not Found"));
        // Chuyển từ department sang departmentDetailResponseDTO
        DepartmentDetailResponseDTO dto = new DepartmentDetailResponseDTO();
        dto.setDepartmentId(department.getId());
        dto.setDepartmentName(department.getDepartmentName());
        dto.setDescription(department.getDescription());
        dto.setPhone(department.getPhone());
        List<DoctorDTO> doctorDTOS = new ArrayList<>();
        for (Doctor doctor : department.getDoctors()) {
            DoctorDTO doctorDTO = new DoctorDTO();
            doctorDTO.setDoctorId(doctor.getId());
            doctorDTO.setFullName(doctor.getFullName());
            doctorDTO.setSpecialization(doctor.getSpecialization());
            doctorDTO.setPhone(doctor.getPhone());
            doctorDTO.setEmail(doctor.getEmail());
            doctorDTO.setStatus(doctor.getStatus());
            doctorDTOS.add(doctorDTO);
        }
        dto.setDoctors(doctorDTOS);
        return dto;
    }

    // DELETE  /api/departments/{id}
    public void deleteDepartment(int id) {
        // Tìm department xem có hay ko
        Department department = departmentRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department Not Found"));
        departmentRepo.delete(department);
    }

    // POST  /api/departments
    public DepartmentResponseDTO createDepartment(CreateDepartmentDTO dto) {
        Department department = new Department();

        department.setDepartmentName(dto.getDepartmentName());
        department.setPhone(dto.getPhone());
        department.setDescription(dto.getDescription());

        Department savedDepartment = departmentRepo.save(department);

        DepartmentResponseDTO response = new DepartmentResponseDTO();

        response.setDepartmentId(savedDepartment.getId());
        response.setDepartmentName(savedDepartment.getDepartmentName());
        response.setPhone(savedDepartment.getPhone());
        response.setDescription(savedDepartment.getDescription());

        // Khoa mới chưa có bác sĩ
        response.setNumberOfDoctors(0);
        return response;
    }

    // PUT  /api/departments/{id}
    public DepartmentResponseDTO updateDepartment(int id, CreateDepartmentDTO dto) {
        Department department = departmentRepo.findById(id).orElseThrow(() -> new RuntimeException(
                                "Không tìm thấy khoa có id: " + id));

        // Cập nhật thông tin khoa
        department.setDepartmentName(dto.getDepartmentName());
        department.setPhone(dto.getPhone());
        department.setDescription(dto.getDescription());

        // Lưu database
        Department updatedDepartment = departmentRepo.save(department);

        // Tạo response
        DepartmentResponseDTO response = new DepartmentResponseDTO();

        response.setDepartmentId(updatedDepartment.getId());
        response.setDepartmentName(updatedDepartment.getDepartmentName());
        response.setPhone(updatedDepartment.getPhone());
        response.setDescription(updatedDepartment.getDescription());

        // Đếm số bác sĩ hiện tại của khoa
        int numberOfDoctors = doctorRepo.findByDepartmentId(id).size();
        response.setNumberOfDoctors(numberOfDoctors);
        return response;
    }
}

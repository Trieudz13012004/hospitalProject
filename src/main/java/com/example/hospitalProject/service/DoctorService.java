package com.example.hospitalProject.service;

import com.example.hospitalProject.DTO.request.CreateDoctorDTO;
import com.example.hospitalProject.DTO.response.AppointmentDTO;
import com.example.hospitalProject.DTO.response.DoctorDetailResponseDTO;
import com.example.hospitalProject.DTO.response.DoctorResponseDTO;
import com.example.hospitalProject.entity.Appointment;
import com.example.hospitalProject.entity.Department;
import com.example.hospitalProject.entity.Doctor;
import com.example.hospitalProject.repository.AppointmentRepo;
import com.example.hospitalProject.repository.DepartmentRepo;
import com.example.hospitalProject.repository.DoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {
    @Autowired
    private DepartmentRepo departmentRepo;
    @Autowired
    private DoctorRepo doctorRepo;
    @Autowired
    private AppointmentRepo appointmentRepo;

    // GET ALL /api/doctors
    public List<DoctorResponseDTO> getAllDoctors() {
        List<Doctor> doctors = doctorRepo.findAll();
        List<DoctorResponseDTO> doctorResponseDTOs = new ArrayList<>();
        for (Doctor doctor : doctors) {
            DoctorResponseDTO dto = new DoctorResponseDTO();

            dto.setDoctorId(doctor.getId());
            dto.setFullName(doctor.getFullName());
            dto.setSpecialization(doctor.getSpecialization());

            // Lấy tên khoa từ Department
            if (doctor.getDepartment() != null) {
                dto.setDepartmentName(doctor.getDepartment().getDepartmentName());
            }

            dto.setPhone(doctor.getPhone());
            dto.setEmail(doctor.getEmail());
            dto.setStatus(doctor.getStatus());

            doctorResponseDTOs.add(dto);
        }
        return doctorResponseDTOs;
    }

    // GET /api/doctors/{id}
    public DoctorDetailResponseDTO getDoctorDetail(int id) {

        // ========================= // 1. Tìm bác sĩ // =========================
        Doctor doctor = doctorRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy bác sĩ có ID: " + id));

        // ========================= // 2. Map Doctor -> DoctorDetailResponseDTO // =========================
        DoctorDetailResponseDTO response = new DoctorDetailResponseDTO();
        response.setDoctorId(doctor.getId());
        response.setFullName(doctor.getFullName());
        response.setSpecialization(doctor.getSpecialization());
        response.setPhone(doctor.getPhone());
        response.setEmail(doctor.getEmail());
        response.setStatus(doctor.getStatus());

        // ========================= // 3. Lấy tên khoa // =========================
        if (doctor.getDepartment() != null) {
            response.setDepartmentName(doctor.getDepartment().getDepartmentName());
        }

        // ========================= // 4. Lấy danh sách lịch khám // =========================
        List<Appointment> appointments = appointmentRepo.findByDoctorId(id);

        // ========================= // 5. Map Appointment -> AppointmentDTO // =========================
        List<AppointmentDTO> appointmentDTOs = appointments.stream().map(appointment -> {
            AppointmentDTO dto = new AppointmentDTO();
            // ID lịch khám
            dto.setAppointmentId(appointment.getId());
            // Ngày khám
            dto.setAppointmentDate(appointment.getAppointmentDate());
            // Giờ khám
            dto.setAppointmentTime(appointment.getAppointmentTime());
            // Trạng thái
            dto.setStatus(appointment.getStatus());

            // ========================= // Tên bệnh nhân // =========================
            if (appointment.getPatient() != null) {
                dto.setPatientName(appointment.getPatient().getFullName());
            } else {
                dto.setPatientName("-");
            }

            // ========================= // Số phòng // =========================
            if (appointment.getRoom() != null) {
                dto.setRoomNumber(appointment.getRoom().getRoomNumber());
            } else {
                dto.setRoomNumber("-");
            }
            return dto;
        }).toList();

        // ========================= // 6. Gán danh sách lịch khám // =========================
            response.setAppointments(appointmentDTOs);
            return response;
    }

    // DELETE  /api/doctors/{id}
    public void deleteDoctor(int id) {
        // Tìm department xem có hay ko
        Doctor doctor = doctorRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor Not Found"));
        doctorRepo.delete(doctor);
    }

    // POST /api/doctors
    public DoctorResponseDTO createDoctor(CreateDoctorDTO dto) {

        Doctor doctor = new Doctor();

        doctor.setFullName(dto.getFullName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setPhone(dto.getPhone());
        doctor.setEmail(dto.getEmail());
        doctor.setStatus(dto.getStatus());

        // Tìm khoa theo tên khoa
        Department department = departmentRepo
                .findByDepartmentName(dto.getDepartmentName()).orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy khoa có tên: " + dto.getDepartmentName()));
        // Gán khoa cho bác sĩ
        doctor.setDepartment(department);

        // Lưu database
        Doctor savedDoctor = doctorRepo.save(doctor);

        // Tạo response
        DoctorResponseDTO response = new DoctorResponseDTO();

        response.setDoctorId(savedDoctor.getId());
        response.setFullName(savedDoctor.getFullName());
        response.setSpecialization(savedDoctor.getSpecialization());
        response.setPhone(savedDoctor.getPhone());
        response.setEmail(savedDoctor.getEmail());
        response.setStatus(savedDoctor.getStatus());

        // Lấy tên khoa
        if (savedDoctor.getDepartment() != null) {
            response.setDepartmentName(savedDoctor.getDepartment().getDepartmentName());
        }
        return response;
    }

    // PUT /api/doctors/{id}
    public DoctorResponseDTO updateDoctor(int id, CreateDoctorDTO dto) {
        // Tìm bác sĩ
        Doctor doctor = doctorRepo.findById(id).orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy bác sĩ có id: " + id));

        // Cập nhật thông tin bác sĩ
        doctor.setFullName(dto.getFullName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setPhone(dto.getPhone());
        doctor.setEmail(dto.getEmail());
        doctor.setStatus(dto.getStatus());

        // Tìm khoa mới theo tên khoa
        Department department = departmentRepo.findByDepartmentName(dto.getDepartmentName()).orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy khoa có tên: " + dto.getDepartmentName()));

        // Cập nhật khoa
        doctor.setDepartment(department);

        // Lưu database
        Doctor updatedDoctor = doctorRepo.save(doctor);

        // Tạo response
        DoctorResponseDTO response = new DoctorResponseDTO();

        response.setDoctorId(updatedDoctor.getId());
        response.setFullName(updatedDoctor.getFullName());
        response.setSpecialization(updatedDoctor.getSpecialization());
        response.setPhone(updatedDoctor.getPhone());
        response.setEmail(updatedDoctor.getEmail());
        response.setStatus(updatedDoctor.getStatus());

        // Lấy tên khoa
        if (updatedDoctor.getDepartment() != null) {
            response.setDepartmentName(updatedDoctor.getDepartment().getDepartmentName());
        }
        return response;
    }

    // GET /api/doctors/search
    // /api/doctors/search?keyword=Nguyen&departmentId=2&status=Active
    public List<DoctorResponseDTO> searchDoctors(String keyword, Integer departmentId, String status) {
        List<Doctor> doctors = doctorRepo.searchDoctors(keyword, departmentId, status);
        List<DoctorResponseDTO> doctorResponseDTOs = new ArrayList<>();
        for (Doctor doctor : doctors) {
            DoctorResponseDTO dto = new DoctorResponseDTO();

            dto.setDoctorId(doctor.getId());
            dto.setFullName(doctor.getFullName());
            dto.setSpecialization(doctor.getSpecialization());
            dto.setPhone(doctor.getPhone());
            dto.setEmail(doctor.getEmail());
            dto.setStatus(doctor.getStatus());

            if (doctor.getDepartment() != null) {
                dto.setDepartmentName(doctor.getDepartment().getDepartmentName());
            }
            doctorResponseDTOs.add(dto);
        }
        return doctorResponseDTOs;
    }

    // [Dễ + Trung bình]
    public Page<DoctorResponseDTO> getAllDoctorsPage(Pageable pageable) {
        Page<Doctor> doctors = doctorRepo.findAll(pageable);
        return doctors.map(this::convertToDTO);
    }

    // [Thử thách]
    public Page<DoctorResponseDTO> getDoctorsBySpecialization(
            String specialization,
            Pageable pageable
    ) {
        Page<Doctor> doctors = doctorRepo.findBySpecialization(
                specialization,
                pageable
        );

        return doctors.map(this::convertToDTO);
    }

    private DoctorResponseDTO convertToDTO(Doctor doctor) {
        DoctorResponseDTO dto = new DoctorResponseDTO();

        dto.setDoctorId(doctor.getId());
        dto.setFullName(doctor.getFullName());
        dto.setSpecialization(doctor.getSpecialization());
        if (doctor.getDepartment() != null) {
            dto.setDepartmentName(
                    doctor.getDepartment().getDepartmentName()
            );
        }
        dto.setPhone(doctor.getPhone());
        dto.setEmail(doctor.getEmail());
        dto.setStatus(doctor.getStatus());
        return dto;
    }
}

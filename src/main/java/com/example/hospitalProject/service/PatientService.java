package com.example.hospitalProject.service;

import com.example.hospitalProject.DTO.request.CreatePatientDTO;
import com.example.hospitalProject.DTO.response.AppointmentResponseDTO;
import com.example.hospitalProject.DTO.response.PatientDetailResponseDTO;
import com.example.hospitalProject.DTO.response.PatientResponseDTO;
import com.example.hospitalProject.entity.Appointment;
import com.example.hospitalProject.entity.Patient;
import com.example.hospitalProject.repository.AppointmentRepo;
import com.example.hospitalProject.repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private AppointmentRepo appointmentRepo;

    // GET /api/patients
    public List<PatientResponseDTO> getAllPatients() {
        List<Patient> patients = patientRepo.findAll();
        return patients.stream().map(this::convertToDTO).toList();
    }

    // GET /api/patients/{id}
    // Chi tiết bệnh nhân + lịch khám

    public PatientDetailResponseDTO getPatientDetail(int id) {
        // 1. Tìm bệnh nhân
        Patient patient = patientRepo.findById(id).orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy bệnh nhân với ID: " + id));

        // 2. Tìm tất cả lịch khám của bệnh nhân
        List<Appointment> appointments = appointmentRepo.findByPatient_Id(id);

        // 3. Tạo response
        PatientDetailResponseDTO response = new PatientDetailResponseDTO();

        // 4. Map thông tin bệnh nhân
        response.setPatientId(patient.getId());
        response.setFullName(patient.getFullName());
        response.setDateOfBirth(patient.getDateOfBirth());
        response.setGender(patient.getGender());
        response.setPhone(patient.getPhone());
        response.setEmail(patient.getEmail());
        response.setAddress(patient.getAddress());
        response.setCreatedAt(patient.getCreatedAt());

        // 5. Map danh sách lịch khám
        List<AppointmentResponseDTO> appointmentDTOs =
                appointments.stream()
                        .map(appointment -> {
                            AppointmentResponseDTO dto = new AppointmentResponseDTO();
                            dto.setAppointmentId(appointment.getId());
                            dto.setAppointmentDate(appointment.getAppointmentDate());
                            dto.setAppointmentTime(appointment.getAppointmentTime());
                            dto.setReason(appointment.getReason());
                            dto.setDiagnosis(appointment.getDiagnosis());
                            dto.setStatus(appointment.getStatus());

                            // Doctor
                            if (appointment.getDoctor() != null) {
                                dto.setDoctorName(appointment.getDoctor().getFullName());
                                dto.setSpecialization(appointment.getDoctor().getSpecialization());
                            }

                            // Room
                            if (appointment.getRoom() != null) {
                                dto.setRoomName(appointment.getRoom().getRoomNumber());
                            }
                            return dto;
                        }).toList();
        response.setAppointments(appointmentDTOs);
        return response;
    }

    // POST /api/patients
    public PatientResponseDTO createPatient(CreatePatientDTO dto) {
        Patient patient = new Patient();

        patient.setFullName(dto.getFullName());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setGender(dto.getGender());
        patient.setPhone(dto.getPhone());
        patient.setEmail(dto.getEmail());
        patient.setAddress(dto.getAddress());

        // Tự động lưu thời gian tạo
        patient.setCreatedAt(LocalDateTime.now());
        Patient savedPatient = patientRepo.save(patient);
        return convertToDTO(savedPatient);
    }

    // PUT /api/patients/{id}
    public PatientResponseDTO updatePatient(int id, CreatePatientDTO dto) {
        Patient patient = patientRepo.findById(id).orElseThrow(() -> new RuntimeException(
                                "Không tìm thấy bệnh nhân có ID: " + id));
        patient.setFullName(dto.getFullName());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setGender(dto.getGender());
        patient.setPhone(dto.getPhone());
        patient.setEmail(dto.getEmail());
        patient.setAddress(dto.getAddress());

        Patient updatedPatient = patientRepo.save(patient);
        return convertToDTO(updatedPatient);
    }

    // 17. DELETE /api/patients/{id}
    public void deletePatient(int id) {
        Patient patient = patientRepo.findById(id).orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy bệnh nhân có ID: " + id));
        patientRepo.delete(patient);
    }

    // GET /api/patients/search?keyword=
    // Tìm theo tên, SĐT, email
    public List<PatientResponseDTO> searchPatients(String keyword) {
        List<Patient> patients = patientRepo.findByFullNameContainingIgnoreCaseOrPhoneContainingOrEmailContaining(
                                keyword,
                                keyword,
                                keyword);
        return patients.stream().map(this::convertToDTO).toList();
    }

    // Convert Patient Entity -> PatientResponseDTO
    private PatientResponseDTO convertToDTO(Patient patient) {
        PatientResponseDTO response = new PatientResponseDTO();

        response.setPatientId(patient.getId());
        response.setFullName(patient.getFullName());
        response.setDateOfBirth(patient.getDateOfBirth());
        response.setGender(patient.getGender());
        response.setPhone(patient.getPhone());
        response.setEmail(patient.getEmail());
        response.setAddress(patient.getAddress());
        response.setCreatedAt(patient.getCreatedAt());

        return response;
    }
}

package com.example.hospitalProject.service;

import com.example.hospitalProject.DTO.request.CreateAppointmentDTO;
import com.example.hospitalProject.DTO.response.*;
import com.example.hospitalProject.entity.Appointment;
import com.example.hospitalProject.entity.Doctor;
import com.example.hospitalProject.entity.Patient;
import com.example.hospitalProject.entity.Room;
import com.example.hospitalProject.repository.AppointmentRepo;
import com.example.hospitalProject.repository.DoctorRepo;
import com.example.hospitalProject.repository.PatientRepo;
import com.example.hospitalProject.repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepo appointmentRepo;
    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private DoctorRepo doctorRepo;
    @Autowired
    private RoomRepo roomRepo;

    // GET /api/appointments
    public List<AppointmentListResponseDTO> getAllAppointments() {
        List<Appointment> appointments = appointmentRepo.findAll();
        return appointments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GET /api/appointments/{id}
    public AppointmentDetailResponseDTO getAppointmentDetail(int id) {
        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        AppointmentDetailResponseDTO dto = new AppointmentDetailResponseDTO();

        dto.setAppointmentId(appointment.getId());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setReason(appointment.getReason());
        dto.setDiagnosis(appointment.getDiagnosis());
        dto.setStatus(appointment.getStatus());
        dto.setCreatedAt(appointment.getCreatedAt());

        // Doctor
        if (appointment.getDoctor() != null) {
            dto.setDoctorId(appointment.getDoctor().getId());
            dto.setDoctorName(appointment.getDoctor().getFullName());
        }

        // Room
        if (appointment.getRoom() != null) {
            dto.setRoomNumber(appointment.getRoom().getRoomNumber());
            dto.setRoomType(appointment.getRoom().getRoomType());

            if (appointment.getRoom().getDepartment() != null) {
                dto.setDepartmentName(appointment.getRoom().getDepartment().getDepartmentName());
            }
        }

        // Patient
        if (appointment.getPatient() != null) {
            dto.setPatientId(appointment.getPatient().getId());
            dto.setPatientName(appointment.getPatient().getFullName());
            dto.setDateOfBirth(appointment.getPatient().getDateOfBirth());
            dto.setGender(appointment.getPatient().getGender());
            dto.setPhone(appointment.getPatient().getPhone());
        }
        return dto;
    }

    private AppointmentListResponseDTO convertToDTO(Appointment appointment) {
        AppointmentListResponseDTO dto = new AppointmentListResponseDTO();
        dto.setAppointmentId(appointment.getId());
        dto.setPatientName(appointment.getPatient() != null ? appointment.getPatient().getFullName() : null);
        dto.setDoctorName(appointment.getDoctor() != null ? appointment.getDoctor().getFullName() : null);

        if (appointment.getRoom() != null) {
            dto.setRoomNumber(appointment.getRoom().getRoomNumber());
            dto.setDepartmentName(appointment.getRoom().getDepartment() != null
                            ? appointment.getRoom().getDepartment().getDepartmentName() : null);
        }

        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setReason(appointment.getReason());
        dto.setStatus(appointment.getStatus());
        return dto;
    }

    // DELETE /api/appointments/{id}
    public void deleteAppointment(int id) {
        Appointment appointment = appointmentRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy lịch khám"));
        appointmentRepo.delete(appointment);
    }

    //  POST  /api/appointments
    public AppointmentAddResponseDTO createAppointment(CreateAppointmentDTO dto) {

        Patient patient = patientRepo.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh nhân"));

        Doctor doctor = doctorRepo.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bác sĩ"));

        Room room = roomRepo.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng"));

        Appointment appointment = new Appointment();

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setRoom(room);

        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());

        if (dto.getAppointmentDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Ngày khám không hợp lệ");
        }

        if (appointmentRepo.existsByRoomIdAndAppointmentDateAndAppointmentTime(
                dto.getRoomId(),
                dto.getAppointmentDate(),
                dto.getAppointmentTime())) {
            throw new RuntimeException("Phòng đã được sử dụng.");
        }

        if (appointmentRepo.existsByDoctorIdAndAppointmentDateAndAppointmentTime(
                dto.getDoctorId(),
                dto.getAppointmentDate(),
                dto.getAppointmentTime())) {
            throw new RuntimeException("Bác sĩ đã có lịch.");
        }

        if (appointmentRepo.existsByPatientIdAndAppointmentDateAndAppointmentTime(
                dto.getPatientId(),
                dto.getAppointmentDate(),
                dto.getAppointmentTime())) {
            throw new RuntimeException("Bệnh nhân đã có lịch.");
        }

        appointment.setReason(dto.getReason());
        appointment.setStatus("Scheduled");
        appointment.setCreatedAt(LocalDateTime.now());

        appointmentRepo.save(appointment);
        return mapToDTO(appointment);
    }
    private AppointmentAddResponseDTO mapToDTO(Appointment appointment) {
        AppointmentAddResponseDTO dto = new AppointmentAddResponseDTO();

        dto.setAppointmentId(appointment.getId());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setDiagnosis(appointment.getDiagnosis());
        dto.setStatus(appointment.getStatus());
        dto.setReason(appointment.getReason());
        dto.setCreatedAt(appointment.getCreatedAt());

        dto.setDoctorId(appointment.getDoctor().getId());
        dto.setDoctorName(appointment.getDoctor().getFullName());

        dto.setPatientId(appointment.getPatient().getId());
        dto.setPatientName(appointment.getPatient().getFullName());

        dto.setRoomId(appointment.getRoom().getId());
        dto.setRoomNumber(appointment.getRoom().getRoomNumber());

        return dto;
    }

    // GET  /api/appointments/search
    public List<AppointmentSearchResponseDTO> searchAppointments(
            String keyword,
            LocalDate appointmentDate,
            String doctor,
            String status
    ) {
        List<Appointment> appointments = appointmentRepo.findAll();
        String keywordValue = keyword == null ? "" : keyword.trim().toLowerCase();
        String doctorValue = doctor == null ? "" : doctor.trim().toLowerCase();
        String statusValue = status == null ? "" : status.trim().toLowerCase();
        return appointments.stream()
                // Tìm theo tên bệnh nhân hoặc tên bác sĩ
                .filter(appointment -> {
                    if (keywordValue.isEmpty()) {
                        return true;
                    }
                    String patientName = appointment.getPatient() != null
                            && appointment.getPatient().getFullName() != null
                            ? appointment.getPatient().getFullName().toLowerCase()
                            : "";

                    String doctorName = appointment.getDoctor() != null
                            && appointment.getDoctor().getFullName() != null
                            ? appointment.getDoctor().getFullName().toLowerCase()
                            : "";
                    return patientName.contains(keywordValue)
                            || doctorName.contains(keywordValue);
                })

                // Lọc ngày khám
                .filter(appointment -> {
                    if (appointmentDate == null) {
                        return true;
                    }

                    return appointment.getAppointmentDate()
                            .equals(appointmentDate);
                })

                // Lọc bác sĩ
                .filter(appointment -> {
                    if (doctorValue.isEmpty()) {
                        return true;
                    }

                    if (appointment.getDoctor() == null) {
                        return false;
                    }

                    String doctorName = appointment.getDoctor().getFullName() == null
                                    ? ""
                                    : appointment.getDoctor()
                                    .getFullName()
                                    .toLowerCase();
                    return doctorName.equals(doctorValue);
                })

                // Lọc trạng thái
                .filter(appointment -> {
                    if (statusValue.isEmpty()) {
                        return true;
                    }
                    String appointmentStatus = appointment.getStatus() == null
                            ? ""
                            : appointment.getStatus().toLowerCase();
                    return appointmentStatus.equals(statusValue);
                })
                .map(this::mapToDetailDTO)
                .toList();
    }

    private AppointmentSearchResponseDTO mapToDetailDTO(Appointment appointment) {
        AppointmentSearchResponseDTO dto = new AppointmentSearchResponseDTO();

        dto.setAppointmentId(appointment.getId());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setDiagnosis(appointment.getDiagnosis());
        dto.setStatus(appointment.getStatus());
        dto.setReason(appointment.getReason());
        dto.setCreatedAt(appointment.getCreatedAt());

        dto.setDoctorId(appointment.getDoctor().getId());
        dto.setDoctorName(appointment.getDoctor().getFullName());

        dto.setPatientId(appointment.getPatient().getId());
        dto.setPatientName(appointment.getPatient().getFullName());

        dto.setRoomNumber(appointment.getRoom().getRoomNumber());
        dto.setRoomType(appointment.getRoom().getRoomType());

        return dto;
    }
}

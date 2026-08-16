package com.example.hospitalProject.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hospitalProject.DTO.request.CreateInvoiceDTO;
import com.example.hospitalProject.DTO.response.InvoiceMedicineDTO;
import com.example.hospitalProject.DTO.response.InvoiceResponseDTO;
import com.example.hospitalProject.DTO.response.UpdateInvoiceDTO;
import com.example.hospitalProject.entity.Appointment;
import com.example.hospitalProject.entity.Invoice;
import com.example.hospitalProject.entity.InvoiceMedicine;
import com.example.hospitalProject.entity.Medicine;
import com.example.hospitalProject.repository.AppointmentRepo;
import com.example.hospitalProject.repository.InvoiceMedicineRepo;
import com.example.hospitalProject.repository.InvoiceRepo;
import com.example.hospitalProject.repository.MedicineRepo;

// import jakarta.transaction.Transactional;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepo invoiceRepo;
    @Autowired
    private InvoiceMedicineRepo invoiceMedicineRepo;
    @Autowired
    private MedicineRepo medicineRepo;
    @Autowired
    private AppointmentRepo appointmentRepo;

    // =========================================================
    // GET ALL
    // GET /api/invoices
    // =========================================================
    @Transactional(readOnly = true)
    public List<InvoiceResponseDTO> getAllInvoices() {
        List<Invoice> invoices = invoiceRepo.findAll();
        List<InvoiceResponseDTO> result = new ArrayList<>();
        for (Invoice invoice : invoices) {
            result.add(convertToDTO(invoice));
        }
        return result;
    }

    // =========================================================
    // GET BY ID
    // GET /api/invoices/{id}
    // =========================================================
    @Transactional(readOnly = true)
    public InvoiceResponseDTO getInvoiceById(int id) {
        Invoice invoice = invoiceRepo.findById(id).orElseThrow(() ->
                        new RuntimeException(
                                "Không tìm thấy hóa đơn có id: " + id
                        )
                );
        return convertToDTO(invoice);
    }

    // =========================================================
    // CREATE
    // POST /api/invoices
    // =========================================================
    @Transactional
    public InvoiceResponseDTO createInvoice(CreateInvoiceDTO dto) {
        // Tìm lịch khám
        Appointment appointment = appointmentRepo
                .findById(dto.getAppointmentId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Không tìm thấy lịch khám có id: "
                                        + dto.getAppointmentId()));

        // Một lịch khám chỉ có một hóa đơn
        if (appointment.getInvoice() != null) {
            throw new RuntimeException(
                    "Lịch khám này đã có hóa đơn!"
            );
        }

        // Tạo hóa đơn
        Invoice invoice = new Invoice();

        invoice.setIssueDate(LocalDateTime.now());
        invoice.setExaminationFee(dto.getExaminationFee());
        invoice.setPaymentStatus(dto.getPaymentStatus());
        invoice.setAppointment(appointment);

        // Ban đầu
        invoice.setMedicineFee(0);
        invoice.setTotalAmount(dto.getExaminationFee());

        invoice = invoiceRepo.save(invoice);

        // =====================================================
        // XỬ LÝ THUỐC
        // =====================================================

        double medicineFee = 0;
        List<InvoiceMedicine> invoiceMedicines = new ArrayList<>();
        if (dto.getMedicines() != null) {
            for (InvoiceMedicineDTO medicineDTO : dto.getMedicines()) {
                Medicine medicine = medicineRepo
                        .findById(medicineDTO.getMedicineId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy thuốc có id: "
                                                + medicineDTO.getMedicineId()
                                ));
                int quantity = medicineDTO.getQuantity();
                if (quantity <= 0) {
                    throw new RuntimeException(
                            "Số lượng thuốc phải lớn hơn 0");
                }

                double unitPrice = medicine.getPrice();
                double subtotal = unitPrice * quantity;
                medicineFee += subtotal;
                InvoiceMedicine invoiceMedicine = new InvoiceMedicine();
                invoiceMedicine.setQuantity(quantity);
                invoiceMedicine.setUnitPrice(unitPrice);
                invoiceMedicine.setSubtotal(subtotal);
                invoiceMedicine.setInvoice(invoice);
                invoiceMedicine.setMedicine(medicine);
                invoiceMedicines.add(invoiceMedicine);
            }
        }
        invoiceMedicineRepo.saveAll(invoiceMedicines);

        // =====================================================
        // TÍNH TIỀN
        // =====================================================
        invoice.setMedicineFee(medicineFee);
        invoice.setTotalAmount(invoice.getExaminationFee() + medicineFee);
        invoice.setInvoiceMedicines(invoiceMedicines);
        invoice = invoiceRepo.save(invoice);
        return convertToDTO(invoice);
    }

    // =========================================================
    // UPDATE
    // PUT /api/invoices/{id}
    // =========================================================
    @Transactional
    public InvoiceResponseDTO updateInvoice(int id, UpdateInvoiceDTO dto) {
        Invoice invoice = invoiceRepo.findById(id).orElseThrow(() ->
                        new RuntimeException(
                                "Không tìm thấy hóa đơn có id: " + id));

        // Cập nhật phí khám
        invoice.setExaminationFee(dto.getExaminationFee());

        // Cập nhật trạng thái
        invoice.setPaymentStatus(dto.getPaymentStatus());

        // =====================================================
        // XÓA THUỐC CŨ
        // =====================================================

        if (invoice.getInvoiceMedicines() != null
                && !invoice.getInvoiceMedicines().isEmpty()) {
            invoiceMedicineRepo.deleteAll(invoice.getInvoiceMedicines());
            invoice.getInvoiceMedicines().clear();
        }

        // =====================================================
        // THÊM THUỐC MỚI
        // =====================================================

        double medicineFee = 0;
        List<InvoiceMedicine> newMedicines = new ArrayList<>();
        if (dto.getMedicines() != null) {
            for (InvoiceMedicineDTO medicineDTO : dto.getMedicines()) {
                Medicine medicine = medicineRepo
                        .findById(medicineDTO.getMedicineId())
                        .orElseThrow(() -> new RuntimeException(
                                "Không tìm thấy thuốc có id: "
                                                + medicineDTO.getMedicineId()));
                int quantity = medicineDTO.getQuantity();
                if (quantity <= 0) {
                    throw new RuntimeException("Số lượng thuốc phải lớn hơn 0");
                }
                double unitPrice = medicine.getPrice();
                double subtotal = unitPrice * quantity;
                medicineFee += subtotal;
                InvoiceMedicine invoiceMedicine = new InvoiceMedicine();
                invoiceMedicine.setQuantity(quantity);
                invoiceMedicine.setUnitPrice(unitPrice);
                invoiceMedicine.setSubtotal(subtotal);
                invoiceMedicine.setInvoice(invoice);
                invoiceMedicine.setMedicine(medicine);
                newMedicines.add(invoiceMedicine);
            }
        }
        invoiceMedicineRepo.saveAll(newMedicines);

        // =====================================================
        // TÍNH LẠI TIỀN
        // =====================================================

        invoice.setMedicineFee(medicineFee);
        invoice.setTotalAmount(invoice.getExaminationFee() + medicineFee);
        invoice.setInvoiceMedicines(newMedicines);
        invoice = invoiceRepo.save(invoice);
        return convertToDTO(invoice);
    }

    // =========================================================
    // DELETE
    // DELETE /api/invoices/{id}
    // =========================================================
    @Transactional
    public void deleteInvoice(int id) {
        Invoice invoice = invoiceRepo.findById(id).orElseThrow(() ->
                        new RuntimeException(
                                "Không tìm thấy hóa đơn có id: " + id));
        invoiceRepo.delete(invoice);
    }

    // =========================================================
    // SEARCH
    // GET /api/invoices/search?keyword=
    // =========================================================
    @Transactional(readOnly = true)
    public List<InvoiceResponseDTO> searchInvoices(String keyword) {
        keyword = keyword.trim();
        if (keyword.isEmpty()) {
            return getAllInvoices();
        }
        String value = keyword.trim().toLowerCase();
        List<Invoice> invoices;

        // =====================================================
        // HD0001
        // =====================================================
        if (value.startsWith("hd")) {
            String number = value.substring(2);
            try {
                int id = Integer.parseInt(number);
                invoices = invoiceRepo.findByInvoiceId(id);
            } catch (NumberFormatException e) {
                invoices = new ArrayList<>();
            }
        }

        // =====================================================
        // LK0001
        // =====================================================
        else if (value.startsWith("lk")) {
            String number = value.substring(2);
            try {
                int id = Integer.parseInt(number);
                invoices = invoiceRepo.findByAppointmentId(id);
            } catch (NumberFormatException e) {
                invoices = new ArrayList<>();
            }
        }

        // =====================================================
        // BN0001
        // =====================================================
        else if (value.startsWith("bn")) {
            String number = value.substring(2);
            try {
                int id = Integer.parseInt(number);
                invoices = invoiceRepo.findByPatientId(id);
            } catch (NumberFormatException e) {
                invoices = new ArrayList<>();
            }
        }

        // =====================================================
        // TÊN BỆNH NHÂN / ID
        // =====================================================
        else {
            invoices = invoiceRepo.searchInvoices(value);
        }

        // =====================================================
        // ENTITY -> DTO
        // =====================================================
        List<InvoiceResponseDTO> result = new ArrayList<>();
        for (Invoice invoice : invoices) {
            result.add(convertToDTO(invoice));
        }
        return result;
    }

    // =========================================================
    // CONVERT ENTITY -> DTO
    // =========================================================
    private InvoiceResponseDTO convertToDTO(Invoice invoice) {
        InvoiceResponseDTO dto = new InvoiceResponseDTO();
        // =====================================================
        // HÓA ĐƠN
        // =====================================================
        dto.setInvoiceId(invoice.getId());

        // HD0001
        dto.setInvoiceCode(String.format("HD%04d", invoice.getId()));

        // =====================================================
        // LỊCH KHÁM
        // =====================================================
        Appointment appointment = invoice.getAppointment();
        if (appointment != null) {
            // LK0001
            dto.setAppointmentCode(String.format("LK%04d", appointment.getId()));

            // =================================================
            // BỆNH NHÂN
            // =================================================
            if (appointment.getPatient() != null) {
                int patientId = appointment.getPatient().getId();
                dto.setPatientId(patientId);

                // BN0001
                dto.setPatientCode(String.format("BN%04d", patientId));
                dto.setPatientName(appointment.getPatient().getFullName());
            }
        }

        // =====================================================
        // THÔNG TIN TIỀN
        // =====================================================

        dto.setIssueDate(invoice.getIssueDate());
        dto.setExaminationFee(invoice.getExaminationFee());
        dto.setMedicineFee(invoice.getMedicineFee());
        dto.setTotalAmount(invoice.getTotalAmount());
        dto.setPaymentStatus(invoice.getPaymentStatus());
        return dto;
    }
}

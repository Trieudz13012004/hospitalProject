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

    // GET /api/invoices
    @Transactional(readOnly = true)
    public List<InvoiceResponseDTO> getAllInvoices() {
        List<Invoice> invoices = invoiceRepo.findAll();
        List<InvoiceResponseDTO> result = new ArrayList<>();
        for (Invoice invoice : invoices) {
            result.add(convertToDTO(invoice));
        }
        return result;
    }

    // GET /api/invoices/{id}
    @Transactional(readOnly = true)
    public InvoiceResponseDTO getInvoiceById(int id) {
        Invoice invoice = invoiceRepo.findById(id).orElseThrow(() ->
                new RuntimeException(
                        "Không tìm thấy hóa đơn có id: " + id));
        return convertToDTO(invoice);
    }

    // POST /api/invoices
    @Transactional
    public InvoiceResponseDTO createInvoice(CreateInvoiceDTO dto) {

        // Tìm lịch khám
        Appointment appointment = appointmentRepo.findById(dto.getAppointmentId()).orElseThrow(() ->
                new RuntimeException(
                        "Không tìm thấy lịch khám có id: "
                                + dto.getAppointmentId()));

        // Một lịch khám chỉ được có một hóa đơn
        if (appointment.getInvoice() != null) {
            throw new RuntimeException("Lịch khám này đã có hóa đơn!");
        }

        // Tạo hóa đơn
        Invoice invoice = new Invoice();
        invoice.setIssueDate(LocalDateTime.now());
        invoice.setExaminationFee(dto.getExaminationFee());
        invoice.setPaymentStatus(dto.getPaymentStatus());
        invoice.setAppointment(appointment);

        // Lưu trước để lấy invoice ID
        invoice = invoiceRepo.save(invoice);

        // XỬ LÝ THUỐC
        double medicineFee = 0;
        List<InvoiceMedicine> invoiceMedicines = new ArrayList<>();
        if (dto.getMedicines() != null) {
            for (InvoiceMedicineDTO medicineDTO : dto.getMedicines()) {
                Medicine medicine = medicineRepo.findById(medicineDTO.getMedicineId()).orElseThrow(() ->
                        new RuntimeException(
                                "Không tìm thấy thuốc có id: " + medicineDTO.getMedicineId()));

                int quantity = medicineDTO.getQuantity();
                if (quantity <= 0) {throw new RuntimeException("Số lượng thuốc phải lớn hơn 0");}
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


        // Lưu chi tiết thuốc
        invoiceMedicineRepo.saveAll(invoiceMedicines);

        // TÍNH TIỀN

        invoice.setMedicineFee(medicineFee);
        invoice.setTotalAmount(invoice.getExaminationFee() + medicineFee);
        invoice.setInvoiceMedicines(invoiceMedicines);

        // Lưu lại hóa đơn
        invoice = invoiceRepo.save(invoice);
        return convertToDTO(invoice);
    }

    // API: PUT /api/invoices/{id}
    @Transactional
    public InvoiceResponseDTO updateInvoice(int id, UpdateInvoiceDTO dto) {
        Invoice invoice = invoiceRepo.findById(id).orElseThrow(() ->
                new RuntimeException("Không tìm thấy hóa đơn có id: " + id));
        invoice.setExaminationFee(dto.getExaminationFee());
        invoice.setPaymentStatus(dto.getPaymentStatus());

        // Xóa thuốc cũ
        if (invoice.getInvoiceMedicines() != null) {
            invoiceMedicineRepo.deleteAll(invoice.getInvoiceMedicines());
            invoice.getInvoiceMedicines().clear();
        }

        // THÊM THUỐC MỚI

        double medicineFee = 0;
        List<InvoiceMedicine> newMedicines = new ArrayList<>();
        if (dto.getMedicines() != null) {
            for (InvoiceMedicineDTO medicineDTO : dto.getMedicines()) {
                Medicine medicine = medicineRepo.findById(medicineDTO.getMedicineId()).orElseThrow(() ->
                        new RuntimeException("Không tìm thấy thuốc có id: " + medicineDTO.getMedicineId()));
                int quantity = medicineDTO.getQuantity();
                if (quantity <= 0) {
                    throw new RuntimeException("Số lượng thuốc phải lớn hơn 0");
                }

                double unitPrice = medicine.getPrice();

                double subtotal = unitPrice * quantity;

                medicineFee += subtotal;

                InvoiceMedicine invoiceMedicine =
                        new InvoiceMedicine();

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
    // DELETE /api/invoices/{id}
    // =========================================================
    @Transactional
    public void deleteInvoice(int id) {
        Invoice invoice = invoiceRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn có id: " + id));
        invoiceRepo.delete(invoice);
    }

    // =========================================================
    // CONVERT ENTITY -> DTO
    // =========================================================
    private InvoiceResponseDTO convertToDTO(Invoice invoice) {
        InvoiceResponseDTO dto = new InvoiceResponseDTO();
        dto.setInvoiceId(invoice.getId());

        // Mã hóa đơn: HD001
        dto.setInvoiceCode(String.format("HD%03d", invoice.getId())
        );

        Appointment appointment = invoice.getAppointment();
        if (appointment != null) {
            // Mã lịch khám: LK001
            dto.setAppointmentCode(String.format("LK%03d", appointment.getId()));
            if (appointment.getPatient() != null) {
                // Mã bệnh nhân: BN001
                dto.setPatientCode(String.format("BN%03d", appointment.getPatient().getId()));
                dto.setPatientName(appointment.getPatient().getFullName());
            }
        }

        dto.setIssueDate(invoice.getIssueDate());
        dto.setExaminationFee(invoice.getExaminationFee());
        dto.setMedicineFee(invoice.getMedicineFee());
        dto.setTotalAmount(invoice.getTotalAmount());
        dto.setPaymentStatus(invoice.getPaymentStatus());
        return dto;
    }
}

package com.example.hospitalProject.DTO.request;

import java.util.List;

import com.example.hospitalProject.DTO.response.InvoiceMedicineDTO;

public class CreateInvoiceDTO {
    private int appointmentId;

    private double examinationFee;

    private String paymentStatus;

    private List<InvoiceMedicineDTO> medicines;

    public CreateInvoiceDTO() {
    }

    public CreateInvoiceDTO(int appointmentId, double examinationFee, String paymentStatus,
                            List<InvoiceMedicineDTO> medicines) {
        this.appointmentId = appointmentId;
        this.examinationFee = examinationFee;
        this.paymentStatus = paymentStatus;
        this.medicines = medicines;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public double getExaminationFee() {
        return examinationFee;
    }

    public void setExaminationFee(double examinationFee) {
        this.examinationFee = examinationFee;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public List<InvoiceMedicineDTO> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<InvoiceMedicineDTO> medicines) {
        this.medicines = medicines;
    }


}

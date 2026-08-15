package com.example.hospitalProject.DTO.response;

import java.util.List;

public class UpdateInvoiceDTO {
    private double examinationFee;
    private String paymentStatus;
    private List<InvoiceMedicineDTO> medicines;

    public UpdateInvoiceDTO() {
    }

    public UpdateInvoiceDTO(double examinationFee, String paymentStatus, List<InvoiceMedicineDTO> medicines) {
        this.examinationFee = examinationFee;
        this.paymentStatus = paymentStatus;
        this.medicines = medicines;
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

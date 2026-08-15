package com.example.hospitalProject.DTO.response;

import java.time.LocalDateTime;

public class InvoiceResponseDTO {
    private int invoiceId;

    private String invoiceCode;

    private String appointmentCode;

    private String patientName;

    private String patientCode;

    private LocalDateTime issueDate;

    private double examinationFee;

    private double medicineFee;

    private double totalAmount;

    private String paymentStatus;

    public InvoiceResponseDTO() {
    }

    public InvoiceResponseDTO(int invoiceId, String invoiceCode, String appointmentCode, String patientName, String patientCode, LocalDateTime issueDate, double examinationFee, double medicineFee, double totalAmount, String paymentStatus) {
        this.invoiceId = invoiceId;
        this.invoiceCode = invoiceCode;
        this.appointmentCode = appointmentCode;
        this.patientName = patientName;
        this.patientCode = patientCode;
        this.issueDate = issueDate;
        this.examinationFee = examinationFee;
        this.medicineFee = medicineFee;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getAppointmentCode() {
        return appointmentCode;
    }

    public void setAppointmentCode(String appointmentCode) {
        this.appointmentCode = appointmentCode;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public double getExaminationFee() {
        return examinationFee;
    }

    public void setExaminationFee(double examinationFee) {
        this.examinationFee = examinationFee;
    }

    public double getMedicineFee() {
        return medicineFee;
    }

    public void setMedicineFee(double medicineFee) {
        this.medicineFee = medicineFee;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}

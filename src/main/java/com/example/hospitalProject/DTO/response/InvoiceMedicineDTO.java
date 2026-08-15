package com.example.hospitalProject.DTO.response;

import jakarta.validation.constraints.NotNull;

public class InvoiceMedicineDTO {
    @NotNull(message = "Mã thuốc không được để trống")
    private int medicineId;

    @NotNull(message = "Số lượng không được để trống")
    private int quantity;

    public InvoiceMedicineDTO() {
    }

    public InvoiceMedicineDTO(@NotNull(message = "Mã thuốc không được để trống") int medicineId,
                              @NotNull(message = "Số lượng không được để trống") int quantity) {
        this.medicineId = medicineId;
        this.quantity = quantity;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}

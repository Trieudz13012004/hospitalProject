package com.example.hospitalProject.controller;

import com.example.hospitalProject.DTO.request.CreateInvoiceDTO;
import com.example.hospitalProject.DTO.response.InvoiceResponseDTO;
import com.example.hospitalProject.DTO.response.UpdateInvoiceDTO;
import com.example.hospitalProject.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(origins = "*")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    // =========================================================
    // GET /api/invoices
    // =========================================================
    @GetMapping
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    // =========================================================
    // SEARCH
    // GET /api/invoices/search?keyword=
    // =========================================================
    @GetMapping("/search")
    public ResponseEntity<List<InvoiceResponseDTO>> searchInvoices(@RequestParam String keyword) {
        return ResponseEntity.ok(invoiceService.searchInvoices(keyword));
    }

    // =========================================================
    // GET /api/invoices/{id}
    // =========================================================
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponseDTO> getInvoiceById(@PathVariable int id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    // =========================================================
    // POST /api/invoices
    // =========================================================
    @PostMapping
    public ResponseEntity<InvoiceResponseDTO> createInvoice(@RequestBody CreateInvoiceDTO dto) {
        InvoiceResponseDTO result = invoiceService.createInvoice(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // =========================================================
    // PUT /api/invoices/{id}
    // =========================================================
    @PutMapping("/{id}")
    public ResponseEntity<InvoiceResponseDTO> updateInvoice(
            @PathVariable int id,
            @RequestBody UpdateInvoiceDTO dto) {
        return ResponseEntity.ok(invoiceService.updateInvoice(id, dto));
    }

    // =========================================================
    // DELETE /api/invoices/{id}
    // =========================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(
            @PathVariable int id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }

}

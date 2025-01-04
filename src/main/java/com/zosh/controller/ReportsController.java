package com.zosh.controller;

import com.zosh.model.Reports;
import com.zosh.model.User;
import com.zosh.request.CreateReportRequest;
import com.zosh.service.ReportsService;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportsController {

    private final ReportsService reportsService;

    @Autowired
    public ReportsController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    // Tworzenie raportu
    @PostMapping
    public ResponseEntity<Reports> createReport(@RequestBody CreateReportRequest report,
                                                @RequestHeader("Authorization") String jwt
    ) throws Exception {
        Reports createdReport = reportsService.createReport(report, jwt);
        return ResponseEntity.ok(createdReport);
    }

    // Pobieranie raportów
    @GetMapping
    public ResponseEntity<List<Reports>> getAllReports() {
        List<Reports> reports = reportsService.getAllReports();
        return ResponseEntity.ok(reports);
    }

    // Pobieranie raportu po ID
    @GetMapping("/{id}")
    public ResponseEntity<Reports> getReportById(@PathVariable Long id) {
        Reports report = reportsService.getReportById(id);
        return report != null ? ResponseEntity.ok(report) : ResponseEntity.notFound().build();
    }

    // Dodanie produktu do raportu
    @PutMapping("/{reportId}/add-product/{productId}")
    public ResponseEntity<Reports> addProductToReport(@PathVariable Long reportId, @PathVariable Long productId) {
        Reports updatedReport = reportsService.addProductToReport(reportId, productId);
        return updatedReport != null ? ResponseEntity.ok(updatedReport) : ResponseEntity.notFound().build();
    }

    // Usuwanie produktu z raportu
    @PutMapping("/{reportId}/remove-product/{productId}")
    public ResponseEntity<Reports> removeProductFromReport(@PathVariable Long reportId, @PathVariable Long productId) {
        Reports updatedReport = reportsService.removeProductFromReport(reportId, productId);
        return updatedReport != null ? ResponseEntity.ok(updatedReport) : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/paginated")
    public ResponseEntity<List<User>> getUserReportsPaginated(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(value = "page", defaultValue = "0") int page) {
        try {
            List<User> userReports = reportsService.getCountOfReportsByUserAndProduct(jwt);
            return ResponseEntity.ok(userReports);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}

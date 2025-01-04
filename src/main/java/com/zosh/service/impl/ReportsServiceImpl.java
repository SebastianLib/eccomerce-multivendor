package com.zosh.service.impl;

import com.zosh.model.Reports;
import com.zosh.model.Product;
import com.zosh.model.User;
import com.zosh.repository.ReportsRepository;
import com.zosh.repository.ProductRepository;
import com.zosh.request.CreateReportRequest;
import com.zosh.service.AuthService;
import com.zosh.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;


import java.util.List;
import java.util.Optional;

@Service
public class ReportsServiceImpl implements ReportsService {

    private final ReportsRepository reportsRepository;
    private final ProductRepository productRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    public ReportsServiceImpl(ReportsRepository reportsRepository, ProductRepository productRepository) {
        this.reportsRepository = reportsRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Reports createReport(CreateReportRequest reportRequest, String jwt) throws Exception {
        User user = authService.findUserByJwtToken(jwt);
        List<Product> products = productRepository.findProductsByIds(reportRequest.getProductIds());

        Reports newReport = new Reports();
        newReport.setDescription(reportRequest.getDescription());
        newReport.setUser(user);
        newReport.setProducts(products);

        return reportsRepository.save(newReport);
    }


    @Override
    public List<Reports> getAllReports() {
        return reportsRepository.findAll();
    }

    @Override
    public Reports getReportById(Long id) {
        Optional<Reports> report = reportsRepository.findById(id);
        return report.orElse(null);
    }

    @Override
    public Reports addProductToReport(Long reportId, Long productId) {
        Optional<Reports> reportOptional = reportsRepository.findById(reportId);
        Optional<Product> productOptional = productRepository.findById(Math.toIntExact(productId));

        if (reportOptional.isPresent() && productOptional.isPresent()) {
            Reports report = reportOptional.get();
            Product product = productOptional.get();
            report.getProducts().add(product);
            return reportsRepository.save(report);
        }
        return null;
    }

    @Override
    public Reports removeProductFromReport(Long reportId, Long productId) {
        Optional<Reports> reportOptional = reportsRepository.findById(reportId);
        Optional<Product> productOptional = productRepository.findById(Math.toIntExact(productId));

        if (reportOptional.isPresent() && productOptional.isPresent()) {
            Reports report = reportOptional.get();
            Product product = productOptional.get();
            report.getProducts().remove(product);
            return reportsRepository.save(report);
        }
        return null;
    }

    @Override
    public List<Reports> getReportsByUser(String jwt) throws Exception {
        return List.of();
    }

    @Override
    public Page<Reports> getReportsByUserPaginated(String jwt, int page) throws Exception {
        return null;
    }

    @Override
    public List<User> getCountOfReportsByUserAndProduct(String jwt) throws Exception {
        User user = authService.findUserByJwtToken(jwt);
        return reportsRepository.findUsersByProductCategory(Long.valueOf(1));
    }

}

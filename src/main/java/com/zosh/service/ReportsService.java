package com.zosh.service;

import com.zosh.model.Reports;
import com.zosh.model.Product;
import com.zosh.model.User;
import com.zosh.request.CreateReportRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReportsService {

    Reports createReport(CreateReportRequest report, String jwt) throws Exception;

    List<Reports> getAllReports();

    Reports getReportById(Long id);

    Reports addProductToReport(Long reportId, Long productId);

    Reports removeProductFromReport(Long reportId, Long productId);

    List<Reports> getReportsByUser(String jwt) throws Exception;

    Page<Reports> getReportsByUserPaginated(String jwt, int page) throws Exception;

    List<User> getCountOfReportsByUserAndProduct(String jwt) throws Exception;
}

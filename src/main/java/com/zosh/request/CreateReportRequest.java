package com.zosh.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateReportRequest {
    private String description;
    private List<Long> productIds;
}

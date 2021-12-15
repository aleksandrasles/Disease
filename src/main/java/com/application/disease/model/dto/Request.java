package com.application.disease.model.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("requests")
public class Request {
    private String id;
    private String diseaseName;
    private String regionName;
    private int numberOfCases = 0;
    private int numberOfRecovered = 0;
    private int numberOfDeaths = 0;
    private String userId;
    private LocalDateTime createdAt;


    public Request(String diseaseName, String regionName, int numberOfCases, int numberOfRecovered, int numberOfDeaths, String userId) {
        this.diseaseName = diseaseName;
        this.regionName = regionName;
        this.numberOfCases = numberOfCases;
        this.numberOfRecovered = numberOfRecovered;
        this.numberOfDeaths = numberOfDeaths;
        this.userId = userId;
    }
}

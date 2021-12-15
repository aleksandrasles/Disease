package com.application.disease.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;

@Getter
@Setter
@Document("disease_metrics")
public class DiseaseMetrics {

    @Id
    private String id;
    private String diseaseName;
    private String regionName;
    private int numberOfCases = 0;
    private int numberOfRecovered = 0;
    private int numberOfDeaths = 0;
    private LocalDateTime updatedAt = LocalDateTime.now();
    private LocalDateTime createdAt = LocalDateTime.now();

    public DiseaseMetrics(){}

    public DiseaseMetrics(String diseaseName, String regionName, int numberOfCases, int numberOfRecovered, int numberOfDeaths) {
        this.diseaseName = diseaseName;
        this.regionName = regionName;
        this.numberOfCases = numberOfCases;
        this.numberOfRecovered = numberOfRecovered;
        this.numberOfDeaths = numberOfDeaths;
    }
}

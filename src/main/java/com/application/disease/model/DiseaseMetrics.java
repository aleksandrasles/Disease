package com.application.disease.model;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@Document("disease_metrics")
public class DiseaseMetrics {

    @Id
    private String id;

    private String diseaseName;

    private String regionName;

    private int numberOfIll = 0;

    private int numberOfRecovered = 0;

    private LocalDateTime lastUpdatedAt = LocalDateTime.now();

    private final LocalDateTime createdAt = LocalDateTime.now();

    public DiseaseMetrics(String diseaseName, String regionName, int numberOfIll, int numberOfRecovered) {
        this.diseaseName = diseaseName;
        this.regionName = regionName;
        this.numberOfIll = numberOfIll;
        this.numberOfRecovered = numberOfRecovered;
    }

    public void addNumOfIll(int numberOfIll) {
        this.numberOfIll += numberOfIll;
    }

    public void addNumOfRecovered(int numberOfRecovered) {
        this.numberOfRecovered += numberOfRecovered;
    }

    public String getId(){
        return id;
    }

    public int getNumberOfRecovered() {
        return numberOfRecovered;
    }

    public void setNumberOfRecovered(int numberOfRecovered) {
        this.numberOfRecovered = numberOfRecovered;
    }

    public String getName() {
        return diseaseName;
    }

    public void setName(String name) {
        this.diseaseName = name;
    }

    public String getRegion() {
        return regionName;
    }

    public void setRegion(String regionName) {
        this.regionName = regionName;
    }

    public int getNumberOfIll() {
        return numberOfIll;
    }

    public void setNumberOfIll(int numberOfIll) {
        this.numberOfIll = numberOfIll;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public LocalDateTime getCreatedAt() {return createdAt;}

}

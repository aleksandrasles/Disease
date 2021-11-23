package com.application.disease.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDto {
    String id;
    String diseaseName;
    String regionName;
    int numberOfIll = 0;
    int numberOfRecovered = 0;
    String userId;

    public RequestDto(String diseaseName, String regionName, int numberOfIll, int numberOfRecovered, String userId) {
        this.diseaseName = diseaseName;
        this.regionName = regionName;
        this.numberOfIll = numberOfIll;
        this.numberOfRecovered = numberOfRecovered;
        this.userId = userId;
    }
}

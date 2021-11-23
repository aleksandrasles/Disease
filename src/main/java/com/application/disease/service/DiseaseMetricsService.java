package com.application.disease.service;

import com.application.disease.dao.DiseaseMetricsRepository;
import com.application.disease.model.DiseaseMetrics;
import com.application.disease.model.dto.RequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiseaseMetricsService {

    @Autowired
    private DiseaseMetricsRepository diseaseMetricsRepository;

    public DiseaseMetrics updateDiseaseMetricsByRequestDto(DiseaseMetrics diseaseMetrics, RequestDto requestDto) {
        if(diseaseMetricsRepository.findByDiseaseAndRegion(requestDto.getDiseaseName(), requestDto.getRegionName()) == null) {
            DiseaseMetrics newDiseaseMetrics= new DiseaseMetrics();
            newDiseaseMetrics.setName(requestDto.getDiseaseName());
            newDiseaseMetrics.setRegion(requestDto.getRegionName());
            newDiseaseMetrics.setNumberOfIll(newDiseaseMetrics.getNumberOfIll());
            newDiseaseMetrics.setNumberOfRecovered(newDiseaseMetrics.getNumberOfRecovered());
            newDiseaseMetrics.setLastUpdatedAt(LocalDateTime.now());
            diseaseMetricsRepository.crete(newDiseaseMetrics);
            diseaseMetrics = newDiseaseMetrics;
        } else {
            diseaseMetrics = diseaseMetricsRepository.findByDiseaseAndRegion(requestDto.getDiseaseName(), requestDto.getRegionName());
            diseaseMetrics.addNumOfIll(requestDto.getNumberOfIll());
            diseaseMetrics.addNumOfRecovered(requestDto.getNumberOfRecovered());
            diseaseMetrics.setLastUpdatedAt(LocalDateTime.now());
            diseaseMetricsRepository.update(diseaseMetrics);
        }

        return diseaseMetrics;
    }

    public List<DiseaseMetrics> findDiseaseMetricsWithParams(String diseaseName, String regionName) {
        List<DiseaseMetrics> diseaseMetricsList = null;
        if (diseaseName != null && regionName == null) {
            return diseaseMetricsRepository.findByDiseaseName(diseaseName);
        }
        if (diseaseName == null && regionName != null) {
            return diseaseMetricsRepository.findByRegionName(regionName);
        }
        if(diseaseName != null && regionName != null) {
            return diseaseMetricsRepository.findByDiseaseAndRegionList(diseaseName, regionName);
        }

        return diseaseMetricsRepository.findAll();
    }
}

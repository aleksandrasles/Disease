package com.application.disease.service;

import com.application.disease.dao.DiseaseMetricsRepository;
import com.application.disease.model.DiseaseMetrics;
import com.application.disease.model.dto.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiseaseMetricsService {

    @Autowired
    private DiseaseMetricsRepository diseaseMetricsRepository;

    public DiseaseMetrics updateDiseaseMetricsByRequestDto(DiseaseMetrics diseaseMetrics, Request request) {
        if(diseaseMetricsRepository.findByDiseaseAndRegion(request.getDiseaseName(), request.getRegionName()) == null) {
            DiseaseMetrics newDiseaseMetrics= new DiseaseMetrics();
            newDiseaseMetrics.setDiseaseName(request.getDiseaseName());
            newDiseaseMetrics.setRegionName(request.getRegionName());
            newDiseaseMetrics.setNumberOfRecovered(newDiseaseMetrics.getNumberOfRecovered());
            newDiseaseMetrics.setNumberOfRecovered(newDiseaseMetrics.getNumberOfRecovered());
            newDiseaseMetrics.setNumberOfDeaths(newDiseaseMetrics.getNumberOfDeaths());
            newDiseaseMetrics.setUpdatedAt(LocalDateTime.now());
            diseaseMetricsRepository.crete(newDiseaseMetrics);
            diseaseMetrics = newDiseaseMetrics;
        } else {
            diseaseMetrics = diseaseMetricsRepository.findByDiseaseAndRegion(request.getDiseaseName(), request.getRegionName());
            diseaseMetrics.setNumberOfCases(diseaseMetrics.getNumberOfCases() + request.getNumberOfCases());
            diseaseMetrics.setNumberOfRecovered(diseaseMetrics.getNumberOfRecovered() + request.getNumberOfRecovered());
            diseaseMetrics.setNumberOfDeaths(diseaseMetrics.getNumberOfDeaths() + request.getNumberOfDeaths());
            diseaseMetrics.setUpdatedAt(LocalDateTime.now());
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

    public List<DiseaseMetrics> findDiseaseMetricsWithParams(String diseaseName, String regionName, String startedAt, String endedAt) {
        LocalDateTime start = LocalDateTime.ofInstant(Instant.parse(startedAt + "T22:00:00.000Z"), ZoneId.systemDefault());
        LocalDateTime end = LocalDateTime.ofInstant(Instant.parse(endedAt + "T22:00:00.000Z"), ZoneId.systemDefault());

        List<DiseaseMetrics> diseaseMetricsList = findDiseaseMetricsWithParams(diseaseName, regionName);
        LocalDateTime dateTime = start.minusDays(1);
        if(start.isEqual(end)) {
           return diseaseMetricsList
                    .stream()
                    .filter(diseaseMetrics -> diseaseMetrics.getUpdatedAt().isBefore(end) &&
                            diseaseMetrics.getUpdatedAt().isAfter(dateTime))
                    .collect(Collectors.toList());
        }

        return diseaseMetricsList
                .stream()
                .filter(diseaseMetrics -> diseaseMetrics.getUpdatedAt().isAfter(start))
                .filter(diseaseMetrics -> diseaseMetrics.getUpdatedAt().isBefore(end))
                .collect(Collectors.toList());
    }
}

package com.application.disease.service;

import com.application.disease.dao.DiseaseMetricsRepository;
import com.application.disease.model.DiseaseMetrics;
import com.application.disease.model.dto.RequestDto;
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

    public DiseaseMetrics updateDiseaseMetricsByRequestDto(DiseaseMetrics diseaseMetrics, RequestDto requestDto) {
        if(diseaseMetricsRepository.findByDiseaseAndRegion(requestDto.getDiseaseName(), requestDto.getRegionName()) == null) {
            DiseaseMetrics newDiseaseMetrics= new DiseaseMetrics();
            newDiseaseMetrics.setName(requestDto.getDiseaseName());
            newDiseaseMetrics.setRegion(requestDto.getRegionName());
            newDiseaseMetrics.setNumberOfIll(newDiseaseMetrics.getNumberOfIll());
            newDiseaseMetrics.setNumberOfRecovered(newDiseaseMetrics.getNumberOfRecovered());
            newDiseaseMetrics.setUpdatedAt(LocalDateTime.now());
            diseaseMetricsRepository.crete(newDiseaseMetrics);
            diseaseMetrics = newDiseaseMetrics;
        } else {
            diseaseMetrics = diseaseMetricsRepository.findByDiseaseAndRegion(requestDto.getDiseaseName(), requestDto.getRegionName());
            diseaseMetrics.addNumOfIll(requestDto.getNumberOfIll());
            diseaseMetrics.addNumOfRecovered(requestDto.getNumberOfRecovered());
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

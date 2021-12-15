package com.application.disease.service.facade;

import com.application.disease.dao.DiseaseMetricsRepository;
import com.application.disease.model.DiseaseMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FinderOfDiseaseMetrics {

    @Autowired
    private DiseaseMetricsRepository diseaseMetricsRepository;

    public List<DiseaseMetrics> findDiseaseMetricsWithParams(String diseaseName, String regionName) {
            return diseaseMetricsRepository.findByDiseaseAndRegionList(diseaseName, regionName);
    }

    public List<DiseaseMetrics> findDiseaseMetricsWithParamsAndDate(String diseaseName, String regionName, String startedAt, String endedAt) {
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

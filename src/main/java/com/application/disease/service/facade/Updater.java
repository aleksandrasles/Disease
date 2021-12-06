package com.application.disease.service.facade;

import com.application.disease.dao.DiseaseMetricsRepository;
import com.application.disease.model.DiseaseMetrics;
import com.application.disease.model.dto.RequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Updater {

    @Autowired
    private DiseaseMetricsRepository diseaseMetricsRepository;

    public DiseaseMetrics updateDiseaseMetricsByRequestDto(DiseaseMetrics diseaseMetrics, RequestDto requestDto) {
            DiseaseMetrics newDiseaseMetrics= new DiseaseMetrics();
            newDiseaseMetrics.setName(requestDto.getDiseaseName());
            newDiseaseMetrics.setRegion(requestDto.getRegionName());
            newDiseaseMetrics.setNumberOfIll(newDiseaseMetrics.getNumberOfIll());
            newDiseaseMetrics.setNumberOfRecovered(newDiseaseMetrics.getNumberOfRecovered());
            newDiseaseMetrics.setUpdatedAt(LocalDateTime.now());
            diseaseMetricsRepository.crete(newDiseaseMetrics);
            diseaseMetrics = newDiseaseMetrics;

        return diseaseMetrics;
    }
}

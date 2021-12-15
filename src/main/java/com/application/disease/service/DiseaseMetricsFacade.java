package com.application.disease.service;

import com.application.disease.model.DiseaseMetrics;
import com.application.disease.model.dto.RequestDto;
import com.application.disease.service.facade.CheckerForDiseaseNameAndRegionName;
import com.application.disease.service.facade.FinderOfDiseaseMetrics;
import com.application.disease.service.facade.UpdaterOfDiseaseMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
public class DiseaseMetricsFacade {

    @Autowired
    private final UpdaterOfDiseaseMetrics updater;

    @Autowired
    private final FinderOfDiseaseMetrics finder;

    @Autowired
    private final CheckerForDiseaseNameAndRegionName checker;

    public DiseaseMetricsFacade(UpdaterOfDiseaseMetrics updater, FinderOfDiseaseMetrics finder, CheckerForDiseaseNameAndRegionName checker) {
        this.updater = updater;
        this.finder = finder;
        this.checker = checker;
    }

    public DiseaseMetrics updateDiseaseMetricsByRequestDto(DiseaseMetrics diseaseMetrics, RequestDto requestDto) {
        if(checker.checkForDiseaseAndRegion(requestDto.getDiseaseName(), requestDto.getRegionName())) {
            return updater.updateDiseaseMetricsByRequestDto(diseaseMetrics, requestDto);
        } else {
            return new DiseaseMetrics();
        }
    }

    public List<DiseaseMetrics> findDiseaseMetricsWithParamsAndDate(String diseaseName, String regionName, String startedAt, String endedAt) {
        if(checker.checkForDiseaseAndRegion(diseaseName, regionName)) {
            return finder.findDiseaseMetricsWithParamsAndDate(diseaseName, regionName, startedAt, endedAt);
        } else {
            return new ArrayList<DiseaseMetrics>();
        }
    }
}

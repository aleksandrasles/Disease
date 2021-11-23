package com.application.disease.dao;

import com.application.disease.model.DiseaseMetrics;

import java.util.List;

public interface DiseaseMetricsRepository {

    DiseaseMetrics crete(DiseaseMetrics diseaseMetrics);

    DiseaseMetrics findById(String id);

    List<DiseaseMetrics> findByDiseaseName(String name);

    List<DiseaseMetrics> findByRegionName(String region);

    DiseaseMetrics findByDiseaseAndRegion(String diseaseName, String regionName);

    List<DiseaseMetrics> findByDiseaseAndRegionList(String diseaseName, String regionName);

    List<DiseaseMetrics> findAll();

    DiseaseMetrics update(DiseaseMetrics diseaseMetrics);

    void delete(DiseaseMetrics diseaseMetrics);
}

package com.application.disease.controller;

import com.application.disease.dao.DiseaseRepository;
import com.application.disease.dao.RegionRepository;
import com.application.disease.model.DiseaseMetrics;
import com.application.disease.service.DiseaseMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RequestMapping("/api/v1/ministry_of_health")
@RestController
public class MinistryOfHealthController {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Autowired
    private DiseaseMetricsService diseaseMetricsService;

    @RequestMapping("/homepage")
    public ModelAndView homePage() {
        return new ModelAndView("dashboards/ministry_of_health");
    }

    @GetMapping("/find")
    public ResponseEntity<List<DiseaseMetrics>> findDiseaseMetricsWithParams(
            @RequestParam(required = false) String diseaseName, @RequestParam(required = false) String regionName) {
        return new ResponseEntity<>(diseaseMetricsService.findDiseaseMetricsWithParams(diseaseName, regionName), HttpStatus.OK);
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<DiseaseMetrics>> getStatisticsWithParams(
            @RequestParam(required = false) String diseaseName, @RequestParam(required = false) String regionName,
            @RequestParam(required = false) String startPeriod, @RequestParam(required = false) String endPeriod)
    {
        return new ResponseEntity<>(diseaseMetricsService.findDiseaseMetricsWithParams(diseaseName, regionName, startPeriod, endPeriod), HttpStatus.OK);
    }
}

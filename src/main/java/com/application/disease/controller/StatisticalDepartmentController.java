package com.application.disease.controller;

import com.application.disease.dao.DiseaseRepository;
import com.application.disease.dao.RegionRepository;
import com.application.disease.model.DiseaseMetrics;
import com.application.disease.model.dto.RequestDto;
import com.application.disease.service.DiseaseMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/statistical_department")
@RestController
public class StatisticalDepartmentController {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Autowired
    private DiseaseMetricsService diseaseMetricsService;

    @GetMapping("/find")
    public ResponseEntity<List<DiseaseMetrics>> findDiseaseMetricsWithParams(
            @RequestParam(required = false) String diseaseName, @RequestParam(required = false) String regionName) {
        return new ResponseEntity<>(diseaseMetricsService.findDiseaseMetricsWithParams(diseaseName, regionName), HttpStatus.OK);
    }

    @PostMapping("/add_new")
    public ResponseEntity<DiseaseMetrics> addNewDiseaseMetricsOrUpdate(@RequestBody RequestDto requestDto){
        if(requestDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (diseaseRepository.findByName(requestDto.getDiseaseName()) == null ||
                regionRepository.findByName(requestDto.getRegionName()) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DiseaseMetrics diseaseMetrics = null;
        diseaseMetrics = diseaseMetricsService.updateDiseaseMetricsByRequestDto(diseaseMetrics, requestDto);
        return new ResponseEntity<>(diseaseMetrics, HttpStatus.OK);
    }
}
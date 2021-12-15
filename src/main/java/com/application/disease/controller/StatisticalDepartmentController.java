package com.application.disease.controller;

import com.application.disease.dao.DiseaseRepository;
import com.application.disease.dao.RegionRepository;
import com.application.disease.dao.RequestRepository;
import com.application.disease.model.DiseaseMetrics;
import com.application.disease.model.User;
import com.application.disease.model.dto.Request;
import com.application.disease.service.DiseaseMetricsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/api/v1/statistical_department")
@RestController
public class StatisticalDepartmentController {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Autowired
    private DiseaseMetricsService diseaseMetricsService;

    public User getAuthenticatedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User)auth.getPrincipal();
    }

    @RequestMapping("/homepage")
    public ModelAndView homePage() {
        return new ModelAndView("dashboards/statistical_department");
    }

    @GetMapping("/find")
    public ResponseEntity<List<DiseaseMetrics>> findDiseaseMetricsWithParams(
            @RequestParam(required = false) String diseaseName, @RequestParam(required = false) String regionName) {
        return new ResponseEntity<>(diseaseMetricsService.findDiseaseMetricsWithParams(diseaseName, regionName), HttpStatus.OK);
    }

    @PostMapping("/add_new")
    public ResponseEntity<DiseaseMetrics> addNewDiseaseMetricsOrUpdate(@RequestBody Request request){
        if(request == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (diseaseRepository.findByName(request.getDiseaseName()) == null ||
                regionRepository.findByName(request.getRegionName()) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        request.setUserId(getAuthenticatedUser().getId());
        DiseaseMetrics diseaseMetrics = null;
        diseaseMetrics = diseaseMetricsService.updateDiseaseMetricsByRequestDto(diseaseMetrics, request);
        requestRepository.create(request);
        return new ResponseEntity<>(diseaseMetrics, HttpStatus.OK);
    }
}

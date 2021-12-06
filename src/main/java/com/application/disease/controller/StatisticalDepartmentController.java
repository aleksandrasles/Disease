package com.application.disease.controller;

import com.application.disease.dao.DiseaseRepository;
import com.application.disease.dao.RegionRepository;
import com.application.disease.model.DiseaseMetrics;
import com.application.disease.model.proxy.User;
import com.application.disease.model.dto.RequestDto;
import com.application.disease.service.DiseaseMetricsFacade;
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
    private RegionRepository regionRepository;

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Autowired
    private DiseaseMetricsFacade diseaseMetricsFacade;

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
        return new ResponseEntity<>(diseaseMetricsFacade.findDiseaseMetricsWithParamsAndDate(diseaseName, regionName, "", ""), HttpStatus.OK);
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

        requestDto.setId("11111");
        DiseaseMetrics diseaseMetrics = null;
        diseaseMetrics = diseaseMetricsFacade.updateDiseaseMetricsByRequestDto(diseaseMetrics, requestDto);
        return new ResponseEntity<>(diseaseMetrics, HttpStatus.OK);
    }
}

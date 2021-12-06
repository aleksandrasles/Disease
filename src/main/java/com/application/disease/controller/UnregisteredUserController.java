package com.application.disease.controller;

import com.application.disease.model.DiseaseMetrics;
import com.application.disease.service.DiseaseMetricsFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RestController
public class UnregisteredUserController {

    @Autowired
    private DiseaseMetricsFacade diseaseMetricsFacade;

    @RequestMapping("/")
    public @ResponseBody String greeting() {
        return "homepage";
    }

    @RequestMapping("/homepage")
    public ModelAndView homePage() {
        return new ModelAndView("dashboards/admin");
    }

    @GetMapping("/find")
    public ResponseEntity<List<DiseaseMetrics>> findDiseaseMetricsWithParams(
            @RequestParam(required = false) String diseaseName, @RequestParam(required = false) String regionName) {
        return new ResponseEntity<>(diseaseMetricsFacade.findDiseaseMetricsWithParamsAndDate(diseaseName, regionName, "", ""), HttpStatus.OK);
    }
}

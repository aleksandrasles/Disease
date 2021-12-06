package com.application.disease.service.facade;

import com.application.disease.dao.DiseaseRepository;
import com.application.disease.dao.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckerForDiseaseNameAndRegionName {

    @Autowired
    private RegionRepository regionRepository;

    private DiseaseRepository diseaseRepository;

    public boolean checkForDiseaseAndRegion(String diseaseName, String regionName) {
        if(regionRepository.findByName(regionName) != null && diseaseRepository.findByName(diseaseName) != null) {
            return true;
        }
        return false;
    }
}

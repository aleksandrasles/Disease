package com.application.disease.dao.impl;

import com.application.disease.model.DiseaseMetrics;
import com.application.disease.dao.DiseaseMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DiseaseMetricsRepositoryImpl implements DiseaseMetricsRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public DiseaseMetrics crete(DiseaseMetrics diseaseMetrics) {
        return mongoTemplate.insert(diseaseMetrics);
    }

    @Override
    public DiseaseMetrics findById(String id) {
        return mongoTemplate.findById(id, DiseaseMetrics.class);
    }

    @Override
    public List<DiseaseMetrics> findByDiseaseName(String diseaseName) {
        Query query = Query.query(Criteria.where("diseaseName").is(diseaseName));
        return mongoTemplate.find(query, DiseaseMetrics.class);
    }

    @Override
    public List<DiseaseMetrics> findByRegionName(String region) {
        Query query = Query.query(Criteria.where("region").is(region));
        return mongoTemplate.find(query, DiseaseMetrics.class);
    }

    @Override
    public DiseaseMetrics findByDiseaseAndRegion(String diseaseName, String regionName) {
        Query query = Query.query(Criteria.where("regionName").is(regionName))
                .addCriteria(Criteria.where("diseaseName").is(diseaseName));
        if(mongoTemplate.find(query, DiseaseMetrics.class).isEmpty()){
            return null;
        }
        return mongoTemplate.find(query, DiseaseMetrics.class).get(0);
    }

    @Override
    public List<DiseaseMetrics> findByDiseaseAndRegionList(String diseaseName, String regionName) {
        Query query = Query.query(Criteria.where("regionName").is(regionName))
                .addCriteria(Criteria.where("diseaseName").is(diseaseName));
        return mongoTemplate.find(query, DiseaseMetrics.class);
    }

    @Override
    public List<DiseaseMetrics> findAll() {
        return mongoTemplate.findAll(DiseaseMetrics.class);
    }

    @Override
    public DiseaseMetrics update(DiseaseMetrics diseaseMetrics) {
        return mongoTemplate.save(diseaseMetrics);
    }

    @Override
    public void delete(DiseaseMetrics diseaseMetrics) {
        Query query = Query.query(Criteria.where("id").is(diseaseMetrics.getId()));
        mongoTemplate.findAndRemove(query, DiseaseMetrics.class);
    }
}

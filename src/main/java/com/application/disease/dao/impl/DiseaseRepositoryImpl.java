package com.application.disease.dao.impl;

import com.application.disease.model.Disease;
import com.application.disease.dao.DiseaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DiseaseRepositoryImpl implements DiseaseRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Disease create(Disease disease) {
        return mongoTemplate.insert(disease);
    }

    @Override
    public Disease findByName(String name) {
        Query query = Query.query(Criteria.where("name").is(name));
        return mongoTemplate.find(query, Disease.class).get(0);
    }

    @Override
    public Disease findById(String id) {
        return mongoTemplate.findById(id, Disease.class);
    }

    @Override
    public List<Disease> findAll() {
        return mongoTemplate.findAll(Disease.class);
    }

    @Override
    public Disease update(Disease disease) {
        return mongoTemplate.save(disease);
    }

    @Override
    public void removeById(String id) {
        mongoTemplate.remove(findById(id));
    }
}

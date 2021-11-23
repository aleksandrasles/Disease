package com.application.disease.dao.impl;


import com.application.disease.model.Region;
import com.application.disease.dao.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class RegionRepositoryImpl implements RegionRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Region create(Region region) {
        return mongoTemplate.insert(region);
    }

    @Override
    public Region findById(String id) {
        return mongoTemplate.findById(id, Region.class);
    }

    @Override
    public Region findByName(String name) {
        Query query = Query.query(Criteria.where("name").is(name));
        return mongoTemplate.find(query, Region.class).get(0);
    }

    @Override
    public Region update(Region region) {
        return mongoTemplate.save(region);
    }

    @Override
    public void removeById(String id) {
        mongoTemplate.remove(findById(id));
    }
}

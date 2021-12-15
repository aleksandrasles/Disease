package com.application.disease.dao.impl;

import com.application.disease.dao.RequestRepository;
import com.application.disease.model.dto.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class RequestRepositoryImpl implements RequestRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Request create(Request request) {
        request.setCreatedAt(LocalDateTime.now());
        return mongoTemplate.insert(request);
    }

    @Override
    public List<Request> findAll() {
        return mongoTemplate.findAll(Request.class);
    }

    @Override
    public Request findById(String id) {
        return mongoTemplate.findById(id, Request.class);
    }

    @Override
    public Request findByName(String name) {
        Query query = Query.query(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query, Request.class);
    }

    @Override
    public List<Request> findByQuery(Query query) {
        return mongoTemplate.find(query, Request.class);
    }

    @Override
    public Request update(Request request) {
        return mongoTemplate.save(request);
    }

    @Override
    public void removeById(String id) {
        mongoTemplate.remove(findById(id));
    }
}

package com.application.disease.dao;

import com.application.disease.model.dto.Request;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface RequestRepository {

    Request create(Request request);

    List<Request> findAll();

    Request findById(String id);

    Request findByName(String name);

    List<Request> findByQuery(Query query);

    Request update(Request request);

    void removeById(String id);
}

package com.application.disease.dao;

import com.application.disease.model.Region;

import java.util.List;

public interface RegionRepository {

    Region create(Region region);

    List<Region> findAll();

    Region findById(String id);

    Region findByName(String name);

    Region update(Region region);

    void removeById(String id);
}

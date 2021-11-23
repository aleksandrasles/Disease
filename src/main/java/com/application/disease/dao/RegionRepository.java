package com.application.disease.dao;

import com.application.disease.model.Region;

public interface RegionRepository {

    Region create(Region region);

    Region findById(String id);

    Region findByName(String name);

    Region update(Region region);

    void removeById(String id);
}

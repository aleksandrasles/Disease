package com.application.disease.dao;

import com.application.disease.model.Disease;
import jdk.dynalink.linker.LinkerServices;

import java.util.List;

public interface DiseaseRepository {

    Disease create(Disease disease);

    Disease findByName(String name);

    Disease findById(String id);

    List<Disease> findAll();

    Disease update(Disease disease);

    void removeById(String id);
}

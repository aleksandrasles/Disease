package com.application.disease.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("diseases")
public class Disease {

    @Id
    private String id;
    private String name;
    private String description;

    public Disease(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

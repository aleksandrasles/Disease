package com.application.disease.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
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
}

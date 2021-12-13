package com.application.disease.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document("regions")
public class Region {

    @Id
    private String id;
    private String name;
}

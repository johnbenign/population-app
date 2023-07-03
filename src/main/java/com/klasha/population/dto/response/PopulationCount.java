package com.klasha.population.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class PopulationCount{
    private String year;
    private String value;
    private String sex;
    private String reliability;
}

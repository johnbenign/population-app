package com.klasha.population.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Population{

    private String city;
    private String country;
    private String code;
    private List<PopulationCount> populationCounts;
}

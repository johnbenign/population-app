package com.klasha.population.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CountryData {
    private List<Population> populationData;
    private CapitalDetail capitalDetail;
    private LocationDetail locationDetail;
    private String currency;
}

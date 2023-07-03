package com.klasha.population.dto.response;

import lombok.Data;

@Data
public class CountryData {
    private Population population;
    private CapitalDetail capitalDetail;
    private LocationDetail locationDetail;
    private String currency;
}

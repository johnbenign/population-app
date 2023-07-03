package com.klasha.population.dto.response;

import lombok.Data;

@Data
public class CurrencyConversionResponse {
    private String countryCurrency;
    private String targetAmount;
}

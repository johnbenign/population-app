package com.klasha.population.service;

import com.klasha.population.dto.request.CurrencyConversionRequest;
import com.klasha.population.dto.response.GeneralResponse;

public interface CountryService {
    GeneralResponse getCitiesByPopulation(long numberOfCities);

    GeneralResponse getCountryData(String country);

    GeneralResponse getStateDetails(String country);

    GeneralResponse convertCurrency(CurrencyConversionRequest request);
}
